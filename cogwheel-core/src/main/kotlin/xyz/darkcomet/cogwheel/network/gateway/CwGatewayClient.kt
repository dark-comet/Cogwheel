package xyz.darkcomet.cogwheel.network.gateway

import xyz.darkcomet.cogwheel.impl.authentication.AuthenticationMode
import xyz.darkcomet.cogwheel.models.Intents

interface CwGatewayClient {
    
    suspend fun startGatewayConnection(gatewayUrlFetcher: suspend () -> String)

    @FunctionalInterface
    interface Factory {
        fun create(authMode: AuthenticationMode, intents: Intents): CwGatewayClient
    }
}