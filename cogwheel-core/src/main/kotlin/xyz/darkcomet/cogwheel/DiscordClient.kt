package xyz.darkcomet.cogwheel

import kotlinx.coroutines.runBlocking
import xyz.darkcomet.cogwheel.events.Event
import xyz.darkcomet.cogwheel.events.InteractionCreateEvent
import xyz.darkcomet.cogwheel.impl.authentication.AuthenticationMode
import xyz.darkcomet.cogwheel.impl.authentication.BotTokenAuthenticationMode
import xyz.darkcomet.cogwheel.impl.authentication.OAuth2BearerAuthenticationMode
import xyz.darkcomet.cogwheel.network.http.api.*

interface DiscordClient {
    
    fun restApi(): ClientRestApi
    fun gatewayApi(): ClientGatewayApi
    fun events(): ClientEventManager
    
    suspend fun startGatewayConnection()

    interface ClientRestApi {
        fun application(): ApplicationApi
        fun applicationRoleConnectionMetadata(): ApplicationRoleConnectionMetadataApi
        fun auditLog(): AuditLogApi
        fun autoModeration(): AutoModerationApi
        fun channel(): ChannelApi
        fun emoji(): EmojiApi
        fun entitlement(): EntitlementApi
        fun gateway(): GatewayApi
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
    
    interface ClientGatewayApi {
        fun requestGuildMembers() // TODO
        fun updateVoiceState() // TODO
        fun updatePresence() // TODO
    }
    
    interface ClientEventManager {
        fun <T : Event> subscribe(handler: DiscordClient.(T) -> Unit)
        fun fireInteractionCreate(event: InteractionCreateEvent)
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

            val client = builder.build()
            
            if (builder.isGatewayEnabled()) {
                runBlocking {
                    client.startGatewayConnection()
                }
            }
            
            return client
        }
    }
}