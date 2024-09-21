package xyz.darkcomet.cogwheel.network.gateway.events

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import xyz.darkcomet.cogwheel.network.gateway.GatewayEventPayload
import xyz.darkcomet.cogwheel.network.gateway.GatewayOpCode

class GatewayHeartbeatSendEvent(private val lastReceivedSequenceId: Int) : GatewaySendEvent {
    override fun payload(): GatewayEventPayload {
        return GatewayEventPayload(
            op = GatewayOpCode.HEARTBEAT,
            d = Json.encodeToJsonElement(lastReceivedSequenceId),
        )
    }
}