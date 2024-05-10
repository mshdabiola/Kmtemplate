package com.mshdabiola.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Model(
    val id: Long,
    val name: String,
)
