package xyz.darkcomet.cogwheel.network.gateway

import xyz.darkcomet.cogwheel.network.gateway.events.GatewaySendEvent

interface GatewayEventSender {
    
    suspend fun send(event: GatewaySendEvent)
    
}