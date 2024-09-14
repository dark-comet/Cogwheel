package xyz.darkcomet.cogwheel.events

import xyz.darkcomet.cogwheel.network.entities.GatewayReadyEventDataEntity

class GatewayReadyEvent internal constructor(val data: GatewayReadyEventDataEntity) : Event {
}