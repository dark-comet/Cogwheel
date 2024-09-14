package xyz.darkcomet.cogwheel.impl.authentication

internal class OAuth2Token(private val token: String) : Token {
    
    override val value: String
        get() = token

    override fun getAuthorizationHeaderValue(): String {
        return "Bearer $token"
    }
    
}