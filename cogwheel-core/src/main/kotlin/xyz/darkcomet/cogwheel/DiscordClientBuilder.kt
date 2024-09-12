package xyz.darkcomet.cogwheel

import xyz.darkcomet.cogwheel.impl.DiscordClientDependencies
import xyz.darkcomet.cogwheel.impl.DiscordClientImpl
import xyz.darkcomet.cogwheel.impl.authentication.AuthenticationMode

class DiscordClientBuilder 
internal constructor(private val authenticationMode: AuthenticationMode) {
    
    var clientVersion: String? = null
    var clientUrl: String? = null
    
    private val dependencies: DiscordClientDependencies = DiscordClientDependencies()
    
    internal fun internalDependencies(init: DiscordClientDependencies.() -> Unit) {
        init.invoke(dependencies)
    }

    internal fun build(): DiscordClient {
        return DiscordClientImpl(dependencies, authenticationMode, clientVersion, clientUrl)
    }
}