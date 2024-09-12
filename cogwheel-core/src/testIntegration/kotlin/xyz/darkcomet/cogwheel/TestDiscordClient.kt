package xyz.darkcomet.cogwheel

class TestDiscordClient {
    companion object {
        private const val TEST_BOT_TOKEN_ENV_KEY = "CW_CORE_CI_BOT_TOKEN"
        private const val TEST_OAUTH2_TOKEN_ENV_KEY = "CW_CORE_CI_OAUTH2_TOKEN"
        
        fun fromEnvBotToken(init: (DiscordClientBuilder.() -> Unit)? = null): DiscordClient {
            val token = System.getenv(TEST_BOT_TOKEN_ENV_KEY)
            
            if (token == null || token.isBlank()) {
                throw IllegalArgumentException("No bot token set for ENV key: $TEST_BOT_TOKEN_ENV_KEY")
            }
            
            return DiscordClient.fromBotToken(token, init)
        }
        
        fun fromEnvOauth2Token(init: (DiscordClientBuilder.() -> Unit)?): DiscordClient {
            val token = System.getenv(TEST_OAUTH2_TOKEN_ENV_KEY)

            if (token == null || token.isBlank()) {
                throw IllegalArgumentException("No OAuth2 token set for ENV key: $TEST_OAUTH2_TOKEN_ENV_KEY")
            }

            return DiscordClient.fromOAuth2Token(token, init)
        }
    }
}