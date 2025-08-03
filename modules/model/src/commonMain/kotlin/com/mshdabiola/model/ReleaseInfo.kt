package com.mshdabiola.model

 data class ReleaseInfo(
    val tagName: String,
    val releaseName: String,
    val body: String,
    val assets: List<String>,
)
