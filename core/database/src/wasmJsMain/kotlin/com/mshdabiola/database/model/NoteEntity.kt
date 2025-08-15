package com.mshdabiola.database.model

import kotlinx.serialization.Serializable

@Serializable
data class NoteEntity(
    val id: Long?,
    val title: String,
    val content: String,
)
