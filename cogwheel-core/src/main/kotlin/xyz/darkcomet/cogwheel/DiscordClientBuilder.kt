package xyz.darkcomet.cogwheel

import xyz.darkcomet.cogwheel.impl.DiscordClientImpl
import xyz.darkcomet.cogwheel.impl.DiscordClientSettings
import xyz.darkcomet.cogwheel.impl.authentication.AuthenticationMode
import xyz.darkcomet.cogwheel.models.Intents
import xyz.darkcomet.cogwheel.network.http.CwHttpClient

class DiscordClientBuilder 
internal constructor(private val authenticationMode: AuthenticationMode) {
    
    var clientVersion: String?
        get() = settings.customClientVersion
        set(value) { settings.customClientVersion = value }
    
    var clientUrl: String?
        get() = settings.customClientUrl
        set(value) { settings.customClientUrl = value }
    
    internal var cwHttpClientFactory: CwHttpClient.Factory
        get() = settings.cwHttpClientFactory
        set(value) { settings.cwHttpClientFactory = value }
    
    private val settings = DiscordClientSettings(authenticationMode)
    
    fun useGateway(intents: Intents) {
        settings.gatewayEnabled = true
        settings.gatewayIntents = intents
    }
    
    fun isGatewayEnabled() = settings.gatewayEnabled

    internal fun build(): DiscordClient {
        return DiscordClientImpl(settings)
    }
}