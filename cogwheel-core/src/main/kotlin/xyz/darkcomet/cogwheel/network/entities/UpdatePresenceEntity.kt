package xyz.darkcomet.cogwheel.network.entities

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePresenceEntity(
    val since: Int?,
    val activities: ActivityEntity,
    val status: String,
    val afk: Boolean
)