/*
 *abiola 2022
 */

package com.mshdabiola.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.NoteRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.model.Note
import com.mshdabiola.ui.MainState
import com.mshdabiola.ui.asNoteUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel constructor(
    private val userDataRepository: UserDataRepository,
    private val modelRepository: NoteRepository,

) : ViewModel() {

//    val timeLine = modelRepository
//        .imagePagingData()

    val notes = modelRepository
        .getAll()
        .map { it.map { it.asNoteUiState() }.toImmutableList() }

//    val pager = modelRepository
//        .notePagingData()
//        .map { it.map { it.asNoteUiState() } }
//        .cachedIn(viewModelScope)

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    init {
        viewModelScope.launch {
            // delay(2000)
            addNotify("Add Model")
            addNotify("remove model")
        }
    }

    fun addName(name: String) {
        insert(Note(title = name, id = Random(34).nextLong()))
    }

    fun insert(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            modelRepository.upsert(note)
        }
    }

    private fun addNotify(text: String) {
//        val notifies = mainState.value.messages.toMutableList()
//
//        notifies.add(Notify(message = text, callback = ::onNotifyDelive))
//        _mainState.update {
//            it.copy(messages = notifies.toImmutableList())
        // }
    }

    private fun onNotifyDelive() {
//        val notifies = mainState.value.messages.toMutableList()
//
//        notifies.removeFirst()
//        _mainState.value = mainState.value.copy(messages = notifies.toImmutableList())
    }
}
