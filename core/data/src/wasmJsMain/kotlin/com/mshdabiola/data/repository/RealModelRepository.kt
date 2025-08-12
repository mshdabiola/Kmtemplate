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
package com.mshdabiola.data.repository

import com.mshdabiola.model.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.withContext

internal class RealModelRepository(
    private val ioDispatcher: CoroutineDispatcher,
) : NoteRepository {
    private val notes = MutableStateFlow(listOf<Note>())

    override suspend fun upsert(note: Note): Long {
        return withContext(ioDispatcher) {
            var id = note.id
            val notesMutable = notes.value.toMutableList()
            if (id == -1L) {
                id = notes.value.maxByOrNull { it.id }?.id?.plus(1) ?: 1
                notesMutable.add(note.copy(id = id))
            } else {
                notesMutable.removeAll { it.id == id }
                notesMutable.add(note.copy(id = id))
                id = note.id
            }

            notes.update { notesMutable }

            return@withContext id
        }
    }

    override fun getAll(): Flow<List<Note>> {
        return notes
    }

    override fun getOne(id: Long): Flow<Note?> {
        return notes
            .map { notes1 -> notes1.firstOrNull { it.id == id } }
            .flowOn(ioDispatcher)
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            notes.updateAndGet { notes1 ->

                notes1.toMutableList().apply { removeAll { it.id == id } }
            }
        }
    }
}
