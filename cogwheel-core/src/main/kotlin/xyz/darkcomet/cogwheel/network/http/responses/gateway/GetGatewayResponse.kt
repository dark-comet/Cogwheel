package xyz.darkcomet.cogwheel.network.http.responses.gateway

import kotlinx.serialization.Serializable

@Serializable
data class GetGatewayResponse(val url: String)