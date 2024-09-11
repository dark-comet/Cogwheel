package xyz.darkcomet.cogwheel.network.http.entities.application

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.darkcomet.cogwheel.models.Snowflake
import xyz.darkcomet.cogwheel.network.http.entities.guild.GuildEntity
import xyz.darkcomet.cogwheel.network.http.entities.user.UserEntity

@Serializable()
data class ApplicationEntity(
    @Required val id: Snowflake,
    @Required val name: String,
    @Required val icon: String?,
    @Required val description: String,
    @SerialName("rpc_origins") val rpcOrigins: List<String>? = null,
    @Required @SerialName("bot_public") val botPublic: Boolean,
    @Required @SerialName("bot_require_code_grant") val botRequireCodeGrant: Boolean,
    val bot: UserEntity? = null,
    @SerialName("terms_of_service_url") val termsOfServiceUrl: String? = null,
    @SerialName("privacy_policy_url") val privacyPolicyUrl: String? = null,
    @SerialName("owner") val owner: UserEntity? = null,
    @Required @SerialName("verify_key") val verifyKey: String,
    @Required val team: TeamEntity?,
    @SerialName("guild_id") val guildId: Snowflake? = null,
    val guild: GuildEntity? = null,
    @SerialName("primary_sku_id") val primarySkuId: Snowflake? = null,
    val slug: String? = null,
    @SerialName("cover_image") val coverImage: String? = null,
    val flags: Int? = null,
    @SerialName("approximate_guild_count") val approximateGuildCount: Int? = null,
    @SerialName("approximate_user_install_count") val approximateUserInstallCount: Int? = null,
    @SerialName("redirect_uris") val redirectUris: List<String>? = null,
    @SerialName("interactions_endpoint_url") val interactionsEndpointUrl: String? = null,
    @SerialName("role_connections_verification_url") val roleConnectionsVerificationUrl: String? = null,
    val tags: List<String>? = null,
    @SerialName("install_params") val installParams: InstallParamsEntity? = null,
//    @SerialName("integration_types_config") val integrationTypesConfig: Map<String, String>? = null,
    @SerialName("custom_install_url") val customInstallUrl: String? = null,
) {
}