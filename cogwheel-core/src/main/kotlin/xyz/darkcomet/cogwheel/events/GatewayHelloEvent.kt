package xyz.darkcomet.cogwheel.events

import xyz.darkcomet.cogwheel.network.entities.GatewayHelloEventDataEntity

class GatewayHelloEvent 
internal constructor(val data: GatewayHelloEventDataEntity) : Event {
    
}