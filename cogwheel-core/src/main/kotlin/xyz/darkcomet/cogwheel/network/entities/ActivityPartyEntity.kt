package xyz.darkcomet.cogwheel.network.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivityPartyEntity(
    val id: String? = null,
    val size: List<Int>? = null
)
