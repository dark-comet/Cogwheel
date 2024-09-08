package xyz.darkcomet.cogwheel.network.http.entities.guild

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.darkcomet.cogwheel.models.*

/**
 * @see <a href="https://discord.com/developers/docs/resources/guild">Discord API Reference: Guild</a>
 */
@Serializable
data class GuildEntity(
    @Required val id: Snowflake,
    @Required val name: String,
    @Required val icon: String?,
    @SerialName("icon_hash") val iconHash: String?,
    @Required val splash: String?,
    @Required @SerialName("discovery_splash") val discoverySplash: String?,
    val owner: String?, //Only set when using the GET Current User Guilds endpoint, and are relative to the requested user
    @Required @SerialName("owner_id") val ownerId: Snowflake,
    val permissions: Permissions?,
    @Deprecated("replaced by channel.rtc_region") val region: String?,
    @SerialName("afk_channel_id") val afkChannelId: Snowflake?,
    @Required @SerialName("afk_timeout") val afkTimeout: Int,
    @SerialName("widget_enabled") val widgetEnabled: Boolean,
    @SerialName("widget_channel_id") val widgetChannelId: Snowflake?,
    @Required @SerialName("verification_level") val verificationLevel: Int,
    @Required @SerialName("default_message_notifications") val defaultMessageNotifications: Int,
    @Required @SerialName("explicit_content_filter") val explicitContentFilter: Int,
    @Required val roles: List<Role>,
    @Required val emojis: List<Emoji>,
    @Required val features: List<GuildFeature>,
    @Required @SerialName("mfa_level") val mfaLevel: Int,
    @Required @SerialName("application_id") val applicationId: Snowflake?,
    @Required @SerialName("system_channel_id") val systemChannelId: Snowflake?,
    @Required @SerialName("system_channel_flags") val systemChannelFlags: Int,
    @Required @SerialName("rules_channel_id") val rulesChannelId: Snowflake?,
    @SerialName("max_presences") val maxPresences: Int?,
    @SerialName("max_members") val maxMembers: Int?,
    @Required @SerialName("vanity_url_code") val vanityUrlCode: String?,
    @Required val description: String?,
    @Required val banner: String?,
    @Required @SerialName("premium_tier") val premiumTier: Int?,
    @SerialName("premium_subscription_count") val premiumSubscriptionCount: Int?,
    @Required @SerialName("preferred_locale") val preferredLocale: String,
    @Required @SerialName("public_updates_channel_id") val publicUpdatesChannelId: Snowflake?,
    @SerialName("max_video_channel_users") val maxVideoChannelUsers: Int?,
    @SerialName("max_stage_video_channel_users") val maxStageVideoChannelUsers: Int?,
    @SerialName("approximate_member_count") val approximateMemberCount: Int?,
    @SerialName("approximate_presence_count") val approximatePresenceCount: Int?,
    @SerialName("welcome_screen") val welcomeScreen: GuildWelcomeScreen?,
    @Required @SerialName("nsfw_level") val nsfwLevel: Int,
    val stickers: List<Sticker>?,
    @SerialName("premium_progress_bar_enabled") val premiumProgressBarEnabled: Boolean,
    @SerialName("safety_alerts_channel_id") val safetyAlertsChannelId: Snowflake?,
)
