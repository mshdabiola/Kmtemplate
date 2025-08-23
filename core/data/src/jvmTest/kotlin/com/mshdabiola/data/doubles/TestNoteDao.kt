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
package com.mshdabiola.data.doubles

import com.mshdabiola.database.dao.NoteDao
import com.mshdabiola.database.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class TestNoteDao : NoteDao {
    private val notesFlow = MutableStateFlow<List<NoteEntity>>(emptyList())
    private var nextId = 1L

    override suspend fun upsert(noteEntity: NoteEntity): Long {
        val currentNotes = notesFlow.value.toMutableList()
        val newEntity = if (noteEntity.id == null) noteEntity.copy(id = nextId++) else noteEntity
        if (noteEntity.id == null) {
            currentNotes.add(newEntity)
            notesFlow.value = currentNotes
        } else {
            val index = currentNotes.indexOfFirst { it.id == newEntity.id }
            if (index != -1) {
                currentNotes[index] = newEntity
            } else {
                currentNotes.add(index, newEntity)
            }
            notesFlow.value = currentNotes
        }

        return newEntity.id ?: 0L
    }

    override suspend fun insert(noteEntity: NoteEntity): Long {
        return upsert(noteEntity)
    }

    override suspend fun update(noteEntity: NoteEntity) {
        upsert(noteEntity)
    }

    override fun getAll(): Flow<List<NoteEntity>> {
        return notesFlow.asStateFlow()
    }

    override fun getOne(id: Long): Flow<NoteEntity?> {
        return notesFlow.asStateFlow().map { notes -> notes.find { it.id == id } }
    }

    override suspend fun delete(id: Long) {
        val currentNotes = notesFlow.value.toMutableList()
        currentNotes.removeAll { it.id == id }
        notesFlow.value = currentNotes
    }

    override suspend fun insertAll(notes: List<NoteEntity>) {
        notes.forEach { upsert(it) }
    }

    override suspend fun clearAll() {
        notesFlow.value = emptyList()
        nextId = 1L // Reset id counter
    }
}
