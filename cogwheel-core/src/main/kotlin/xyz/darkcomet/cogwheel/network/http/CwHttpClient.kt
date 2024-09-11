package xyz.darkcomet.cogwheel.network.http

import kotlinx.serialization.DeserializationStrategy

internal interface CwHttpClient {
    
    suspend fun <T> submit(request: CwHttpRequest, responseEntityDeserializer: DeserializationStrategy<T>?): CwHttpResponse<T>

}