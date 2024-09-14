package xyz.darkcomet.cogwheel.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.darkcomet.cogwheel.DiscordClient
import xyz.darkcomet.cogwheel.events.Event
import xyz.darkcomet.cogwheel.events.InteractionCreateEvent
import xyz.darkcomet.cogwheel.impl.models.CwConfiguration
import xyz.darkcomet.cogwheel.network.CancellationTokenSource
import xyz.darkcomet.cogwheel.network.gateway.CwGatewayClient
import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.api.*

internal open class DiscordClientImpl 
internal constructor(settings: DiscordClientSettings) : DiscordClient {
    
    private val config: CwConfiguration = CwConfiguration.load()
    private val logger: Logger = LoggerFactory.getLogger(DiscordClientImpl::class.java)

    private val restClient: CwHttpClient
    private val gatewayClient: CwGatewayClient?
    
    private val restApi: DiscordClient.ClientRestApi
    private val gatewayApi: DiscordClient.ClientGatewayApi
    private val clientEventManager: DiscordClient.ClientEventManager
    
    init {
        logger.info("{} v{} initializing...", config.clientName, config.clientVersion)
        
        restClient = settings.cwHttpClientFactory.create(settings, config)
        logger.info("Initialized CwHttpClient: {}", restClient.javaClass.name)

        gatewayClient = if (settings.gatewayEnabled) {
            settings.cwGatewayClientFactory.create(settings.token, settings.gatewayIntents, config.clientName)
        } else null
        
        restApi = ClientRestApiImpl(restClient)
        gatewayApi = ClientGatewayApiImpl(gatewayClient)
        clientEventManager = ClientEventManagerImpl()
        logger.info("DiscordClient initialized")
    }

    override suspend fun startGatewayConnection() {
        if (gatewayClient == null) {
            throw IllegalStateException("gatewayClient not initialized! Build DiscordClient using withGateway() first.")
        }
        
        val cancellationToken = CancellationTokenSource()
        
        gatewayClient.startGatewayConnection(cancellationToken) {
            restApi().gateway().get().entity!!.url
        }
    }

    override fun restApi(): DiscordClient.ClientRestApi = restApi
    override fun gatewayApi(): DiscordClient.ClientGatewayApi = gatewayApi
    override fun events(): DiscordClient.ClientEventManager = clientEventManager
    
    internal class ClientRestApiImpl(client: CwHttpClient) : DiscordClient.ClientRestApi {
        private val applicationApi = ApplicationApi(client)
        private val applicationRoleConnectionMetadataApi = ApplicationRoleConnectionMetadataApi(client)
        private val auditLogApi = AuditLogApi(client)
        private val autoModerationApi = AutoModerationApi(client)
        private val channelApi = ChannelApi(client)
        private val emojiApi = EmojiApi(client)
        private val entitlementApi = EntitlementApi(client)
        private val gatewayApi = GatewayApi(client)
        private val guildApi = GuildApi(client)
        private val guildScheduledEventApi = GuildScheduledEventApi(client)
        private val guildTemplateApi = GuildTemplateApi(client)
        private val inviteApi = InviteApi(client)
        private val messageApi = MessageApi(client)
        private val pollApi = PollApi(client)
        private val skuApi = SkuApi(client)
        private val stageInstanceApi = StageInstanceApi(client)
        private val stickerApi = StickerApi(client)
        private val subscriptionApi = SubscriptionApi(client)
        private val userAPi = UserApi(client)
        private val voiceApi = VoiceApi(client)
        private val webhookApi = WebhookApi(client)

        override fun application(): ApplicationApi = applicationApi
        override fun applicationRoleConnectionMetadata(): ApplicationRoleConnectionMetadataApi = applicationRoleConnectionMetadataApi
        override fun auditLog(): AuditLogApi = auditLogApi
        override fun autoModeration(): AutoModerationApi = autoModerationApi
        override fun channel(): ChannelApi = channelApi
        override fun emoji(): EmojiApi = emojiApi
        override fun entitlement(): EntitlementApi = entitlementApi
        override fun gateway(): GatewayApi = gatewayApi
        override fun guild(): GuildApi = guildApi
        override fun guildScheduledEvent(): GuildScheduledEventApi = guildScheduledEventApi
        override fun guildTemplate(): GuildTemplateApi = guildTemplateApi
        override fun invite(): InviteApi = inviteApi
        override fun message(): MessageApi = messageApi
        override fun poll(): PollApi = pollApi
        override fun sku(): SkuApi = skuApi
        override fun stageInstance(): StageInstanceApi = stageInstanceApi
        override fun sticker(): StickerApi = stickerApi
        override fun subscription(): SubscriptionApi = subscriptionApi
        override fun user(): UserApi = userAPi
        override fun voice(): VoiceApi = voiceApi
        override fun webhook(): WebhookApi = webhookApi
    }
    
    internal class ClientGatewayApiImpl(client: CwGatewayClient?) : DiscordClient.ClientGatewayApi {
        override fun requestGuildMembers() {
            TODO("Not yet implemented")
        }

        override fun updateVoiceState() {
            TODO("Not yet implemented")
        }

        override fun updatePresence() {
            TODO("Not yet implemented")
        }
    }
    
    internal class ClientEventManagerImpl : DiscordClient.ClientEventManager {
        override fun <T : Event> subscribe(handler: DiscordClient.(T) -> Unit) {
            TODO("Not yet implemented")
        }

        override fun fireInteractionCreate(event: InteractionCreateEvent) {
            TODO("Not yet implemented")
        }
        
        internal fun fire(event: Event) {
            TODO("Not yet implemented")
        }
    }
}