package xyz.darkcomet.cogwheel.network.gateway.impl

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import okhttp3.OkHttpClient
import xyz.darkcomet.cogwheel.impl.authentication.AuthenticationMode
import xyz.darkcomet.cogwheel.models.Intents
import xyz.darkcomet.cogwheel.network.gateway.CwGatewayClient
import java.util.concurrent.TimeUnit

class KtorGatewayClient 
private constructor(
    private val authMode: AuthenticationMode, 
    private val intents: Intents
) : CwGatewayClient {

    private val httpClient: HttpClient

    private var lastFetchedGatewayUrl: String? = null
    
    init {
        httpClient = HttpClient(OkHttp) {
            install(WebSockets)
            engine {
                // TODO: Work out the specifics
                preconfigured = OkHttpClient.Builder().pingInterval(20, TimeUnit.SECONDS).build()
            }
        }
    }
    
    override suspend fun startGatewayConnection(urlFetcher: suspend () -> String) {
        if (lastFetchedGatewayUrl == null) {
            lastFetchedGatewayUrl = urlFetcher.invoke().replace("wss://", "")
        }

        httpClient.wss(method = HttpMethod.Get, host = lastFetchedGatewayUrl) {
//            while (isActive) {
//                
//            }
            println("Connected to $lastFetchedGatewayUrl!")
        }
    }
    
    class Factory : CwGatewayClient.Factory {
        override fun create(authMode: AuthenticationMode, intents: Intents): CwGatewayClient {
            return KtorGatewayClient(authMode, intents)
        }

    }
}