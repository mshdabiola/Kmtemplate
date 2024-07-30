/*
 *abiola 2022
 */

package com.mshdabiola.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.NoteRepository
import com.mshdabiola.model.Note
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, FlowPreview::class)
class DetailViewModel constructor(
    // savedStateHandle: SavedStateHandle,
    id: Long,
    private val noteRepository: NoteRepository,
) : ViewModel() {

    private val noteId = id

    private val note = MutableStateFlow<Note?>(Note())

    val title = TextFieldState()
    val content = TextFieldState()

    init {
        viewModelScope.launch {
            if (noteId > 0) {
                val initNOte = noteRepository.getOne(noteId)
                    .first()
                note.update { initNOte }

                if (initNOte != null) {
                    title.edit {
                        this.append(initNOte.title)
                    }
                    content.edit {
                        append(initNOte.content)
                    }
                }
            }

            note
                .collectLatest {
                    onContentChange(it)
                }
        }

        viewModelScope.launch() {
            snapshotFlow { title.text }
                .debounce(500)
                .collectLatest { text ->
                    note.update { it?.copy(title = text.toString()) }
                }
        }
        viewModelScope.launch() {
            snapshotFlow { content.text }
                .debounce(500)
                .collectLatest { text ->
                    note.update { it?.copy(content = text.toString()) }
                }
        }
    }

    private suspend fun onContentChange(note: Note?) {
        if (note?.title?.isNotBlank() == true || note?.content?.isNotBlank() == true) {
            val id = noteRepository.upsert(note)
            if (note.id == -1L) {
                this@DetailViewModel.note.update { note.copy(id = id) }
            }
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            note.value?.id?.let { noteRepository.delete(it) }
        }
    }
}
