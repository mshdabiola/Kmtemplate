package com.mshdabiola.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Thumbnail(
    @SerialName("height")
    val height: Int? = null,
    @SerialName("source")
    val source: String? = null,
    @SerialName("width")
    val width: Int? = null,
)
