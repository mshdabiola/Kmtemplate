package com.mshdabiola.ui

import com.mshdabiola.model.Note

// sealed interface MainState {
//    data class Show(val models: List<ModelUiState>) : MainState
//    object Error : MainState
//
//    object Loading : MainState
// }

data class MainState(
    val name: String = "abiola",
//    val messages: ImmutableList<Notify> = emptyList<Notify>().toImmutableList()
)

fun Note.asNoteUiState() = NoteUiState(id, title, content)
