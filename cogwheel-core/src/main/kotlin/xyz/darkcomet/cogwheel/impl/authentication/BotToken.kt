package xyz.darkcomet.cogwheel.impl.authentication

internal class BotToken(private val token: String) : Token {
    
    override fun getAuthorizationHeaderValue(): String {
        return "Bot $token"
    }

}