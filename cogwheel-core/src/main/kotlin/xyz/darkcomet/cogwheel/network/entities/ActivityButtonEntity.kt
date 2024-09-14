package xyz.darkcomet.cogwheel.network.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivityButtonEntity(
    val label: String,
    val url: String
)