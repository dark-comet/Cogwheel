package xyz.darkcomet.cogwheel.network.http.requests.application

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.darkcomet.cogwheel.network.entities.application.InstallParamsEntity

@Serializable
data class ModifyCurrentApplicationRequest(
    @SerialName("custom_install_url") val customInstallUrl: String,
    val description: String,
    @SerialName("role_connection_verification_url") val roleConnectionVerificationUrl: String,
    @SerialName("install_params") val installParams: InstallParamsEntity,
//    @SerialName("integration_types_config") val integrationTypesConfig: Map<String, String>,
    val flags: Int,
    val icon: String?, // image data
    @SerialName("cover_image") val coverImage: String?, // image data
    @SerialName("interactions_endpoint_url") val interactionsEndpointUrl: String,
    val tags: List<String>
)