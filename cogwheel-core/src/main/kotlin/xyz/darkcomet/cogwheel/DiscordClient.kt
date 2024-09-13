package xyz.darkcomet.cogwheel

import xyz.darkcomet.cogwheel.impl.authentication.AuthenticationMode
import xyz.darkcomet.cogwheel.impl.authentication.BotTokenAuthenticationMode
import xyz.darkcomet.cogwheel.impl.authentication.OAuth2BearerAuthenticationMode
import xyz.darkcomet.cogwheel.network.http.api.*

interface DiscordClient {
    
    fun restApi() : RestApi
    
    interface RestApi {
        fun application(): ApplicationApi
        fun applicationRoleConnectionMetadata(): ApplicationRoleConnectionMetadataApi
        fun auditLog(): AuditLogApi
        fun autoModeration(): AutoModerationApi
        fun channel(): ChannelApi
        fun emoji(): EmojiApi
        fun entitlement(): EntitlementApi
        fun guild(): GuildApi
        fun guildScheduledEvent(): GuildScheduledEventApi
        fun guildTemplate(): GuildTemplateApi
        fun invite(): InviteApi
        fun message(): MessageApi
        fun poll(): PollApi
        fun sku(): SkuApi
        fun stageInstance(): StageInstanceApi
        fun sticker(): StickerApi
        fun subscription(): SubscriptionApi
        fun user(): UserApi
        fun voice(): VoiceApi
        fun webhook(): WebhookApi
    }
    
    companion object {
        fun fromBotToken(token: String, init: (DiscordClientBuilder.() -> Unit)? = null): DiscordClient {
            val authMode = BotTokenAuthenticationMode(token)
            return build(authMode, init)
        }

        fun fromOAuth2Token(token: String, init: (DiscordClientBuilder.() -> Unit)? = null): DiscordClient {
            val authMode = OAuth2BearerAuthenticationMode(token)
            return build(authMode, init);
        }

        private fun build(authMode: AuthenticationMode, init: (DiscordClientBuilder.() -> Unit)? = null): DiscordClient {
            val builder = DiscordClientBuilder(authMode)
            init?.invoke(builder)
            
            return builder.build()
        }
    }
}