package xyz.darkcomet.cogwheel.impl.authentication

internal class BotTokenAuthenticationMode(private val token: String) : AuthenticationMode {
    
    override fun getAuthorizationHeaderValue(): String {
        return "Bot $token"
    }

}