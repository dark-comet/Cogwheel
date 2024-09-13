package xyz.darkcomet.cogwheel.impl

import xyz.darkcomet.cogwheel.impl.authentication.Token
import xyz.darkcomet.cogwheel.models.Intents
import xyz.darkcomet.cogwheel.network.gateway.CwGatewayClient
import xyz.darkcomet.cogwheel.network.gateway.impl.KtorGatewayClient
import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.impl.KtorHttpClient

internal data class DiscordClientSettings(
    val token: Token,
    var cwHttpClientFactory: CwHttpClient.Factory = KtorHttpClient.Factory(),
    var cwGatewayClientFactory: CwGatewayClient.Factory = KtorGatewayClient.Factory(),
    var customClientVersion: String? = null,
    var customClientUrl: String? = null,
    var gatewayEnabled: Boolean = false,
    var gatewayIntents: Intents = Intents() // TODO
)