package xyz.darkcomet.cogwheel.network.entities.application

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.darkcomet.cogwheel.network.entities.user.UserEntity

@Serializable
data class TeamMemberEntity(
    @SerialName("membership_state") val membershipState: Int,
    @SerialName("team_id") val teamId: Int,
    val user: xyz.darkcomet.cogwheel.network.entities.user.UserEntity,
    val role: String
)