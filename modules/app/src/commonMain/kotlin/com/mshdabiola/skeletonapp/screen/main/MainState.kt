package com.mshdabiola.skeletonapp.screen.main

import com.mshdabiola.model.Model

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

data class ModelUiState(val id: Long?, val name: String)

fun Model.asModelUiState() = ModelUiState(id, name)
