package xyz.darkcomet.cogwheel.network.entities.application

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class InstallParamsEntity(
    val scopes: List<String>,
    val permissions: String
)