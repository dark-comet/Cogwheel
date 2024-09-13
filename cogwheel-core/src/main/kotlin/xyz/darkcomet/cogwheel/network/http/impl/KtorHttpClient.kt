package xyz.darkcomet.cogwheel.network.http.impl

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import okhttp3.OkHttpClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.darkcomet.cogwheel.impl.DiscordClientSettings
import xyz.darkcomet.cogwheel.impl.models.CwConfiguration
import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.CwHttpMethod
import xyz.darkcomet.cogwheel.network.http.CwHttpRequest
import xyz.darkcomet.cogwheel.network.http.CwHttpResponse
import java.util.*
import java.util.concurrent.TimeUnit

internal class KtorHttpClient(
    private val settings: DiscordClientSettings,
    private val config: CwConfiguration
) : CwHttpClient {
    
    private val logger: Logger = LoggerFactory.getLogger(KtorHttpClient::class.java)
    
    private val httpClient: HttpClient
    private val httpClientUserAgentHeaderValue: String
    
    init {
        httpClient = HttpClient(OkHttp) {
            if (settings.gatewayEnabled) {
                engine {
                    // TODO: Work out the specifics
                    preconfigured = OkHttpClient.Builder().pingInterval(20, TimeUnit.SECONDS).build()
                }
            }
        }
        
        httpClientUserAgentHeaderValue = getUserAgentHeaderValue()
        logger.info("User-Agent: $httpClientUserAgentHeaderValue")
    }

    private fun getUserAgentHeaderValue(): String {
        // Hard-coded values for this library
        val libName = config.clientName
        val libVersion = config.clientVersion
        val libUrl = config.clientUrl

        // Specific to client project settings
        val officialUrl = settings.customClientUrl ?: libUrl
        val officialVersion = settings.customClientVersion ?: libVersion

        return "DiscordBot ($officialUrl, $officialVersion) $libName/$libVersion"
    }

    override suspend fun submitHttp(request: CwHttpRequest): CwHttpResponse.Raw {
        val endpointUrl = getEndpointUrl(request.endpointPath)
        
        val contentType = if (request.bodyContent != null) ContentType.Application.Json else ContentType.Any
        val requestBody = request.bodyContent ?: ""
        
        val requestId = UUID.randomUUID()
        
        logger.debug("Submitting HttpRequest: id={}, {} {}, bodyContent={}", requestId, request.method, endpointUrl, requestBody)
        
        val httpResponse = httpClient.request(endpointUrl) {
            method = getHttpMethod(request.method)
            
            request.queryParameters.entries.forEach {
                parameter(it.key, it.value)
            }
            
            headers {
                append(HttpHeaders.Authorization, settings.authMode.getAuthorizationHeaderValue())
                append(HttpHeaders.UserAgent, httpClientUserAgentHeaderValue)
            }
            
            contentType(contentType)
            setBody(requestBody)
        }
        
        val responseBody = httpResponse.bodyAsText()

        logger.debug("Received HttpResponse for id={}, {}, bodyContent={}", requestId, httpResponse.toString(), responseBody)

        return KtorHttpResponse.Raw(httpResponse, responseBody)
    }
    
    private fun getEndpointUrl(endpointUrl: String): String {
        var url = config.discordApiUrl
        
        if (url.endsWith("/")) {
            url = url.substring(0, url.length - 1)
        }

        url += "/v${config.discordApiVersion}${endpointUrl}"
        
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
        override fun create(settings: DiscordClientSettings, config: CwConfiguration): CwHttpClient {
            return KtorHttpClient(settings, config)
        }
    }
}