package xyz.darkcomet.cogwheel.models

class Intents(val value: Int) {
    companion object {
        val GUILDS = Intents(1)
        val GUILD_MEMBERS = Intents(1 shl 1)
        val GUILD_MODERATION = Intents(1 shl 2)
        val GUILD_EMOJIS_AND_STICKERS = Intents(1 shl 3)
        val GUILD_INTEGRATIONS = Intents(1 shl 4)
        val GUILD_WEBHOOKS = Intents(1 shl 5)
        val GUILD_INVITES = Intents(1 shl 6)
        val GUILD_VOICE_STATES = Intents(1 shl 7)
        val GUILD_PRESENCES = Intents(1 shl 8)
        val GUILD_MESSAGES = Intents(1 shl 9)
        val GUILD_MESSAGE_REACTIONS = Intents(1 shl 10)
        val GUILD_MESSAGE_TYPING = Intents(1 shl 11)
        val DIRECT_MESSAGES = Intents(1 shl 12)
        val DIRECT_MESSAGE_REACTIONS = Intents(1 shl 13)
        val DIRECT_MESSAGE_TYPING = Intents(1 shl 14)
        val MESSAGE_CONTENT = Intents(1 shl 15)
        val GUILD_SCHEDULED_EVENTS = Intents(1 shl 16)
        val AUTO_MODERATION_CONFIGURATION = Intents(1 shl 20)
        val AUTO_MODERATION_EXECUTION = Intents(1 shl 21)
        val GUILD_MESSAGE_POLLS = Intents(1 shl 24)
        val DIRECT_MESSAGE_POLLS = Intents(1 shl 25)
        
        fun of(vararg intents: Intents): Intents {
            var value = 0
            
            for (intent in intents) {
                value = value or intent.value
            }
            
            return Intents(value)
        }
        
        fun none(): Intents {
            return Intents(0)
        }
        
        fun all(): Intents {
            return Intents(Int.MAX_VALUE)
        }
    }
}