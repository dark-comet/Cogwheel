package xyz.darkcomet.cogwheel.network.http.impl

import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import xyz.darkcomet.cogwheel.network.http.CwHttpResponse

internal class KtorHttpResponse(private val httpResponse: HttpResponse) : CwHttpResponse {
    
    override val statusCode: Int
        get() = httpResponse.status.value
    
    override val statusMessage: String
        get() = httpResponse.status.description
    
    override val success: Boolean
        get() = httpResponse.status.isSuccess()
    

    override suspend fun <T> unwrapEntity(deserializationStrategy: DeserializationStrategy<T>): T? {
        if (!success || httpResponse.contentType() != ContentType.Application.Json) {
            return null
        }
        
        val bodyText = httpResponse.bodyAsText()
        
        return JSON_DESERIALIZER.decodeFromString(deserializationStrategy, bodyText)
    }
    
    companion object {
        private val JSON_DESERIALIZER = Json { ignoreUnknownKeys = true }
    }

}