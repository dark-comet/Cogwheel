package xyz.darkcomet.cogwheel.network.http

class CwHttpResponse<T>(
    val statusCode: Int,
    val statusMessage: String,
    val success: Boolean,
    val responseEntity: T?
) {
}