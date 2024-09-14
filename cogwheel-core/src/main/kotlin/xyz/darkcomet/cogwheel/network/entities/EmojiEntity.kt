package xyz.darkcomet.cogwheel.network.entities

import kotlinx.serialization.Serializable
import xyz.darkcomet.cogwheel.models.Snowflake

@Serializable
data class EmojiEntity(
    val id: Snowflake
)