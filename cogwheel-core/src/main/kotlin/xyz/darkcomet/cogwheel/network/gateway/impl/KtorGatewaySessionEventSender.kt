package xyz.darkcomet.cogwheel.network.gateway.impl

import io.ktor.client.plugins.websocket.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.darkcomet.cogwheel.network.gateway.GatewayEventSender
import xyz.darkcomet.cogwheel.network.gateway.events.GatewaySendEvent

class KtorGatewaySessionEventSender(private val wssSession: DefaultClientWebSocketSession) : GatewayEventSender {
    
    private val logger: Logger = LoggerFactory.getLogger(KtorGatewaySessionEventSender::class.java)
    
    override suspend fun send(event: GatewaySendEvent) {
        val payload = event.payload()
        logger.debug("Sending event: {}", payload)
        
        wssSession.sendSerialized(payload)
    }
}