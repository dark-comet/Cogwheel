package xyz.darkcomet.cogwheel.network.http.resources

import xyz.darkcomet.cogwheel.models.Snowflake
import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.CwHttpResponse
import xyz.darkcomet.cogwheel.network.entities.guild.GuildAuditLogEntity

class AuditLogResource internal constructor(httpClient: CwHttpClient) {

    fun getAuditLog(guildId: Snowflake): CwHttpResponse {
        TODO("Not implemented yet")
    }
    
}