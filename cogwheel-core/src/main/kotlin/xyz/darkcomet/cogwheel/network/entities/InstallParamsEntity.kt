package xyz.darkcomet.cogwheel.network.entities

import kotlinx.serialization.Serializable

@Serializable
data class InstallParamsEntity(
    val scopes: List<String>,
    val permissions: String
)