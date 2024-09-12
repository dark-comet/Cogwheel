package xyz.darkcomet.cogwheel

import xyz.darkcomet.cogwheel.impl.authentication.AuthenticationMode
import xyz.darkcomet.cogwheel.impl.authentication.BotTokenAuthenticationMode
import xyz.darkcomet.cogwheel.impl.authentication.OAuth2BearerAuthenticationMode
import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.resources.*

interface DiscordClient {
    
    fun restApi() : RestApi
    
    interface RestApi {
        fun application(): ApplicationResource
        fun applicationRoleConnectionMetadata(): ApplicationRoleConnectionMetadataResource
        fun auditLog(): AuditLogResource
        fun autoModeration(): AutoModerationResource
        fun channel(): ChannelResource
        fun emoji(): EmojiResource
        fun entitlement(): EntitlementResource
        fun guild(): GuildResource
        fun guildScheduledEvent(): GuildScheduledEventResource
        fun guildTemplateResource(): GuildTemplateResource
        fun inviteResource(): InviteResource
        fun message(): MessageResource
        fun poll(): PollResource
        fun sku(): SkuResource
        fun stageInstance(): StageInstanceResource
        fun sticker(): StickerResource
        fun subscription(): SubscriptionResource
        fun user(): UserResource
        fun voice(): VoiceResource
        fun webhook(): WebhookResource
    }
    
    interface Interactions {
        
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