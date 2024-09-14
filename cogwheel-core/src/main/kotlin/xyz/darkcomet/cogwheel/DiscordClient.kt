package xyz.darkcomet.cogwheel

import kotlinx.coroutines.runBlocking
import xyz.darkcomet.cogwheel.events.Event
import xyz.darkcomet.cogwheel.events.InteractionCreateEvent
import xyz.darkcomet.cogwheel.impl.authentication.Token
import xyz.darkcomet.cogwheel.impl.authentication.BotToken
import xyz.darkcomet.cogwheel.impl.authentication.OAuth2Token
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
            return build(BotToken(token), init)
        }

        fun fromOAuth2Token(token: String, init: (DiscordClientBuilder.() -> Unit)? = null): DiscordClient {
            return build(OAuth2Token(token), init)
        }

        private fun build(token: Token, init: (DiscordClientBuilder.() -> Unit)? = null): DiscordClient {
            val builder = DiscordClientBuilder(token)
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