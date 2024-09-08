package xyz.darkcomet.cogwheel

class DiscordGatewayClientBuilder
internal constructor(
    private val httpClientBuilder: DiscordClientBuilder,
    private var clientVersion: String?,
    private var clientUrl: String?
) {
    fun setClientVersion(version: String?): DiscordGatewayClientBuilder {
        clientVersion = version
        return this
    }

    fun setClientUrl(clientUrl: String?): DiscordGatewayClientBuilder {
        this.clientUrl = clientUrl
        return this
    }

    fun build(): DiscordGatewayClient {
        return DiscordGatewayClientImpl(
            httpClientBuilder.authenticationMode, 
            clientVersion, 
            clientUrl
        )
    }
}