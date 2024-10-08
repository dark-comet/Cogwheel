package xyz.darkcomet.cogwheel.impl.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class CwConfiguration(
    val clientName: String,
    val clientVersion: String,
    val clientUrl: String,
    val discordApiUrl: String,
    val discordApiVersion: Int
) {
    companion object {
        fun load(): CwConfiguration {
            val resourceStream = CwConfiguration::class.java.getResourceAsStream("/cogwheel.json")!!

            resourceStream.bufferedReader(Charsets.UTF_8).use {
                val jsonString = it.readText()
                return Json.decodeFromString(serializer(), jsonString)
            }
        }
    }
}