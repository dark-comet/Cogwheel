package xyz.darkcomet.cogwheel.implementation.authentication

internal class OAuth2BearerAuthenticationMode(private val token: String) : AuthenticationMode {
    
    override fun getAuthorizationHeaderValue(): String {
        return "Bearer $token"
    }
    
}