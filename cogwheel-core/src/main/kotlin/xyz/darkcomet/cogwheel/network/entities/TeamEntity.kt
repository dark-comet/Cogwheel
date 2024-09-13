package xyz.darkcomet.cogwheel.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.darkcomet.cogwheel.models.Snowflake

@Serializable
data class TeamEntity(
    val icon: String?,
    val id: Snowflake,
    val members: List<TeamMemberEntity>,
    val name: String,
    @SerialName("owner_user_id") val ownerUserId: Snowflake,
)