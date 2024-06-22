/*
 *abiola 2022
 */

package com.mshdabiola.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.model.Result
import com.mshdabiola.data.model.asResult
import com.mshdabiola.data.repository.NoteRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.ui.NoteUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel constructor(
    private val userDataRepository: UserDataRepository,
    private val modelRepository: NoteRepository,

) : ViewModel() {

    val feedUiMainState: StateFlow<Result<List<NoteUiState>>> =
        modelRepository.getAll()
            .map { notes -> notes.map { NoteUiState(it.id!!, it.title, it.content) } }
            .asResult()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Result.Loading,
            )
}
