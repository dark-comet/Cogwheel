package xyz.darkcomet.cogwheel.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.darkcomet.cogwheel.DiscordClient
import xyz.darkcomet.cogwheel.impl.authentication.AuthenticationMode
import xyz.darkcomet.cogwheel.impl.models.CwBaseConfiguration
import xyz.darkcomet.cogwheel.impl.models.CwCustomConfiguration
import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.resources.*

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
        
        private val applicationResource: ApplicationResource = ApplicationResource(httpClient)
        private val applicationRoleConnectionMetadataResource: ApplicationRoleConnectionMetadataResource = ApplicationRoleConnectionMetadataResource(httpClient)
        private val auditLogResource: AuditLogResource = AuditLogResource(httpClient)
        private val autoModerationResource: AutoModerationResource = AutoModerationResource(httpClient)
        private val channelResource: ChannelResource = ChannelResource(httpClient)
        private val emojiResource: EmojiResource = EmojiResource(httpClient)
        private val entitlementResource: EntitlementResource = EntitlementResource(httpClient)
        private val guildResource: GuildResource = GuildResource(httpClient)
        private val guildScheduledEventResource: GuildScheduledEventResource = GuildScheduledEventResource(httpClient)
        private val guildTemplateResource: GuildTemplateResource = GuildTemplateResource(httpClient)
        private val inviteResource: InviteResource = InviteResource(httpClient)
        private val messageResource: MessageResource = MessageResource(httpClient)
        private val pollResource: PollResource = PollResource(httpClient)
        private val skuResource: SkuResource = SkuResource(httpClient)
        private val stageInstanceResource: StageInstanceResource = StageInstanceResource(httpClient)
        private val stickerResource: StickerResource = StickerResource(httpClient)
        private val subscriptionResource: SubscriptionResource = SubscriptionResource(httpClient)
        private val userResource: UserResource = UserResource(httpClient)
        private val voiceResource: VoiceResource = VoiceResource(httpClient)
        private val webhookResource: WebhookResource = WebhookResource(httpClient)

        override fun application(): ApplicationResource = applicationResource
        override fun applicationRoleConnectionMetadata(): ApplicationRoleConnectionMetadataResource = applicationRoleConnectionMetadataResource
        override fun auditLog(): AuditLogResource = auditLogResource
        override fun autoModeration(): AutoModerationResource = autoModerationResource
        override fun channel(): ChannelResource = channelResource
        override fun emoji(): EmojiResource = emojiResource
        override fun entitlement(): EntitlementResource = entitlementResource
        override fun guild(): GuildResource = guildResource
        override fun guildScheduledEvent(): GuildScheduledEventResource = guildScheduledEventResource
        override fun guildTemplateResource(): GuildTemplateResource = guildTemplateResource
        override fun inviteResource(): InviteResource = inviteResource
        override fun message(): MessageResource = messageResource
        override fun poll(): PollResource = pollResource
        override fun sku(): SkuResource = skuResource
        override fun stageInstance(): StageInstanceResource = stageInstanceResource
        override fun sticker(): StickerResource = stickerResource
        override fun subscription(): SubscriptionResource = subscriptionResource
        override fun user(): UserResource = userResource
        override fun voice(): VoiceResource = voiceResource
        override fun webhook(): WebhookResource = webhookResource
    }
}