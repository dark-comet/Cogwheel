package xyz.darkcomet.cogwheel.models

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SnowflakeTest {

    @Test
    fun testTimestampProperty_discordApiExample_isCorrect() {
        val snowflake = Snowflake(175928847299117063L)
        
        assertEquals(1462015105796L, snowflake.timestampMs)
    }
    
    @Test
    fun testWorkerIdProperty_discordApiExample_isCorrect() {
        val snowflake = Snowflake(175928847299117063L)

        assertEquals(1, snowflake.workerId)
    }

    @Test
    fun testProcessIdProperty_discordApiExample_isCorrect() {
        val snowflake = Snowflake(175928847299117063L)

        assertEquals(0, snowflake.processId)
    }

    @Test
    fun testIncrementProperty_discordApiExample_isCorrect() {
        val snowflake = Snowflake(175928847299117063L)

        assertEquals(7, snowflake.increment)
    }
    
    @Test
    fun testDeserialization_discordApiExample_works() {
        val snowflake: Snowflake = Json.decodeFromString("\"175928847299117063\"")

        assertEquals(1462015105796L, snowflake.timestampMs)
        assertEquals(1, snowflake.workerId)
        assertEquals(0, snowflake.processId)
        assertEquals(7, snowflake.increment)
    }
    
    @Test
    fun testSerialization_discordApiExample_works() {
        val snowflake = Snowflake(175928847299117063L)
        
        val string = Json.encodeToString(Snowflake.serializer(), snowflake)
        
        assertEquals("\"175928847299117063\"", string)
    }
}