/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.detail

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.NoteRepository
import com.mshdabiola.model.Note
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class DetailViewModel(
    // savedStateHandle: SavedStateHandle,
    id: Long,
    private val noteRepository: NoteRepository,
) : ViewModel() {
    private val note = MutableStateFlow<Note?>(Note())

    val title = TextFieldState()
    val content = TextFieldState()

    private val _state = MutableStateFlow<DetailState>(DetailState.Loading())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            if (id > 0) {
                val initNOte =
                    noteRepository.getOne(id)
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
            _state.update { DetailState.Success(id) }

            note
                .collectLatest {
                    onContentChange(it)
                }
        }

        viewModelScope.launch {
            snapshotFlow { title.text }
                .debounce(500)
                .collectLatest { text ->
                    note.update { it?.copy(title = text.toString()) }
                }
        }
        viewModelScope.launch {
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
