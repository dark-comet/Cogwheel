package xyz.darkcomet.cogwheel.network.gateway.events

import xyz.darkcomet.cogwheel.network.gateway.GatewayEventPayload

interface GatewaySendEvent {
    
    fun payload(): GatewayEventPayload
    
}