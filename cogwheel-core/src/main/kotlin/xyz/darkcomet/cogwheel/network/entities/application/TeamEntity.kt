package xyz.darkcomet.cogwheel.network.entities.application

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.darkcomet.cogwheel.models.Snowflake

@Serializable
data class TeamEntity(
    val icon: String?,
    val id: Snowflake,
    val members: List<xyz.darkcomet.cogwheel.network.entities.application.TeamMemberEntity>,
    val name: String,
    @SerialName("owner_user_id") val ownerUserId: Snowflake,
)