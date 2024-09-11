package xyz.darkcomet.cogwheel.network.http

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import xyz.darkcomet.cogwheel.implementation.authentication.AuthenticationMode
import xyz.darkcomet.cogwheel.implementation.models.CwBaseConfiguration
import xyz.darkcomet.cogwheel.implementation.models.CwCustomConfiguration

internal class KtorHttpClient(
    private val authenticationMode: AuthenticationMode,
    private val configuration: CwBaseConfiguration,
    private val configurationOverride: CwCustomConfiguration
) : CwHttpClient {
    
    private val httpClient: HttpClient
    private val httpClientUserAgentHeaderValue: String
    
    private val json = Json { ignoreUnknownKeys = true }

    init {
        httpClient = HttpClient(OkHttp)
        httpClientUserAgentHeaderValue = getUserAgentHeaderValue()
    }

    private fun getUserAgentHeaderValue(): String {
        // Hard-coded values for this library
        val libName = configuration.clientName
        val libVersion = configuration.clientVersion
        val libUrl = configuration.clientUrl

        // Specific to client project settings
        val officialUrl = configurationOverride.clientUrl ?: libUrl
        val officialVersion = configurationOverride.clientVersion ?: libVersion

        return "DiscordBot ($officialUrl, $officialVersion) $libName/$libVersion"
    }

    override suspend fun <T> submit(
        request: CwHttpRequest, 
        responseEntityDeserializer: DeserializationStrategy<T>?
    ): CwHttpResponse<T> {
    
        val endpointUrl = getEndpointUrl(request.endpointPath)
        
        val contentType = if (request.bodyContent != null) ContentType.Application.Json else ContentType.Any
        val bodyContent = request.bodyContent ?: ""
        
        val httpResponse = httpClient.request(endpointUrl) {
            headers {
                append(HttpHeaders.Authorization, authenticationMode.getAuthorizationHeaderValue())
                append(HttpHeaders.UserAgent, httpClientUserAgentHeaderValue)
            }
            
            method = getHttpMethod(request.method)
            
            contentType(contentType)
            setBody(bodyContent)
        }
        
        return CwHttpResponse(
            statusCode = httpResponse.status.value,
            statusMessage = httpResponse.status.description,
            success = httpResponse.status.isSuccess(),
            responseEntity = if (responseEntityDeserializer != null) toResponseEntity(httpResponse, responseEntityDeserializer) else null
        )
    }
    
    private suspend fun <T> toResponseEntity(
        httpResponse: HttpResponse,
        responseEntityDeserializer: DeserializationStrategy<T>
    ) : T? {
        val type = httpResponse.contentType()
        
        if (type == null) {
            return null
        }
        
        if (type != ContentType.Application.Json) {
            return null
        }

        val bodyContent = httpResponse.bodyAsText()
        
        return json.decodeFromString(responseEntityDeserializer, bodyContent)
    }

    private fun getEndpointUrl(endpointUrl: String): String {
        var url = configuration.discordApiUrl
        
        if (url.endsWith("/")) {
            url = url.substring(0, url.length - 1)
        }

        url += "/v${configuration.discordApiVersion}${endpointUrl}"
        
        return url
    }

    private fun getHttpMethod(request: CwHttpMethod) : HttpMethod {
        return when (request) {
            CwHttpMethod.GET -> HttpMethod.Get
            CwHttpMethod.PUT -> HttpMethod.Put
            CwHttpMethod.POST -> HttpMethod.Post
            CwHttpMethod.PATCH -> HttpMethod.Patch
            CwHttpMethod.DELETE -> HttpMethod.Delete
        }
    }

}