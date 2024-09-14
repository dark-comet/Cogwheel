package xyz.darkcomet.cogwheel.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GatewayIdentifyEventDataEntity(
    val token: String,
    val properties: IdentifyConnectionPropertiesEntity,
    val compress: Boolean? = null,
    @SerialName("large_threshold") val largeThreshold: Int? = null,
    val shard: List<Int>? = null,
    val presence: UpdatePresenceEntity? = null,
    val intents: Int
) {
    @Serializable
    data class IdentifyConnectionPropertiesEntity(
        val os: String,
        val browser: String,
        val device: String
    )
}