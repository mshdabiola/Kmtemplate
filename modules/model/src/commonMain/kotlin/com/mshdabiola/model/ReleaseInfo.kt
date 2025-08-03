package com.mshdabiola.model

sealed class ReleaseInfo {
    data class Error(val message: String) : ReleaseInfo()
    data class Success(
        val tagName: String,
        val releaseName: String,
        val body: String,
        val asset: String,
    ) :
        ReleaseInfo()

}
