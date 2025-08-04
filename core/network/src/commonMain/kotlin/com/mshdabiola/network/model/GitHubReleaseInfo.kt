package com.mshdabiola.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubReleaseInfo(
    @SerialName("tag_name")
    val tagName: String? = null,
    @SerialName("name")
    val releaseName: String? = null,
    @SerialName("body")
    val body: String? = null,
    @SerialName("html_url")
    val htmlUrl: String? = null,
    @SerialName("assets")
    val assets: List<Asset?>?=null,
)
