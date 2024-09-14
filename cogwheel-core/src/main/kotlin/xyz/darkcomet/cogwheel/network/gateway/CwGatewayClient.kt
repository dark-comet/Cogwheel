package xyz.darkcomet.cogwheel.network.gateway

import xyz.darkcomet.cogwheel.impl.authentication.Token
import xyz.darkcomet.cogwheel.models.Intents
import xyz.darkcomet.cogwheel.network.CancellationToken

interface CwGatewayClient {
    
    suspend fun startGatewayConnection(
        cancellationToken: CancellationToken, 
        gatewayUrlFetcher: suspend () -> String?
    )

    @FunctionalInterface
    interface Factory {
        fun create(token: Token, intents: Intents, libName: String): CwGatewayClient
    }
}