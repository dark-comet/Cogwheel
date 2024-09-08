package xyz.darkcomet.cogwheel.network.http

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.http.*
import xyz.darkcomet.cogwheel.implementation.authentication.AuthenticationMode
import xyz.darkcomet.cogwheel.implementation.models.CwBaseConfiguration
import xyz.darkcomet.cogwheel.implementation.models.CwCustomConfiguration

internal class CwHttpClient(
    private val authenticationMode: AuthenticationMode,
    private val configuration: CwBaseConfiguration,
    private val configurationOverride: CwCustomConfiguration
) {
    
    private val httpClient: HttpClient
    
    init {
        httpClient = HttpClient(OkHttp) {
            install(UserAgent) {
                val url = configurationOverride.clientUrl ?: configuration.clientUrl
                val version = configurationOverride.clientVersion ?: configuration.clientVersion

                agent = "DiscordBot ($url, $version) cogwheel/${configuration.clientVersion}"
            }

            headers {
                append(HttpHeaders.Authorization, authenticationMode.getAuthorizationHeaderValue())
            }
        }
    }
    
    suspend fun <T> submit(request: CwHttpRequest): CwHttpResponse<T> {
        TODO("Not implemented")
    }
    
}