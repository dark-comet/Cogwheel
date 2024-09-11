package xyz.darkcomet.cogwheel

import xyz.darkcomet.cogwheel.implementation.authentication.AuthenticationMode
import xyz.darkcomet.cogwheel.implementation.models.CwBaseConfiguration
import xyz.darkcomet.cogwheel.implementation.models.CwCustomConfiguration
import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.KtorHttpClient
import xyz.darkcomet.cogwheel.network.http.resources.*

internal open class DiscordClientImpl 
internal constructor(
    authenticationMode: AuthenticationMode,
    clientVersion: String?,
    clientUrl: String?
) : DiscordClient {

    private val applicationResource: ApplicationResource
    private val applicationRoleConnectionMetadataResource: ApplicationRoleConnectionMetadataResource
    private val auditLogResource: AuditLogResource
    private val autoModerationResource: AutoModerationResource
    private val channelResource: ChannelResource
    private val emojiResource: EmojiResource
    private val entitlementResource: EntitlementResource
    private val guildResource: GuildResource
    private val guildScheduledEventResource: GuildScheduledEventResource
    private val guildTemplateResource: GuildTemplateResource
    private val inviteResource: InviteResource
    private val messageResource: MessageResource
    private val pollResource: PollResource
    private val skuResource: SkuResource
    private val stageInstanceResource: StageInstanceResource
    private val stickerResource: StickerResource
    private val subscriptionResource: SubscriptionResource
    private val userResource: UserResource
    private val voiceResource: VoiceResource
    private val webhookResource: WebhookResource
    
    protected val configuration: CwBaseConfiguration
    private val httpClient: CwHttpClient
    
    init {
        configuration = CwBaseConfiguration.load()
        val configurationOverride = CwCustomConfiguration(clientVersion, clientUrl)
        
        httpClient = KtorHttpClient(authenticationMode, configuration, configurationOverride)

        applicationResource = ApplicationResource(httpClient)
        applicationRoleConnectionMetadataResource = ApplicationRoleConnectionMetadataResource(httpClient)
        auditLogResource = AuditLogResource(httpClient)
        autoModerationResource = AutoModerationResource(httpClient)
        channelResource = ChannelResource(httpClient)
        emojiResource = EmojiResource(httpClient)
        entitlementResource = EntitlementResource(httpClient)
        guildResource = GuildResource(httpClient)
        guildScheduledEventResource = GuildScheduledEventResource(httpClient)
        guildTemplateResource = GuildTemplateResource(httpClient)
        inviteResource = InviteResource(httpClient)
        messageResource = MessageResource(httpClient)
        pollResource = PollResource(httpClient)
        skuResource = SkuResource(httpClient)
        stageInstanceResource = StageInstanceResource(httpClient)
        stickerResource = StickerResource(httpClient)
        subscriptionResource = SubscriptionResource(httpClient)
        userResource = UserResource(httpClient)
        voiceResource = VoiceResource(httpClient)
        webhookResource = WebhookResource(httpClient)
    }

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