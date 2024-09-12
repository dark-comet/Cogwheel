package xyz.darkcomet.cogwheel

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DiscordBotIntegrationTest {
    
    @Test
    fun testIt() {
        val client = TestDiscordClient.fromEnvBotToken()
        val api = client.restApi()

        runBlocking {
            val response = api.application().getCurrent()
            assertEquals(true, response.success)
        }
    }
    
}