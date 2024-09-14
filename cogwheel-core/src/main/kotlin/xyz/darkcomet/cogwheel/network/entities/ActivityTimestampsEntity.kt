package xyz.darkcomet.cogwheel.network.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivityTimestampsEntity(
    val start: Long? = null,
    val end: Long? = null
)