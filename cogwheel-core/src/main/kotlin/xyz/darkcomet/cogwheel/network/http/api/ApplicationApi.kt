package xyz.darkcomet.cogwheel.network.http.api

import xyz.darkcomet.cogwheel.network.entities.ApplicationEntity
import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.CwHttpMethod
import xyz.darkcomet.cogwheel.network.http.CwHttpRequest
import xyz.darkcomet.cogwheel.network.http.CwHttpResponse
import xyz.darkcomet.cogwheel.network.http.requests.application.ModifyCurrentApplicationRequest

class ApplicationApi
internal constructor(private val httpClient: CwHttpClient) {
    
    suspend fun getCurrent(): CwHttpResponse<ApplicationEntity?> {
        val request = CwHttpRequest.create(CwHttpMethod.GET, "/applications/@me")
        return httpClient.submit(request).toEntity(ApplicationEntity.serializer())
    }
    
    suspend fun editCurrent(request: ModifyCurrentApplicationRequest): CwHttpResponse<ApplicationEntity?> {
        val httpRequest = CwHttpRequest.create(CwHttpMethod.PATCH, "/applications/@me") {
            jsonParams(request, ModifyCurrentApplicationRequest.serializer())
        }
        return httpClient.submit(httpRequest).toEntity(ApplicationEntity.serializer())
    }
    
//    fun getActivityInstance(applicationId: Snowflake, instanceId: String): CwHttpResponse<ApplicationInstanceEntity?> {
//        TODO("Not implemented yet")
//    }
//
//    fun getRoleConnectionMetadataRecords(applicationId: Snowflake): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
//
//    fun updateRoleConnectionMetadataRecords(applicationId: Snowflake, request: UpdateApplicationRoleConnectionRecordsRequest): CwHttpResponse {
//        TODO("Not implemented yet")
//    }
    
}