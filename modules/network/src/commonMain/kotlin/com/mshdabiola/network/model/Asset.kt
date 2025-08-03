package com.mshdabiola.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
 data class Asset(
    @SerialName("browser_download_url")
    val browserDownloadUrl: String?,
    @SerialName("size")
    val size: Int?
)
