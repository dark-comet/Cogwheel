package xyz.darkcomet.cogwheel.network.http.api

import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.CwHttpMethod
import xyz.darkcomet.cogwheel.network.http.CwHttpRequest
import xyz.darkcomet.cogwheel.network.http.CwHttpResponse
import xyz.darkcomet.cogwheel.network.http.responses.gateway.GetGatewayResponse

class GatewayApi
internal constructor(private val httpClient: CwHttpClient) {
    suspend fun get(): CwHttpResponse<GetGatewayResponse> {
        val request = CwHttpRequest.create(CwHttpMethod.GET, "/gateway")
        return httpClient.submitHttp(request).toEntity(GetGatewayResponse.serializer())
    }
}