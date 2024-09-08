package xyz.darkcomet.cogwheel.network.http.resources

import xyz.darkcomet.cogwheel.models.Snowflake
import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.CwHttpResponse
import xyz.darkcomet.cogwheel.network.http.entities.automod.GuildAutoModerationRuleEntity
import xyz.darkcomet.cogwheel.network.http.requests.automod.CreateGuildAutoModerationRuleRequest

class AutoModerationResource internal constructor(httpClient: CwHttpClient) {

    fun listAutoModerationRules(guildId: Snowflake): CwHttpResponse<List<GuildAutoModerationRuleEntity>> {
        TODO("Not implemented yet")
    }

    fun getAutoModerationRule(guildId: Snowflake, autoModerationRuleId: Snowflake): CwHttpResponse<GuildAutoModerationRuleEntity> {
        TODO("Not implemented yet")
    }

    fun createAutoModerationRule(guildId: Snowflake, request: CreateGuildAutoModerationRuleRequest, auditLogReason: String?): CwHttpResponse<GuildAutoModerationRuleEntity> {
        TODO("Not implemented yet")
    }

    fun modifyAutoModerationRule(guildId: Snowflake, autoModerationRuleId: Snowflake, auditLogReason: String?): CwHttpResponse<GuildAutoModerationRuleEntity> {
        TODO("Not implemented yet")
    }

    fun deleteAutoModerationRule(guildId: Snowflake, autoModerationRuleId: Snowflake, auditLogReason: String?): CwHttpResponse<Void> {
        TODO("Not implemented yet")
    }
    
}