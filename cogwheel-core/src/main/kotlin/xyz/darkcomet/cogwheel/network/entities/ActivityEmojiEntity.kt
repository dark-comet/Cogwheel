package xyz.darkcomet.cogwheel.network.entities

import kotlinx.serialization.Serializable
import xyz.darkcomet.cogwheel.models.Snowflake

@Serializable
data class ActivityEmojiEntity(
    val name: String,
    val id: Snowflake? = null,
    val animated: Boolean? = null
)