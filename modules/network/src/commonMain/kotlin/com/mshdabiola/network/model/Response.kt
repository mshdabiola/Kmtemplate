package com.mshdabiola.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    @SerialName("batchcomplete")
    val batchcomplete: Boolean? = null,
    @SerialName("continue")
    val continueX: Continue? = null,
    @SerialName("query")
    val query: Query? = null,
)
