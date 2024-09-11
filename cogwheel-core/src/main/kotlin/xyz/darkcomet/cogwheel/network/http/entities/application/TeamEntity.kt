package xyz.darkcomet.cogwheel.network.http.entities.application

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.darkcomet.cogwheel.models.Snowflake

@Serializable
data class TeamEntity(
    @Required val icon: String?,
    @Required val id: Snowflake,
    @Required val members: List<TeamMemberEntity>,
    @Required val name: String,
    @Required @SerialName("owner_user_id") val ownerUserId: Snowflake,
)