package xyz.darkcomet.cogwheel.network.http.entities.application

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class InstallParamsEntity(
    @Required val scopes: List<String>,
    @Required val permissions: String
)