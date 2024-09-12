package xyz.darkcomet.cogwheel.network.http.resources

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import xyz.darkcomet.cogwheel.TestDiscordClient
import xyz.darkcomet.cogwheel.network.http.requests.application.ModifyCurrentApplicationRequest
import java.util.UUID

class ApplicationResourceIntegrationTest {
    
    private val client = TestDiscordClient.fromEnvBotToken()
    private val api = client.restApi()
    
    @Test
    fun testGetCurrent() {
        runBlocking {
            val response = api.application().getCurrent()
            assertEquals(true, response.raw.success)
        }
    }
    
    @Test
    fun testEditCurrent() {
        runBlocking { 
            val request = ModifyCurrentApplicationRequest.create {
                description = "test description: ${UUID.randomUUID()}"
            }
            
            val response = api.application().editCurrent(request)
            
            assertEquals(true, response.raw.success)
            assertNotNull(response.entity)
            assertEquals(request.description, response.entity!!.description)
        }
    }
}