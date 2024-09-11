package xyz.darkcomet.cogwheel

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DiscordBotIntegrationTest {
    
    @Test
    fun testIt() {
        val client = DiscordClient.fromBotToken("<redacted>").build()
        
        runBlocking {
            val response = client.application().getCurrent()
            assertEquals(true, response.success)
        }
    }
    
}