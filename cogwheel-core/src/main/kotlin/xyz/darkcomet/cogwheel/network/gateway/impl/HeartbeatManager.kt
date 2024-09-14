package xyz.darkcomet.cogwheel.network.gateway.impl

import kotlinx.coroutines.delay
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.darkcomet.cogwheel.network.CancellationToken
import kotlin.random.Random

internal class HeartbeatManager(
    private val sessionId: String,
    private val heartbeatIntervalMs: Long,
    private val client: KtorGatewayClient,
    private val sessionCancellation: CancellationToken
) {
    private val logger: Logger = LoggerFactory.getLogger(HeartbeatManager::class.java)
    private var firstHeartbeat = true
    
    suspend fun beginBackgroundHeartbeats() {
        logger.debug("Begin heartbeat loop at {}ms intervals, sessionId: {}", heartbeatIntervalMs, sessionId)
        
        while (!sessionCancellation.isCanceled()) {
            val delayMs: Long

            if (firstHeartbeat) {
                val jitter = Random.nextFloat()
                delayMs = ((heartbeatIntervalMs.toDouble()) * jitter).toLong() - 8_000
                firstHeartbeat = false
            } else {
                delayMs = heartbeatIntervalMs - 5_000 /* Safety margin to account for network latency */
            }

            delay(delayMs)

            logger.debug("Gateway heartbeat. delay: {}", delayMs)
            client.heartbeat()
        }
    }
}