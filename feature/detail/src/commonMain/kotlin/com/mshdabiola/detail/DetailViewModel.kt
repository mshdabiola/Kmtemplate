/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import co.touchlab.kermit.Logger
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
    private val logger: Logger,
) : ViewModel() {

    private val idFlow = MutableStateFlow(initId)

    val initDetailState = DetailState(id = -1)

    private val titleFlow = snapshotFlow { initDetailState.title.text }
        .debounce(500)

    private val detailFlow = snapshotFlow { initDetailState.detail.text }
        .debounce(500)

    private var isInit = true

    val detailState = combine(
        idFlow,
        titleFlow,
        detailFlow,
    ) { id, title, detail ->

        logger.i { "detailState: $id, $title, $detail init " }
        when {

            id > 0 && isInit -> {

                initDetailState.copy(id = id)
                val note = noteRepository.getOne(id).first() ?: Note(id = id)
                isInit = false

                initDetailState.title.edit {
                    append(note.title)
                }
                initDetailState.detail.edit {
                    append(note.content)
                }
                initDetailState.copy(id = id)
            }

            else -> {

                val note = noteRepository.getOne(id).first() ?: Note()

                val newNote = note.copy(title = title.toString(), content = detail.toString())
                if (newNote != note) {
                    val newId = noteRepository.upsert(newNote)
                    if (id == -1L) {
                        isInit = false

                        idFlow.update { newId }
                    }
                }
                initDetailState.copy(id)
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
