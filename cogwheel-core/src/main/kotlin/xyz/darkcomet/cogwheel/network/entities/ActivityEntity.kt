package xyz.darkcomet.cogwheel.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.darkcomet.cogwheel.models.Snowflake

@Serializable
data class ActivityEntity(
    val name: String,
    val type: Int,
    val url: String? = null,
    @SerialName("created_at") val createdAt: Int,
    val timestamps: ActivityTimestampsEntity? = null,
    @SerialName("application_id") val applicationId: Snowflake? = null,
    val details: String? = null,
    val state: String? = null,
    val emoji: ActivityEmojiEntity? = null,
    val party: ActivityPartyEntity? = null,
    val assets: ActivityAssetsEntity? = null,
    val secrets: ActivitySecretsEntity? = null,
    val instance: Boolean? = null,
    val flags: Int? = null,
    val buttons: List<ActivityButtonEntity>? = null
)