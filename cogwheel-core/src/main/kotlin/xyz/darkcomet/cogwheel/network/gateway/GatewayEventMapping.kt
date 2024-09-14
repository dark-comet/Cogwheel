package xyz.darkcomet.cogwheel.network.gateway

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.darkcomet.cogwheel.events.Event
import xyz.darkcomet.cogwheel.events.GatewayHelloEvent
import xyz.darkcomet.cogwheel.events.GatewayReadyEvent
import xyz.darkcomet.cogwheel.network.entities.GatewayHelloEventDataEntity
import xyz.darkcomet.cogwheel.network.entities.GatewayReadyEventDataEntity
import kotlin.reflect.typeOf

internal class GatewayEventMapping private constructor() {
    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(GatewayEventMapping::class.java)
        private val JSON_DESERIALIZER = Json { ignoreUnknownKeys = true }
        
        fun decode(payload: GatewayEventPayload): Event? {
            return when (payload.op) {
                OP_READY -> decodeWithData<GatewayReadyEventDataEntity, GatewayReadyEvent>(payload) { GatewayReadyEvent(it) }
                OP_IDENTIFY -> unsupportedDecode(payload)
                OP_HELLO -> decodeWithData<GatewayHelloEventDataEntity, GatewayHelloEvent>(payload) { GatewayHelloEvent(it) }
                
                else -> null
            }
        }

        private fun unsupportedDecode(payload: GatewayEventPayload): Event? {
            throw IllegalArgumentException("Not expected to receive event with op: ${payload.op}")
        }

        private inline fun <reified TData, TResult> decodeWithData(payload: GatewayEventPayload, resultFactory: (TData) -> TResult): TResult? {
            if (payload.d == null) {
                LOGGER.warn("Error parsing payload (op: {}) to type {}: 'd' == null", payload.op, typeOf<TData>())
                return null;
            }

            val eventData = JSON_DESERIALIZER.decodeFromJsonElement<TData>(payload.d)
            return resultFactory.invoke(eventData)
        }
        
        const val OP_READY = 0
        const val OP_IDENTIFY = 2
        const val OP_HELLO = 10
    }
}