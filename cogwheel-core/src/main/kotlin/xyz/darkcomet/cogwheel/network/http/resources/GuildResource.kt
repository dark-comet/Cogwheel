package xyz.darkcomet.cogwheel.network.http.resources

import xyz.darkcomet.cogwheel.models.Snowflake
import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.CwHttpResponse
import xyz.darkcomet.cogwheel.network.http.requests.guild.*

class GuildResource
internal constructor(private val httpClient: CwHttpClient) {
    
//    fun create(request: CreateGuildRequest): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun get(guildId: Snowflake, withCounts: Boolean = false): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getPreview(guildId: Snowflake): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun modify(guildId: Snowflake, request: ModifyGuildRequest): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun delete(guildId: Snowflake): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getChannels(guildId: Snowflake): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun createChannel(guildId: Snowflake, request: CreateGuildChannelRequest, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun modifyChannelPositions(guildId: Snowflake, request: ModifyGuildChannelPositionsRequest): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun listActiveThreads(guildId: Snowflake): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getMember(guildId: Snowflake, userId: Snowflake): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun listMembers(guildId: Snowflake, limit: Int = 1, after: Snowflake? = null): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun searchMembers(guildId: Snowflake, query: String, limit: Int = 1): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun addMember(guildId: Snowflake, request: AddGuildMemberRequest, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun modifyMember(guildId: Snowflake, userId: Snowflake, request: ModifyGuildMemberRequest, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun modifyCurrentMember(guildId: Snowflake, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    @Deprecated("Use modifyCurrentMember() instead")
//    fun modifyCurrentUserNickname(guildId: Snowflake, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun addMemberRole(guildId: Snowflake, userId: Snowflake, roleId: Snowflake, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun removeMemberRole(guildId: Snowflake, userId: Snowflake, roleId: Snowflake, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun removeMember(guildId: Snowflake, userId: Snowflake, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getBans(guildId: Snowflake, limit: Int? = null, before: Snowflake? = null, after: Snowflake? = null): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getBan(guildId: Snowflake, userId: Snowflake): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun createBan(guildId: Snowflake, userId: Snowflake, request: CreateGuildBanRequest, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun removeBan(guildId: Snowflake, userId: Snowflake, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun bulkBan(guildId: Snowflake, request: BulkGuildBanRequest, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getRoles(guildId: Snowflake, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getRole(guildId: Snowflake, roleId: Snowflake): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun createRole(guildId: Snowflake, request: CreateGuildRoleRequest, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun modifyRolePosition(guildId: Snowflake, request: List<ModifyGuildRolePositionRequest>, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun modifyRole(guildId: Snowflake, roleId: Snowflake, request: ModifyGuildRoleRequest, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun modifyMfaLevel(guildId: Snowflake, request: ModifyGuildMfaLevelRequest, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun deleteRole(guildId: Snowflake, roleId: Snowflake, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getPruneCount(guildId: Snowflake, days: Int? = null, includeRoleIds: List<Snowflake>? = null): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun beginPrune(guildId: Snowflake, request: BeginGuildPruneRequest, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getVoiceRegions(guildId: Snowflake): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getInvites(guildId: Snowflake): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getIntegrations(guildId: Snowflake): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun deleteIntegration(guildId: Snowflake, integrationId: Snowflake, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getWidgetSettings(guildId: Snowflake): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun modifyWidget(guildId: Snowflake, request: ModifyGuildWidgetRequest, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getWidget(guildId: Snowflake): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getVanityUrl(guildId: Snowflake): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getWidgetImage(guildId: Snowflake, style: String? = null): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getWelcomeScreen(guildId: Snowflake): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun modifyWelcomeScreen(guildId: Snowflake, request: ModifyGuildWelcomeScreenRequest): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun getOnboarding(guildId: Snowflake): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//    
//    fun modifyOnboarding(guildId: Snowflake, request: ModifyGuildOnboardingRequest, auditLogReason: String?): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
    
}