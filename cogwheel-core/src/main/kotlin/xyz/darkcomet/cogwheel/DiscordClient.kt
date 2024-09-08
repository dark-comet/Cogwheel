package xyz.darkcomet.cogwheel

import xyz.darkcomet.cogwheel.implementation.authentication.BotTokenAuthenticationMode
import xyz.darkcomet.cogwheel.implementation.authentication.OAuth2BearerAuthenticationMode
import xyz.darkcomet.cogwheel.network.http.resources.*

interface DiscordClient {

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

    companion object {
        fun fromBotToken(token: String): DiscordClientBuilder {
            val authenticationMode = BotTokenAuthenticationMode(token)
            return DiscordClientBuilder(authenticationMode);
        }

        fun fromOAuth2Token(token: String): DiscordClientBuilder {
            val authenticationMode = OAuth2BearerAuthenticationMode(token)
            return DiscordClientBuilder(authenticationMode);
        }
    }
}