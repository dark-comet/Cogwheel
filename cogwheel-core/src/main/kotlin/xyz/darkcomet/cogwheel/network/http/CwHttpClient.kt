package xyz.darkcomet.cogwheel.network.http

import xyz.darkcomet.cogwheel.impl.DiscordClientSettings
import xyz.darkcomet.cogwheel.impl.models.CwConfiguration

internal interface CwHttpClient {
    
    suspend fun submit(request: CwHttpRequest): CwHttpResponse.Raw

    @FunctionalInterface
    interface Factory {
        fun create(settings: DiscordClientSettings, config: CwConfiguration): CwHttpClient
    }
}