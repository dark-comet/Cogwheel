package xyz.darkcomet.cogwheel.network.http

import kotlinx.serialization.DeserializationStrategy

interface CwHttpResponse<T> {
    val raw: Raw
    val entity: T?
    
    interface Raw {
        val success: Boolean
        val statusCode: Int
        val statusMessage: String
        val bodyContent: String?

        fun toEmptyContent(): CwHttpResponse<Void>
        fun <T> toEntity(strategy: Raw.() -> T?) : CwHttpResponse<T>
        fun <T> toEntity(deserializationStrategy: DeserializationStrategy<T>) : CwHttpResponse<T>
    }
}