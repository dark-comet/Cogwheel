package xyz.darkcomet.cogwheel.network.http

import xyz.darkcomet.cogwheel.impl.authentication.AuthenticationMode
import xyz.darkcomet.cogwheel.impl.models.CwBaseConfiguration
import xyz.darkcomet.cogwheel.impl.models.CwCustomConfiguration

internal interface CwHttpClient {
    
    suspend fun submit(request: CwHttpRequest): CwHttpResponse.Raw

    @FunctionalInterface
    interface Factory {
        fun create(authMode: AuthenticationMode, configBase: CwBaseConfiguration, configCustom: CwCustomConfiguration): CwHttpClient
    }
}