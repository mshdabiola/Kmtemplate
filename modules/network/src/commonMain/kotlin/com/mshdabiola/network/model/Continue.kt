package com.mshdabiola.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Continue(
    @SerialName("continue")
    val continueX: String? = null,
    @SerialName("gsroffset")
    val gsroffset: Int? = null,
    @SerialName("grncontinue")
    val grncontinue: String? = null,
)
