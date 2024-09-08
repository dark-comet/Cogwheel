package xyz.darkcomet.cogwheel.network.http.resources

import xyz.darkcomet.cogwheel.models.Channel
import xyz.darkcomet.cogwheel.models.Snowflake
import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.CwHttpResponse
import xyz.darkcomet.cogwheel.network.http.entities.*
import xyz.darkcomet.cogwheel.network.http.entities.channel.ChannelEntity
import xyz.darkcomet.cogwheel.network.http.entities.guild.*
import xyz.darkcomet.cogwheel.network.http.entities.permission.RoleEntity
import xyz.darkcomet.cogwheel.network.http.entities.voice.VoiceRegionEntity
import xyz.darkcomet.cogwheel.network.http.requests.guild.*
import xyz.darkcomet.cogwheel.network.http.responses.guild.*

class GuildResource
internal constructor(private val httpClient: CwHttpClient) {
    
    fun create(request: CreateGuildRequest): CwHttpResponse<GuildEntity> {
        TODO("Not implemented yet")
    }
    
    fun get(guildId: Snowflake, withCounts: Boolean = false): CwHttpResponse<GuildEntity> {
        TODO("Not implemented yet")
    }
    
    fun getPreview(guildId: Snowflake): CwHttpResponse<GuildPreviewEntity> {
        TODO("Not implemented yet")
    }
    
    fun modify(guildId: Snowflake, request: ModifyGuildRequest): CwHttpResponse<GuildEntity> {
        TODO("Not implemented yet")
    }
    
    fun delete(guildId: Snowflake): CwHttpResponse<GuildEntity> {
        TODO("Not implemented yet")
    }
    
    fun getChannels(guildId: Snowflake): CwHttpResponse<List<ChannelEntity>> {
        TODO("Not implemented yet")
    }
    
    fun createChannel(guildId: Snowflake, request: CreateGuildChannelRequest, auditLogReason: String?): CwHttpResponse<Channel> {
        TODO("Not implemented yet")
    }
    
    fun modifyChannelPositions(guildId: Snowflake, request: ModifyGuildChannelPositionsRequest): CwHttpResponse<Void> {
        TODO("Not implemented yet")
    }
    
    fun listActiveThreads(guildId: Snowflake): CwHttpResponse<ListGuildActiveThreadsResponse> {
        TODO("Not implemented yet")
    }
    
    fun getMember(guildId: Snowflake, userId: Snowflake): CwHttpResponse<GuildMemberEntity> {
        TODO("Not implemented yet")
    }
    
    fun listMembers(guildId: Snowflake, limit: Int = 1, after: Snowflake? = null): CwHttpResponse<List<GuildMemberEntity>> {
        TODO("Not implemented yet")
    }
    
    fun searchMembers(guildId: Snowflake, query: String, limit: Int = 1): CwHttpResponse<List<GuildMemberEntity>> {
        TODO("Not implemented yet")
    }
    
    fun addMember(guildId: Snowflake, request: AddGuildMemberRequest, auditLogReason: String?): CwHttpResponse<AddGuildMemberResponse> {
        TODO("Not implemented yet")
    }
    
    fun modifyMember(guildId: Snowflake, userId: Snowflake, request: ModifyGuildMemberRequest, auditLogReason: String?): CwHttpResponse<GuildMemberEntity> {
        TODO("Not implemented yet")
    }
    
    fun modifyCurrentMember(guildId: Snowflake, auditLogReason: String?): CwHttpResponse<GuildMemberEntity> {
        TODO("Not implemented yet")
    }
    
    @Deprecated("Use modifyCurrentMember() instead")
    fun modifyCurrentUserNickname(guildId: Snowflake, auditLogReason: String?): CwHttpResponse<String> {
        TODO("Not implemented yet")
    }
    
    fun addMemberRole(guildId: Snowflake, userId: Snowflake, roleId: Snowflake, auditLogReason: String?): CwHttpResponse<Void> {
        TODO("Not implemented yet")
    }
    
    fun removeMemberRole(guildId: Snowflake, userId: Snowflake, roleId: Snowflake, auditLogReason: String?): CwHttpResponse<Void> {
        TODO("Not implemented yet")
    }
    
    fun removeMember(guildId: Snowflake, userId: Snowflake, auditLogReason: String?): CwHttpResponse<Void> {
        TODO("Not implemented yet")
    }
    
    fun getBans(guildId: Snowflake, limit: Int? = null, before: Snowflake? = null, after: Snowflake? = null): CwHttpResponse<List<GuildBanEntity>> {
        TODO("Not implemented yet")
    }
    
    fun getBan(guildId: Snowflake, userId: Snowflake): CwHttpResponse<GuildBanEntity> {
        TODO("Not implemented yet")
    }
    
    fun createBan(guildId: Snowflake, userId: Snowflake, request: CreateGuildBanRequest, auditLogReason: String?): CwHttpResponse<Void> {
        TODO("Not implemented yet")
    }
    
    fun removeBan(guildId: Snowflake, userId: Snowflake, auditLogReason: String?): CwHttpResponse<Void> {
        TODO("Not implemented yet")
    }
    
    fun bulkBan(guildId: Snowflake, request: BulkGuildBanRequest, auditLogReason: String?): CwHttpResponse<BulkGuildBanResponse> {
        TODO("Not implemented yet")
    }
    
    fun getRoles(guildId: Snowflake, auditLogReason: String?): CwHttpResponse<List<RoleEntity>> {
        TODO("Not implemented yet")
    }
    
    fun getRole(guildId: Snowflake, roleId: Snowflake): CwHttpResponse<RoleEntity> {
        TODO("Not implemented yet")
    }
    
    fun createRole(guildId: Snowflake, request: CreateGuildRoleRequest, auditLogReason: String?): CwHttpResponse<RoleEntity> {
        TODO("Not implemented yet")
    }
    
    fun modifyRolePosition(guildId: Snowflake, request: List<ModifyGuildRolePositionRequest>, auditLogReason: String?): CwHttpResponse<List<RoleEntity>> {
        TODO("Not implemented yet")
    }
    
    fun modifyRole(guildId: Snowflake, roleId: Snowflake, request: ModifyGuildRoleRequest, auditLogReason: String?): CwHttpResponse<RoleEntity> {
        TODO("Not implemented yet")
    }
    
    fun modifyMfaLevel(guildId: Snowflake, request: ModifyGuildMfaLevelRequest, auditLogReason: String?): CwHttpResponse<Int> {
        TODO("Not implemented yet")
    }
    
    fun deleteRole(guildId: Snowflake, roleId: Snowflake, auditLogReason: String?): CwHttpResponse<Void> {
        TODO("Not implemented yet")
    }
    
    fun getPruneCount(guildId: Snowflake, days: Int? = null, includeRoleIds: List<Snowflake>? = null): CwHttpResponse<GetGuildPruneCountResponse> {
        TODO("Not implemented yet")
    }
    
    fun beginPrune(guildId: Snowflake, request: BeginGuildPruneRequest, auditLogReason: String?): CwHttpResponse<BeginGuildPruneResponse> {
        TODO("Not implemented yet")
    }
    
    fun getVoiceRegions(guildId: Snowflake): CwHttpResponse<List<VoiceRegionEntity>> {
        TODO("Not implemented yet")
    }
    
    fun getInvites(guildId: Snowflake): CwHttpResponse<List<InviteEntity>> {
        TODO("Not implemented yet")
    }
    
    fun getIntegrations(guildId: Snowflake): CwHttpResponse<List<GuildIntegrationEntity>> {
        TODO("Not implemented yet")
    }
    
    fun deleteIntegration(guildId: Snowflake, integrationId: Snowflake, auditLogReason: String?): CwHttpResponse<Void> {
        TODO("Not implemented yet")
    }
    
    fun getWidgetSettings(guildId: Snowflake): CwHttpResponse<GuildWidgetSettingsEntity> {
        TODO("Not implemented yet")
    }
    
    fun modifyWidget(guildId: Snowflake, request: ModifyGuildWidgetRequest, auditLogReason: String?): CwHttpResponse<GuildWidgetSettingsEntity> {
        TODO("Not implemented yet")
    }
    
    fun getWidget(guildId: Snowflake): CwHttpResponse<GuildWidgetEntity> {
        TODO("Not implemented yet")
    }
    
    fun getVanityUrl(guildId: Snowflake): CwHttpResponse<PartialInviteEntity> {
        TODO("Not implemented yet")
    }
    
    fun getWidgetImage(guildId: Snowflake, style: String? = null): CwHttpResponse<Void> {
        TODO("Not implemented yet")
    }
    
    fun getWelcomeScreen(guildId: Snowflake): CwHttpResponse<GuildWelcomeScreenEntity> {
        TODO("Not implemented yet")
    }
    
    fun modifyWelcomeScreen(guildId: Snowflake, request: ModifyGuildWelcomeScreenRequest): CwHttpResponse<GuildWelcomeScreenEntity> {
        TODO("Not implemented yet")
    }
    
    fun getOnboarding(guildId: Snowflake): CwHttpResponse<GuildOnboardingEntity> {
        TODO("Not implemented yet")
    }
    
    fun modifyOnboarding(guildId: Snowflake, request: ModifyGuildOnboardingRequest, auditLogReason: String?): CwHttpResponse<GuildOnboardingEntity> {
        TODO("Not implemented yet")
    }
    
}