package xyz.darkcomet.cogwheel.network.gateway.events

import kotlinx.serialization.json.Json
import xyz.darkcomet.cogwheel.models.Intents
import xyz.darkcomet.cogwheel.network.entities.GatewayIdentifyEventDataEntity
import xyz.darkcomet.cogwheel.network.gateway.GatewayEventPayload
import xyz.darkcomet.cogwheel.network.gateway.GatewayOpCode

class GatewayIdentifySendEvent(
    private val token: String,
    private val osName: String,
    private val libName: String,
    private val intents: Intents
) : GatewaySendEvent {
    
    override fun payload(): GatewayEventPayload {
        val data = GatewayIdentifyEventDataEntity(
            token = token,
            properties = GatewayIdentifyEventDataEntity.IdentifyConnectionPropertiesEntity(
                os = osName,
                browser = libName,
                device = libName
            ),
            intents = intents.value
        )

        val dataElement = Json.encodeToJsonElement(GatewayIdentifyEventDataEntity.serializer(), data)
        
        return GatewayEventPayload(GatewayOpCode.IDENTIFY, d = dataElement)
    }
    
}