package com.mshdabiola.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Page(
    @SerialName("description")
    val description: String? = null,
    @SerialName("descriptionsource")
    val descriptionsource: String? = null,
    @SerialName("index")
    val index: Int? = null,
    @SerialName("ns")
    val ns: Int? = null,
    @SerialName("pageid")
    val pageid: Int? = null,
    @SerialName("thumbnail")
    val thumbnail: Thumbnail? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("imageinfo")
    val imageinfo: List<Imageinfo?>? = null,
    @SerialName("imagerepository")
    val imagerepository: String? = null,
)
