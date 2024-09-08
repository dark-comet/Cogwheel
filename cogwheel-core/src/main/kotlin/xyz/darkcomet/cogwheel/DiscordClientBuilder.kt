package xyz.darkcomet.cogwheel

import xyz.darkcomet.cogwheel.implementation.authentication.AuthenticationMode

class DiscordClientBuilder 
internal constructor(internal val authenticationMode: AuthenticationMode) {
    
    private var clientVersion: String? = null
    private var clientUrl: String? = null

    fun setClientVersion(version: String?): DiscordClientBuilder {
        clientVersion = version
        return this
    }

    fun setClientUrl(clientUrl: String?): DiscordClientBuilder {
        this.clientUrl = clientUrl
        return this
    }

    fun withGateway() : DiscordGatewayClientBuilder {
        return DiscordGatewayClientBuilder(this, clientVersion, clientUrl)
    }

    fun build(): DiscordClient {
        return DiscordClientImpl(authenticationMode, clientVersion, clientUrl)
    }
}