package xyz.darkcomet.cogwheel.network.gateway

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class GatewayEventPayload(
    val op: Int,
    val d: JsonElement? = null,
    val s: Int? = null,
    val t: String? = null
)
