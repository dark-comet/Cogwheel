package xyz.darkcomet.cogwheel.network.http.resources

import xyz.darkcomet.cogwheel.models.Snowflake
import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.CwHttpMethod
import xyz.darkcomet.cogwheel.network.http.CwHttpRequest
import xyz.darkcomet.cogwheel.network.http.CwHttpResponse
import xyz.darkcomet.cogwheel.network.http.requests.application.ModifyCurrentApplicationRequest
import xyz.darkcomet.cogwheel.network.http.requests.application.UpdateApplicationRoleConnectionRecordsRequest

class ApplicationResource
internal constructor(private val httpClient: CwHttpClient) {
    
    suspend fun getCurrent(): CwHttpResponse {
        val request = CwHttpRequest.create(CwHttpMethod.GET, "/applications/@me")
        return httpClient.submit(request)
    }
    
    suspend fun modifyCurrent(request: ModifyCurrentApplicationRequest): CwHttpResponse {
        val httpRequest = CwHttpRequest.create(CwHttpMethod.PATCH, "/applications/@me") {
            jsonParams(request, ModifyCurrentApplicationRequest.serializer())
        }
        
        return httpClient.submit(httpRequest)
    }
    
    fun getActivityInstance(applicationId: Snowflake): CwHttpResponse {
        TODO("Not implemented yet")
    }

    fun getRoleConnectionMetadataRecords(applicationId: Snowflake): CwHttpResponse {
        TODO("Not implemented yet")
    }

    fun updateRoleConnectionMetadataRecords(applicationId: Snowflake, request: UpdateApplicationRoleConnectionRecordsRequest): CwHttpResponse {
        TODO("Not implemented yet")
    }
    
}