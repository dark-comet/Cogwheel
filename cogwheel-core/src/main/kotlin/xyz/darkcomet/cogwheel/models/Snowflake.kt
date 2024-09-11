package xyz.darkcomet.cogwheel.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = Snowflake.Serializer::class)
class Snowflake(val value: Long) : Comparable<Snowflake> {

    constructor(value: String) : this(asLong(value))

    val timestampMs: Long
        get() = (value shr 22) + DISCORD_EPOCH_TIME_MS
    
    val workerId: Long
        get() = (value and 0x3E0000) shr 17
    
    val processId: Long
        get() = (value and 0x1F000) shr 12
    
    val increment: Long
        get() = value and 0xFFF
    
    override fun compareTo(other: Snowflake): Int {
        return timestampMs.compareTo(other.timestampMs)
    }
    
    companion object {
        private const val DISCORD_EPOCH_TIME_MS = 1_420_070_400_000L
        
        private fun asLong(value: String): Long {
            return value.toLongOrNull() ?: throw IllegalArgumentException("Invalid Snowflake ID: '$value'")
        }
    }

    class Serializer : KSerializer<Snowflake> {
        
        override val descriptor: SerialDescriptor
            get() = PrimitiveSerialDescriptor("Snowflake", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): Snowflake {
            val string = decoder.decodeString()
            return Snowflake(string)
        }

        override fun serialize(encoder: Encoder, value: Snowflake) {
            val string = value.value.toString()
            encoder.encodeString(string)
        }
        
    }
}