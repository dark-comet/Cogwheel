package xyz.darkcomet.cogwheel.network.entities.user

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.darkcomet.cogwheel.models.Snowflake

@Serializable
data class UserEntity(
    val id: Snowflake,
    val username: String,
    val discriminator: String,
    @SerialName("global_name") val globalName: String?,
    val avatar: String?,
    val bot: Boolean? = null,
    val system: Boolean? = null,
    @SerialName("mfa_enabled") val mfaEnabled: Boolean? = null,
    val banner: String? = null,
    @SerialName("accent_color") val accentColor: Int? = null,
    val locale: String? = null,
    val verified: Boolean? = null,
    val email: String? = null,
    val flags: Int? = null,
    @SerialName("premium_type") val premiumType: Int? = null,
    @SerialName("public_flags") val publicFlags: Int? = null,
    @SerialName("avatar_decoration_data") val avatarDecorationData: AvatarDecorationDataEntity? = null
)