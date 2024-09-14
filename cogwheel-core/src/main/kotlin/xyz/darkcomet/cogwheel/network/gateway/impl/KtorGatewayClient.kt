package xyz.darkcomet.cogwheel.network.gateway.impl

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.darkcomet.cogwheel.events.GatewayHelloEvent
import xyz.darkcomet.cogwheel.events.GatewayReadyEvent
import xyz.darkcomet.cogwheel.impl.authentication.Token
import xyz.darkcomet.cogwheel.models.Intents
import xyz.darkcomet.cogwheel.models.ShardId
import xyz.darkcomet.cogwheel.network.CancellationToken
import xyz.darkcomet.cogwheel.network.CancellationTokenSource
import xyz.darkcomet.cogwheel.network.entities.GatewayIdentifyEventDataEntity
import xyz.darkcomet.cogwheel.network.gateway.CwGatewayClient
import xyz.darkcomet.cogwheel.network.gateway.GatewayEventMapping
import xyz.darkcomet.cogwheel.network.gateway.GatewayEventPayload
import java.net.UnknownHostException
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit

class KtorGatewayClient 
private constructor(
    private val token: Token, 
    private val intents: Intents, 
    private val libName: String
) : CwGatewayClient {
    
    private val httpClient: HttpClient = HttpClient(OkHttp) {
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
        
        engine {
            preconfigured = OkHttpClient.Builder().pingInterval(20, TimeUnit.SECONDS).build()
        }
    }

    private var lastFetchedGatewayUrl: String? = null
    
    private val eventSendQueue: Queue<GatewayEventPayload> = ConcurrentLinkedQueue()
    private val eventReceiveQueue: Queue<GatewayEventPayload> = ConcurrentLinkedQueue()
    private var session: GatewaySession? = null 
    
    private val logger: Logger = LoggerFactory.getLogger(KtorGatewayClient::class.java)
    
    init {
        logger.info("Gateway HttpClient initialized")
    }

    override suspend fun startGatewayConnection(
        cancellationToken: CancellationToken,
        gatewayUrlFetcher: suspend () -> String?
    ) {
        logger.info("Starting Gateway connection...")
        
        var sessionCount = 0
        var fetchUrl = lastFetchedGatewayUrl == null
        
        val serviceCancellation = object : CancellationTokenSource() {
            override fun isCanceled(): Boolean {
                return cancellationToken.isCanceled() || this.canceled.get() 
            }
        }

        while (!serviceCancellation.isCanceled()) {
            sessionCount++
            
            val sessionCancellation = object : CancellationTokenSource() {
                override fun isCanceled(): Boolean {
                    return serviceCancellation.isCanceled() || this.canceled.get()
                }
            }
            
            if (fetchUrl) {
                lastFetchedGatewayUrl = fetchGatewayUrl(gatewayUrlFetcher, sessionCancellation)
                fetchUrl = false
            }
            
            try {
                establishGatewaySession(lastFetchedGatewayUrl!!, sessionCount, sessionCancellation)
            } catch (exception: UnknownHostException) {
                if (sessionCount == 1) {
                    logger.warn("Invalid Gateway URL '{}' - refreshing cached address now...", lastFetchedGatewayUrl)
                    fetchUrl = true
                } else {
                    logger.error("Invalid Gateway URL '{}' after retry, aborting connection attempt", lastFetchedGatewayUrl)
                    serviceCancellation.cancel()
                }
            } catch (exception: ClosedReceiveChannelException) {
                logger.warn("Gateway receive channel closed, retrying connection soon...")
                sessionCancellation.cancel()
                delay(1000)
            }
        }
    }

    private suspend fun fetchGatewayUrl(
        gatewayUrlFetcher: suspend () -> String?,
        sessionCancellation: CancellationTokenSource
    ): String? {
        logger.debug("Fetching Gateway URL...")
        var attempts = 0
        var gatewayUrl: String?

        do {
            attempts++
            gatewayUrl = gatewayUrlFetcher.invoke()
            logger.debug("Fetch Gateway URL attempt {}, got response: {}", attempts, gatewayUrl)
            
            if (gatewayUrl != null) {
                gatewayUrl = gatewayUrl.replace("wss://", "").trim()
            }
            
            if (sessionCancellation.isCanceled()) {
                logger.debug("Aborted Gateway URL fetch: session canceled")
                break
            }
        } while (gatewayUrl == null)
        
        return gatewayUrl
    }

    private suspend fun establishGatewaySession(
        gatewayUrl: String,
        sessionCount: Int,
        sessionCancellation: CancellationToken
    ) {
        logger.debug("Establishing Gateway connection: GET {} (session {})", lastFetchedGatewayUrl, sessionCount)
        
        synchronized(this) {
            if (session != null) {
                session = null
            }
        }
        
        httpClient.wss(method = HttpMethod.Get, host = gatewayUrl) {
            logger.info("Connected to {} successfully!", gatewayUrl)
            val wssSession = this
            
            performHandshake(wssSession, sessionCancellation)

            val receiverJob = launch { eventReceiverLoop(wssSession, sessionCancellation) }
            val senderJob = launch { eventSenderLoop(this, wssSession, sessionCancellation) }

            eventProcessorLoop(wssSession, sessionCancellation)
            
            receiverJob.join()
            senderJob.join()
            
            logger.info("Gateway connection closed permanently")
        }
    }

    private suspend fun performHandshake(
        wssSession: DefaultClientWebSocketSession,
        sessionCancellation: CancellationToken
    ) {
        val gatewaySession = GatewaySession(this, sessionCancellation)
        
        synchronized(this) {
            this.session = gatewaySession
        }
        
        val helloEvent = receiveHelloEvent(wssSession, gatewaySession)
        sendIdentifyWithIntents(wssSession)
        val readyEvent = receiveReadyEvent(wssSession, gatewaySession)
        
        synchronized(this) {
            gatewaySession.initialize(
                apiVersion = readyEvent.data.v, 
                sessionId = readyEvent.data.sessionId,
                resumeGatewayUrl = readyEvent.data.resumeGatewayUrl,
                shard = ShardId.from(readyEvent.data.shard),
                heartbeatIntervalMs = helloEvent.data.heartbeatInterval
            )
        }
        
        gatewaySession.beginBackgroundHeartbeats()
    }

    private suspend fun receiveHelloEvent(
        wssSession: DefaultClientWebSocketSession,
        gatewaySession: GatewaySession
    ): GatewayHelloEvent {
        val payload = receiveEvent(wssSession, gatewaySession)
        val firstEvent = GatewayEventMapping.decode(payload)

        if (firstEvent !is GatewayHelloEvent) {
            throw IllegalStateException("Invalid first payload during handshake! op: ${payload.op} (expected 10)")
        }
        
        return firstEvent
    }

    private suspend fun sendIdentifyWithIntents(session: DefaultClientWebSocketSession) {
        val data = GatewayIdentifyEventDataEntity(
            token = token.value,
            properties = GatewayIdentifyEventDataEntity.IdentifyConnectionPropertiesEntity(
                os = System.getProperty("os.name"),
                browser = libName,
                device = libName
            ),
            intents = intents.value
        )
        
        val dataElement = Json.encodeToJsonElement(GatewayIdentifyEventDataEntity.serializer(), data)
        val payload = GatewayEventPayload(op = GatewayEventMapping.OP_IDENTIFY, d = dataElement)
        
        session.sendSerialized(payload)
    }

    private suspend fun receiveReadyEvent(
        wssSession: DefaultClientWebSocketSession,
        gatewaySession: GatewaySession
    ): GatewayReadyEvent {
        val payload = receiveEvent(wssSession, gatewaySession)
        val secondEvent = GatewayEventMapping.decode(payload)

        if (secondEvent !is GatewayReadyEvent) {
            val eventDescription = if (secondEvent == null) "null" else secondEvent::class.simpleName
            throw IllegalStateException("Invalid second payload during handshake! op: ${payload.op} (expected 0), got event: $eventDescription")
        }

        return secondEvent
    }

    private suspend fun eventReceiverLoop(session: DefaultClientWebSocketSession, cancellationToken: CancellationToken) {
        while (!cancellationToken.isCanceled()) {
            val payload = session.receiveDeserialized<GatewayEventPayload>()
            logger.debug("Received payload: {}", payload)
            
            eventReceiveQueue.add(payload)
        }
    }
    
    private suspend fun eventSenderLoop(
        coroutine: CoroutineScope,
        session: DefaultClientWebSocketSession,
        cancellation: CancellationToken
    ) {
        while (!cancellation.isCanceled()) {
            val sendEvent = eventSendQueue.poll()
            
            if (sendEvent == null) {
                yield()
                continue
            }
            
            coroutine.launch { 
                session.sendSerialized(sendEvent)
                logger.debug("Sent payload: {}", sendEvent)
            }
        }
    }

    private fun eventProcessorLoop(session: DefaultClientWebSocketSession, cancellation: CancellationToken) {
    }

    private suspend fun receiveEvent(
        wssSession: DefaultClientWebSocketSession,
        gatewaySession: GatewaySession
    ): GatewayEventPayload {
        val payload = wssSession.receiveDeserialized<GatewayEventPayload>()
        logger.debug("Received Gateway payload: {}", payload)

        if (payload.s != null) {
            if (payload.s < gatewaySession.lastReceivedSequenceNumber ?: 0) {
                logger.warn("Received Gateway payload has 's' < last locally received 's' number: {} < {}", payload.s, gatewaySession.lastReceivedSequenceNumber)
            }
            gatewaySession.lastReceivedSequenceNumber = payload.s
        }

        return payload
    }

    fun heartbeat() {
        // TODO: 
    }

    class Factory : CwGatewayClient.Factory {
        override fun create(token: Token, intents: Intents, libName: String): CwGatewayClient {
            return KtorGatewayClient(token, intents, libName)
        }
    }
}