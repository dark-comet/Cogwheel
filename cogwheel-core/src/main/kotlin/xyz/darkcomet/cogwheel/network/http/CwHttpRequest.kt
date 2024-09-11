package xyz.darkcomet.cogwheel.network.http

import java.util.*
import kotlin.collections.HashMap

class CwHttpRequest private constructor(
    val method: CwHttpMethod,
    val endpointPath: String,
    private val queryParameters: Map<String, String>,
    val bodyContent: String?
) {
    
    class Builder(private val method: CwHttpMethod, private val urlPath: String) {
        
        private val queryParameters = HashMap<String, String>()
        private val bodyContent: String? = null
        
        fun queryParameter(key: String, value: String) : Builder {
            queryParameters[key] = value
            return this
        }
        
        fun build() : CwHttpRequest {
            val queryParamsCopy = Collections.unmodifiableMap(HashMap(queryParameters))
            return CwHttpRequest(method, urlPath, queryParamsCopy, bodyContent)
        }
    }
    
}