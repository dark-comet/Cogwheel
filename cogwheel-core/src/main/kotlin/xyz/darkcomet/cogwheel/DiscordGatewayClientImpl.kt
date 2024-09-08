package xyz.darkcomet.cogwheel

import xyz.darkcomet.cogwheel.implementation.authentication.AuthenticationMode

internal class DiscordGatewayClientImpl
internal constructor (
    authenticationMode: AuthenticationMode,
    clientVersion: String?,
    clientUrl: String?
) : DiscordClientImpl(authenticationMode, clientVersion, clientUrl), DiscordGatewayClient {

}