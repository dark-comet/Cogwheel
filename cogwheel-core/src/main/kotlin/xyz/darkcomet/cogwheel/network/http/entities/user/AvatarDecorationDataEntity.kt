package xyz.darkcomet.cogwheel.network.http.entities.user

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.darkcomet.cogwheel.models.Snowflake

@Serializable
data class AvatarDecorationDataEntity(
    @Required val asset: String,
    @Required @SerialName("sku_id") val skuId: Snowflake
)