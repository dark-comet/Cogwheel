package xyz.darkcomet.cogwheel.impl.authentication

internal class BotToken(private val token: String) : Token {
    
    override val value: String
        get() = token

    override fun getAuthorizationHeaderValue(): String {
        return "Bot $token"
    }

}