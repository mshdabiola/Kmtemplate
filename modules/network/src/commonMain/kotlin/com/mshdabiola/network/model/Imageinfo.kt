package com.mshdabiola.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Imageinfo(
    @SerialName("descriptionshorturl")
    val descriptionshorturl: String? = null,
    @SerialName("descriptionurl")
    val descriptionurl: String? = null,
    @SerialName("mediatype")
    val mediatype: String? = null,
    @SerialName("mime")
    val mime: String? = null,
    @SerialName("timestamp")
    val timestamp: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("user")
    val user: String? = null,
    @SerialName("userid")
    val userid: Int? = null,
    @SerialName("sha1")
    val id: String? = null,
)
