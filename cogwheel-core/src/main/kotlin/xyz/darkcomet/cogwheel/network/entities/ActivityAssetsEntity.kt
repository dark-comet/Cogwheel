package xyz.darkcomet.cogwheel.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityAssetsEntity(
    @SerialName("large_image") val largeImage: String? = null,
    @SerialName("large_text") val largeText: String? = null,
    @SerialName("small_image") val smallImage: String? = null,
    @SerialName("small_text") val smallText: String? = null,
)
