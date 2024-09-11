package xyz.darkcomet.cogwheel.implementation.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
internal data class CwBaseConfiguration(
    val clientName: String,
    val clientVersion: String,
    val clientUrl: String,
    val discordApiUrl: String,
    val discordApiVersion: Int
) {
    companion object {
        fun load(): CwBaseConfiguration {
            val resourceStream = CwBaseConfiguration::class.java.getResourceAsStream("/cogwheel.json")!!

            resourceStream.bufferedReader(Charsets.UTF_8).use {
                val jsonString = it.readText()
                return Json.decodeFromString(serializer(), jsonString)
            }
        }
    }
}