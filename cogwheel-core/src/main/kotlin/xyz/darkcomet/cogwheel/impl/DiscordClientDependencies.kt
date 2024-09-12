package xyz.darkcomet.cogwheel.impl

import xyz.darkcomet.cogwheel.network.http.CwHttpClient
import xyz.darkcomet.cogwheel.network.http.impl.KtorHttpClient

internal data class DiscordClientDependencies(
    var cwHttpClientFactory: CwHttpClient.Factory = KtorHttpClient.Factory(),
)