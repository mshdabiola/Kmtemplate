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

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.NoteRepository
import com.mshdabiola.model.Note
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class DetailViewModel(
    initId: Long,
    private val noteRepository: NoteRepository,
) : ViewModel() {

    private val idFlow = MutableStateFlow(initId)

    val initDetailState = DetailState(id = initId)

    private val titleFlow = snapshotFlow { initDetailState.title.text }
        .debounce(300)

    private val detailFlow = snapshotFlow { initDetailState.detail.text }
        .debounce(300)

    private var isInit = true

    val detailState = combine(
        idFlow,
        titleFlow,
        detailFlow,
    ) { id, title, detail ->

        when {
            id == -1L && isInit -> {
                val newId = noteRepository.upsert(Note())
                idFlow.update { newId }

                isInit = false
                initDetailState.copy(id = newId)
            }

            isInit -> {
                val note = noteRepository.getOne(id).first()!!
                isInit = false

                initDetailState.title.edit {
                    append(note.title)
                }
                initDetailState.detail.edit {
                    append(note.content)
                }
                initDetailState.copy(id = note.id)
            }

            else -> {
                val note = noteRepository.getOne(id).first()

                val newNote = note!!.copy(title = title.toString(), content = detail.toString())
                if (newNote != note) {
                    noteRepository.upsert(newNote)
                }
                initDetailState.copy(id = note.id)
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = initDetailState,
        )

    fun onDelete() {
        viewModelScope.launch {
            val id = idFlow.first()
            if (id != -1L) {
                noteRepository.delete(id)
            }
        }
    }
}
