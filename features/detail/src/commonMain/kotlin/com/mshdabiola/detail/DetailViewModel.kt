/*
 *abiola 2022
 */

package com.mshdabiola.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import com.mshdabiola.data.repository.IModelRepository
import com.mshdabiola.model.Note
import com.mshdabiola.ui.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailViewModel constructor(
    // savedStateHandle: SavedStateHandle,
    id: Long,
    private val noteRepository: IModelRepository,
) : ViewModel() {

    private val topicId = id

    private var _noteState = mutableStateOf(Note())
    val noteState: State<Note> = _noteState

    init {
        viewModelScope.launch {
            if (topicId > 0) {
                val note = noteRepository.getOne(topicId)
                    .first()
                if (note != null) {
                    _noteState.value = note
                }
            }
        }

        viewModelScope.launch() {
            snapshotFlow { noteState.value }
                .collectLatest {
                    if (it.id != null) {
                        noteRepository.upsert(it)
                    }
                }
        }
    }

    var job: Job? = null
    fun onTitleChange(text: String) {
        _noteState.value = noteState.value.copy(title = text)
        if (noteState.value.id == null) {
            job?.cancel()
            job = viewModelScope.launch {
                val id = getId()
                _noteState.value = noteState.value.copy(id = id)
            }
        }
    }

    fun onContentChange(text: String) {
        _noteState.value = noteState.value.copy(content = text)
        if (noteState.value.id == null) {
            job?.cancel()
            job = viewModelScope.launch {
                val id = getId()
                _noteState.value = noteState.value.copy(id = id)
            }
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            noteState.value.id?.let { noteRepository.delete(it) }
        }
    }

    private suspend fun getId(): Long {
        return noteRepository.upsert(Note())
    }
}
