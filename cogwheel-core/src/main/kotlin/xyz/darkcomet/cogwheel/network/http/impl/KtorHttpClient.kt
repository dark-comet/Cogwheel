package xyz.darkcomet.cogwheel.network.http.impl

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import xyz.darkcomet.cogwheel.impl.authentication.AuthenticationMode
import xyz.darkcomet.cogwheel.impl.models.CwBaseConfiguration
import xyz.darkcomet.cogwheel.impl.models.CwCustomConfiguration
import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.CwHttpMethod
import xyz.darkcomet.cogwheel.network.http.CwHttpRequest
import xyz.darkcomet.cogwheel.network.http.CwHttpResponse

internal class KtorHttpClient(
    private val authenticationMode: AuthenticationMode,
    private val configuration: CwBaseConfiguration,
    private val configurationOverride: CwCustomConfiguration
) : CwHttpClient {
    
    private val httpClient: HttpClient
    private val httpClientUserAgentHeaderValue: String
    
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

    override suspend fun submit(request: CwHttpRequest): CwHttpResponse.Raw {
    
        val endpointUrl = getEndpointUrl(request.endpointPath)
        
        val contentType = if (request.bodyContent != null) ContentType.Application.Json else ContentType.Any
        val bodyContent = request.bodyContent ?: ""
        
        val httpResponse = httpClient.request(endpointUrl) {
            method = getHttpMethod(request.method)
            
            request.queryParameters.entries.forEach {
                parameter(it.key, it.value)
            }
            
            headers {
                append(HttpHeaders.Authorization, authenticationMode.getAuthorizationHeaderValue())
                append(HttpHeaders.UserAgent, httpClientUserAgentHeaderValue)
            }
            
            contentType(contentType)
            setBody(bodyContent)
        }

        val contentBody = httpResponse.bodyAsText()

        return KtorHttpResponse.Raw(httpResponse, contentBody)
    }
    
    private fun getEndpointUrl(endpointUrl: String): String {
        var url = configuration.discordApiUrl
        
        if (url.endsWith("/")) {
            url = url.substring(0, url.length - 1)
        }

        url += "/v${configuration.discordApiVersion}${endpointUrl}"
        
        return url
    }

    private fun getHttpMethod(request: CwHttpMethod): HttpMethod {
        return when (request) {
            CwHttpMethod.GET -> HttpMethod.Get
            CwHttpMethod.PUT -> HttpMethod.Put
            CwHttpMethod.POST -> HttpMethod.Post
            CwHttpMethod.PATCH -> HttpMethod.Patch
            CwHttpMethod.DELETE -> HttpMethod.Delete
        }
    }

    internal class Factory : CwHttpClient.Factory {
        override fun create(
            authMode: AuthenticationMode,
            configBase: CwBaseConfiguration,
            configCustom: CwCustomConfiguration
        ): CwHttpClient {
            return KtorHttpClient(authMode, configBase, configCustom)
        }
    }
}