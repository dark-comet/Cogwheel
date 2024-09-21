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
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.darkcomet.cogwheel.events.GatewayHelloEvent
import xyz.darkcomet.cogwheel.events.GatewayReadyEvent
import xyz.darkcomet.cogwheel.impl.authentication.Token
import xyz.darkcomet.cogwheel.models.Intents
import xyz.darkcomet.cogwheel.models.ShardId
import xyz.darkcomet.cogwheel.network.CancellationToken
import xyz.darkcomet.cogwheel.network.CancellationTokenSource
import xyz.darkcomet.cogwheel.network.gateway.CwGatewayClient
import xyz.darkcomet.cogwheel.network.gateway.GatewayEventMapping
import xyz.darkcomet.cogwheel.network.gateway.GatewayEventPayload
import xyz.darkcomet.cogwheel.network.gateway.GatewayEventSender
import xyz.darkcomet.cogwheel.network.gateway.events.GatewayIdentifySendEvent
import xyz.darkcomet.cogwheel.network.gateway.events.GatewaySendEvent
import java.net.UnknownHostException
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

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
    }
    
    private val eventSendQueue: Queue<GatewayEventPayload> = ConcurrentLinkedQueue()
    private val eventReceiveQueue: Queue<GatewayEventPayload> = ConcurrentLinkedQueue()

    private var lastFetchedGatewayUrl: String? = null
    private var session: GatewaySession? = null 
    
    private val logger: Logger = LoggerFactory.getLogger(KtorGatewayClient::class.java)
    
    init {
        logger.info("Gateway HttpClient initialized")
    }

    override suspend fun startGatewayConnection(cancellationToken: CancellationToken, gatewayUrlFetcher: suspend () -> String?) {
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
    
    private suspend fun fetchGatewayUrl(gatewayUrlFetcher: suspend () -> String?, sessionCancellation: CancellationTokenSource): String? {
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

    private suspend fun establishGatewaySession(gatewayUrl: String, sessionCount: Int, sessionCancellation: CancellationToken) {
        logger.debug("Establishing Gateway connection: GET {} (session {})", lastFetchedGatewayUrl, sessionCount)
        
        synchronized(this) {
            if (session != null) {
                session = null
            }
        }
        
        httpClient.wss(method = HttpMethod.Get, host = gatewayUrl) {
            logger.info("Connected to {} successfully!", gatewayUrl)
            
            val wssSession = this
            val eventSender = KtorGatewaySessionEventSender(wssSession)
            
            performHandshake(eventSender, wssSession, sessionCancellation)

            val receiverJob = launch { eventReceiverLoop(wssSession, sessionCancellation) }
            val senderJob = launch { eventSenderLoop(this, wssSession, sessionCancellation) }

            eventProcessorLoop(wssSession, sessionCancellation)
            
            receiverJob.join()
            senderJob.join()
            
            logger.info("Gateway connection closed permanently")
        }
    }

    private suspend fun performHandshake(
        eventSender: GatewayEventSender, 
        wssSession: DefaultClientWebSocketSession, 
        cancellation: CancellationToken
    ) {
        val gatewaySession = GatewaySession(eventSender, cancellation)
        
        synchronized(this) {
            this.session = gatewaySession
        }
        
        val helloEvent = receiveHelloEvent(wssSession, gatewaySession)
        sendIdentifyWithIntents(eventSender)
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

    private suspend fun receiveHelloEvent(wssSession: DefaultClientWebSocketSession, gatewaySession: GatewaySession): GatewayHelloEvent {
        val payload = receiveEvent(wssSession, gatewaySession)
        val firstEvent = GatewayEventMapping.decode(payload)

        if (firstEvent !is GatewayHelloEvent) {
            throw IllegalStateException("Invalid first payload during handshake! op: ${payload.op} (expected 10)")
        }
        
        return firstEvent
    }

    private suspend fun sendIdentifyWithIntents(eventSender: GatewayEventSender) {
        val osName = System.getProperty("os.name")
        val event = GatewayIdentifySendEvent(token.value, osName, libName, intents)
        
        eventSender.send(event)
    }

    private suspend fun receiveReadyEvent(wssSession: DefaultClientWebSocketSession, gatewaySession: GatewaySession): GatewayReadyEvent {
        val payload = receiveEvent(wssSession, gatewaySession)
        val secondEvent = GatewayEventMapping.decode(payload)

        if (secondEvent !is GatewayReadyEvent) {
            val eventDescription = if (secondEvent == null) "null" else secondEvent::class.simpleName
            throw IllegalStateException("Invalid second payload during handshake! op: ${payload.op} (expected 0), got event: $eventDescription")
        }

        return secondEvent
    }

    private suspend fun eventReceiverLoop(wssSession: DefaultClientWebSocketSession, cancellation: CancellationToken) {
        while (!cancellation.isCanceled()) {
            val payload = wssSession.receiveDeserialized<GatewayEventPayload>()
            logger.debug("Received payload: {}", payload)
            
            if (payload.s != null) {
                synchronized(this) {
                    if (this.session != null) {
                        this.session!!.lastReceivedSequenceNumber = payload.s
                    }
                }
            }
            
            eventReceiveQueue.add(payload)
        }
    }
    
    private suspend fun eventSenderLoop(coroutine: CoroutineScope, session: DefaultClientWebSocketSession, cancellation: CancellationToken) {
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

    private suspend fun receiveEvent(wssSession: DefaultClientWebSocketSession, gatewaySession: GatewaySession): GatewayEventPayload {
        val payload = wssSession.receiveDeserialized<GatewayEventPayload>()
        logger.debug("Received Gateway payload: {}", payload)

        if (payload.s != null) {
            if (payload.s < (gatewaySession.lastReceivedSequenceNumber ?: 0)) {
                logger.warn("Received Gateway payload has 's' < last locally received 's' number: {} < {}", payload.s, gatewaySession.lastReceivedSequenceNumber)
            }
            gatewaySession.lastReceivedSequenceNumber = payload.s
        }

        return payload
    }
    
    override suspend fun send(event: GatewaySendEvent) {
        eventSendQueue.add(event.payload())
    }

    class Factory : CwGatewayClient.Factory {
        override fun create(token: Token, intents: Intents, libName: String): CwGatewayClient {
            return KtorGatewayClient(token, intents, libName)
        }
    }
}