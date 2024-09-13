package xyz.darkcomet.cogwheel.network.http.impl

import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import xyz.darkcomet.cogwheel.network.http.CwHttpResponse

internal class KtorHttpResponse<T>(
    override val raw: CwHttpResponse.Raw,
    override val entity: T?
) : CwHttpResponse<T> {

    internal class Raw(
        private val httpResponse: HttpResponse, 
        override val bodyContent: String
    ) : CwHttpResponse.Raw {
        
        override val success: Boolean
            get() = httpResponse.status.isSuccess()
        
        override val statusCode: Int
            get() = httpResponse.status.value
        
        override val statusMessage: String
            get() = httpResponse.status.description

        override fun toEmptyContent(): CwHttpResponse<Void> {
            return KtorHttpResponse(this, entity = null)
        }

        override fun <T> toEntity(strategy: CwHttpResponse.Raw.() -> T?): CwHttpResponse<T> {
            val entity = strategy.invoke(this)
            return KtorHttpResponse(this, entity)
        }

        override fun <T> toEntity(deserializationStrategy: DeserializationStrategy<T>): CwHttpResponse<T> {
            if (bodyContent.isBlank() || ContentType.Application.Json != httpResponse.contentType()) {
                return KtorHttpResponse(this, entity = null)
            }

            val entity = JSON_DESERIALIZER.decodeFromString(deserializationStrategy, bodyContent)
            return KtorHttpResponse(this, entity)
        }
        
    }

    companion object {
        private val JSON_DESERIALIZER = Json { ignoreUnknownKeys = true }
    }
}