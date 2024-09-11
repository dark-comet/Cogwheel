package xyz.darkcomet.cogwheel.network.http.entities.application

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.darkcomet.cogwheel.network.http.entities.user.UserEntity

@Serializable
data class TeamMemberEntity(
    @Required @SerialName("membership_state") val membershipState: Int,
    @Required @SerialName("team_id") val teamId: Int,
    @Required val user: UserEntity,
    @Required val role: String
)