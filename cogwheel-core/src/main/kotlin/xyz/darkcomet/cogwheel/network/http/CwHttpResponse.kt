package xyz.darkcomet.cogwheel.network.http

import kotlinx.serialization.DeserializationStrategy

interface CwHttpResponse {

    val statusCode: Int
    val statusMessage: String
    val success: Boolean
    
    suspend fun <T> unwrapEntity(deserializationStrategy: DeserializationStrategy<T>) : T?
    
}