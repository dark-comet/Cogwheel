package xyz.darkcomet.cogwheel.network.gateway

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = GatewayOpCode.Serializer::class)
class GatewayOpCode(val code: Int) {

    companion object {
        val READY = GatewayOpCode(0)
        val HEARTBEAT = GatewayOpCode(1)
        val IDENTIFY = GatewayOpCode(2)
        val HELLO = GatewayOpCode(10)
    }
    
    override fun equals(other: Any?): Boolean {
        if (other !is GatewayOpCode) {
            return false
        }
        
        return code == other.code
    }

    override fun hashCode(): Int {
        return code;
    }

    override fun toString(): String {
        return code.toString()
    }
    
    class Serializer : KSerializer<GatewayOpCode> {
        override val descriptor: SerialDescriptor
            get() = PrimitiveSerialDescriptor("GatewayOpCode", PrimitiveKind.INT)

        override fun deserialize(decoder: Decoder): GatewayOpCode {
            val value = decoder.decodeInt()
            return GatewayOpCode(value)
        }

        override fun serialize(encoder: Encoder, value: GatewayOpCode) {
            encoder.encodeInt(value.code)
        }
    }
}