package xyz.darkcomet.cogwheel.network.http.resources

import xyz.darkcomet.cogwheel.models.Snowflake
import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.CwHttpMethod
import xyz.darkcomet.cogwheel.network.http.CwHttpRequest
import xyz.darkcomet.cogwheel.network.http.CwHttpResponse
import xyz.darkcomet.cogwheel.network.http.entities.application.ApplicationEntity
import xyz.darkcomet.cogwheel.network.http.entities.application.ApplicationInstanceEntity
import xyz.darkcomet.cogwheel.network.http.entities.application.ApplicationRoleConnectionMetadataEntity
import xyz.darkcomet.cogwheel.network.http.requests.application.ModifyCurrentApplicationRequest
import xyz.darkcomet.cogwheel.network.http.requests.application.UpdateApplicationRoleConnectionRecordsRequest

class ApplicationResource
internal constructor(private val httpClient: CwHttpClient) {
    
    suspend fun getCurrent(): CwHttpResponse<ApplicationEntity> {
        val request = CwHttpRequest.Builder(CwHttpMethod.GET, "/applications/@me").build()
        return httpClient.submit(request, ApplicationEntity.serializer())
    }
    
    fun modifyCurrent(request: ModifyCurrentApplicationRequest): CwHttpResponse<ApplicationEntity> {
        TODO("Not implemented yet")
    }
    
    fun getActivityInstance(applicationId: Snowflake): CwHttpResponse<ApplicationInstanceEntity> {
        TODO("Not implemented yet")
    }

    fun getRoleConnectionMetadataRecords(applicationId: Snowflake): CwHttpResponse<ApplicationRoleConnectionMetadataEntity> {
        TODO("Not implemented yet")
    }

    fun updateRoleConnectionMetadataRecords(applicationId: Snowflake, request: UpdateApplicationRoleConnectionRecordsRequest): CwHttpResponse<List<ApplicationRoleConnectionMetadataEntity>> {
        TODO("Not implemented yet")
    }
    
}