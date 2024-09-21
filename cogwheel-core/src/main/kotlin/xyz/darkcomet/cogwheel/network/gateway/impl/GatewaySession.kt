package xyz.darkcomet.cogwheel.network.gateway.impl

import xyz.darkcomet.cogwheel.models.ShardId
import xyz.darkcomet.cogwheel.network.CancellationToken
import xyz.darkcomet.cogwheel.network.gateway.GatewayEventSender

internal class GatewaySession(
    private val eventSender: GatewayEventSender,
    private val cancellation: CancellationToken
) {
    
    private var initialized = false
    private var heartbeatStarted = false
    
    var apiVersion: Int? = null
    var sessionId: String? = null
    var resumeGatewayUrl: String? = null
    var shard: ShardId? = null
    var heartbeatIntervalMs: Long? = null
    var lastReceivedSequenceNumber: Int? = null
    
    private var heartbeatManager: GatewayHeartbeatManager? = null
    
    fun initialize(
        apiVersion: Int,
        sessionId: String,
        resumeGatewayUrl: String,
        shard: ShardId?,
        heartbeatIntervalMs: Long
    ) {
        synchronized(this) {
            if (initialized) {
                throw IllegalStateException("Session is already initialized")
            }
            
            this.apiVersion = apiVersion
            this.sessionId = sessionId
            this.resumeGatewayUrl = resumeGatewayUrl
            this.shard = shard
            this.heartbeatIntervalMs = heartbeatIntervalMs

            this.initialized = true
        }
    }
    
    suspend fun beginBackgroundHeartbeats() {
        synchronized(this) {
            if (!initialized) {
                throw IllegalStateException("Session is not initialized!")
            }
            if (heartbeatStarted) {
                throw IllegalStateException("Background heartbeats already started!")
            }

            this.heartbeatManager = GatewayHeartbeatManager({ lastReceivedSequenceNumber }, sessionId!!, heartbeatIntervalMs!!, eventSender, cancellation)
            this.heartbeatStarted = true
        }

        heartbeatManager!!.beginBackgroundHeartbeats()
    }
}