package xyz.darkcomet.cogwheel.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.darkcomet.cogwheel.DiscordClient
import xyz.darkcomet.cogwheel.impl.authentication.AuthenticationMode
import xyz.darkcomet.cogwheel.impl.models.CwBaseConfiguration
import xyz.darkcomet.cogwheel.impl.models.CwCustomConfiguration
import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.api.*

internal open class DiscordClientImpl 
internal constructor(
    dependencies: DiscordClientDependencies,
    authenticationMode: AuthenticationMode,
    clientVersion: String?,
    clientUrl: String?
) : DiscordClient {
    
    private val configuration: CwBaseConfiguration = CwBaseConfiguration.load()
    private val logger: Logger = LoggerFactory.getLogger(DiscordClientImpl::class.java)

    private val httpClient: CwHttpClient
    private val restApi: RestApiImpl
    
    init {
        logger.info("DiscordClient initializing... libName={} version={}", configuration.clientName, configuration.clientVersion)
        
        val configurationOverride = CwCustomConfiguration(clientVersion, clientUrl)
        logger.trace("Custom metadata: version={}, url={}", configurationOverride.clientVersion, configurationOverride.clientUrl)
        
        httpClient = dependencies.cwHttpClientFactory.create(authenticationMode, configuration, configurationOverride)
        logger.info("Initialized CwHttpClient: {}", httpClient.javaClass.name)
        
        restApi = RestApiImpl(httpClient)
        logger.info("DiscordClient initialized")
    }
    
    override fun restApi(): DiscordClient.RestApi = restApi
    
    
    internal class RestApiImpl(httpClient: CwHttpClient) : DiscordClient.RestApi {
        
        private val applicationApi: ApplicationApi = ApplicationApi(httpClient)
        private val applicationRoleConnectionMetadataApi: ApplicationRoleConnectionMetadataApi = ApplicationRoleConnectionMetadataApi(httpClient)
        private val auditLogApi: AuditLogApi = AuditLogApi(httpClient)
        private val autoModerationApi: AutoModerationApi = AutoModerationApi(httpClient)
        private val channelApi: ChannelApi = ChannelApi(httpClient)
        private val emojiApi: EmojiApi = EmojiApi(httpClient)
        private val entitlementApi: EntitlementApi = EntitlementApi(httpClient)
        private val guildAPi: GuildApi = GuildApi(httpClient)
        private val guildScheduledEventApi: GuildScheduledEventApi = GuildScheduledEventApi(httpClient)
        private val guildTemplateApi: GuildTemplateApi = GuildTemplateApi(httpClient)
        private val inviteApi: InviteApi = InviteApi(httpClient)
        private val messageApi: MessageApi = MessageApi(httpClient)
        private val pollApi: PollApi = PollApi(httpClient)
        private val skuApi: SkuApi = SkuApi(httpClient)
        private val stageInstanceApi: StageInstanceApi = StageInstanceApi(httpClient)
        private val stickerApi: StickerApi = StickerApi(httpClient)
        private val subscriptionApi: SubscriptionApi = SubscriptionApi(httpClient)
        private val userAPi: UserApi = UserApi(httpClient)
        private val voiceApi: VoiceApi = VoiceApi(httpClient)
        private val webhookApi: WebhookApi = WebhookApi(httpClient)

        override fun application(): ApplicationApi = applicationApi
        override fun applicationRoleConnectionMetadata(): ApplicationRoleConnectionMetadataApi = applicationRoleConnectionMetadataApi
        override fun auditLog(): AuditLogApi = auditLogApi
        override fun autoModeration(): AutoModerationApi = autoModerationApi
        override fun channel(): ChannelApi = channelApi
        override fun emoji(): EmojiApi = emojiApi
        override fun entitlement(): EntitlementApi = entitlementApi
        override fun guild(): GuildApi = guildAPi
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
}