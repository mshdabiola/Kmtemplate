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
package com.mshdabiola

import com.mshdabiola.database.dao.NoteDao
import com.mshdabiola.database.model.NoteEntity
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

internal class RealNoteDataSource : NoteDao {
    private val noteDataSource: KStore<List<NoteEntity>> = storeOf(key = "note_db", default = listOf())

    override suspend fun upsert(noteEntity: NoteEntity): Long {
        var resultingId: Long? = null
        noteDataSource.update { list ->
            val currentList = list ?: emptyList()
            if (noteEntity.id == null) {
                val newId = (currentList.maxByOrNull { it.id ?: 0 }?.id ?: 0) + 1
                resultingId = newId
                currentList + noteEntity.copy(id = newId)
            } else {
                resultingId = noteEntity.id
                val index = currentList.indexOfFirst { it.id == noteEntity.id }
                if (index != -1) {
                    currentList.toMutableList().apply { this[index] = noteEntity }
                } else {
                    currentList + noteEntity
                }
            }
        }
        return resultingId!!
    }

    override suspend fun insert(noteEntity: NoteEntity): Long {
        var newId = -1L
        noteDataSource.update { list ->
            val currentList = list ?: emptyList()
            newId = (currentList.maxByOrNull { it.id ?: 0 }?.id ?: 0) + 1
            currentList + noteEntity.copy(id = newId)
        }
        return newId
    }

    override suspend fun update(noteEntity: NoteEntity) {
        noteDataSource.update { list ->
            val currentList = list ?: return@update list
            val index = currentList.indexOfFirst { it.id == noteEntity.id }
            if (index != -1) {
                currentList.toMutableList().apply { this[index] = noteEntity }
            } else {
                currentList
            }
        }
    }

    override fun getAll(): Flow<List<NoteEntity>> {
        return noteDataSource
            .updates
            .onEach { println("noteDataSource: $it") }
            .map { it ?: emptyList() }
    }

    override fun getOne(id: Long): Flow<NoteEntity?> {
        return noteDataSource
            .updates
            .map { list -> list?.firstOrNull { it.id == id } }
    }

    override suspend fun delete(id: Long) {
        noteDataSource.update { it?.filter { it.id != id } ?: listOf() }
    }

    override suspend fun insertAll(notes: List<NoteEntity>) {
        noteDataSource.update { list ->
            val currentList = list ?: emptyList()
            val noteMap = currentList.associateBy { it.id }.toMutableMap()
            var maxId = currentList.maxByOrNull { it.id ?: 0 }?.id ?: 0

            for (note in notes) {
                if (note.id == null) {
                    maxId++
                    val newNote = note.copy(id = maxId)
                    noteMap[newNote.id] = newNote
                } else {
                    noteMap[note.id] = note
                }
            }
            noteMap.values.toList()
        }
    }

    override suspend fun clearAll() {
        noteDataSource.update { listOf() }
    }
}
