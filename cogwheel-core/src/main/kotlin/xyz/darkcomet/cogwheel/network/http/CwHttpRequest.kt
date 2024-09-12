package xyz.darkcomet.cogwheel.network.http

import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import java.util.*
import kotlin.collections.HashMap

data class CwHttpRequest 
internal constructor(
    val method: CwHttpMethod,
    val endpointPath: String,
    val queryParameters: Map<String, String>,
    val bodyContent: String?
) {
    internal class Builder(private val method: CwHttpMethod, private val urlPath: String)
    {
        private val queryParameters = HashMap<String, String>()
        private var bodyContent: String? = null
        
        fun queryStringParam(key: String, value: String) : Builder {
            queryParameters[key] = value
            return this
        }
        
        fun <T> jsonParams(value: T, serializationStrategy: SerializationStrategy<T>) : Builder {
            bodyContent = JSON_SERIALIZER.encodeToString(serializationStrategy, value)
            return this
        }
        
        internal fun build() : CwHttpRequest {
            val queryParamsCopy = Collections.unmodifiableMap(HashMap(queryParameters))
            return CwHttpRequest(method, urlPath, queryParamsCopy, bodyContent)
        }
    }
    
    companion object {
        private val JSON_SERIALIZER = Json { encodeDefaults = false }
        
        internal fun create(method: CwHttpMethod, urlPath: String, init: (Builder.() -> Unit)? = null): CwHttpRequest {
            val builder = Builder(method, urlPath)
            init?.invoke(builder)
            
            return builder.build()
        }
    }
}