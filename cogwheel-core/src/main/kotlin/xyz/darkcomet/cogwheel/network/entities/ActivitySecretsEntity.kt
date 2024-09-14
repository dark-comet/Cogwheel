package xyz.darkcomet.cogwheel.network.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivitySecretsEntity(
    val join: String? = null,
    val spectate: String? = null,
    val match: String? = null
)