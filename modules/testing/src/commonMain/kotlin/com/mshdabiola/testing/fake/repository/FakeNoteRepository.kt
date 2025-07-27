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
package com.mshdabiola.testing.fake.repository

import com.mshdabiola.data.repository.NoteRepository
import com.mshdabiola.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeNoteRepository : NoteRepository {

    private val notesFlow = MutableStateFlow<List<Note>>(emptyList())
    private var nextId = 1L // Start generating actual IDs from 1

    // Define the default ID constant
    companion object {
        const val DEFAULT_ID = -1L
    }

    override suspend fun upsert(note: Note): Long {
        val currentNotes = notesFlow.value.toMutableList()
        val newNoteId: Long

        // Check against the DEFAULT_ID for a new note
        if (note.id == DEFAULT_ID) { // New note (added null check for robustness)
            newNoteId = nextId++
            val newNote = note.copy(id = newNoteId)
            currentNotes.add(newNote)
            notesFlow.update { currentNotes }
        } else { // Existing note
            newNoteId = note.id // Assume ID is non-null and not DEFAULT_ID if it's an existing note
            val index = currentNotes.indexOfFirst { it.id == newNoteId }
            if (index != -1) {
                currentNotes[index] = note
            } else {
                // If an existing note (not DEFAULT_ID) isn't found, add it.
                // This could happen if a note was created with a specific ID elsewhere
                // and is now being upserted into this repository for the first time.
                currentNotes.add(note)
                // Ensure nextId is correctly positioned if this note's ID is high
                if (note.id >= nextId) {
                    nextId = note.id + 1
                }
            }
            notesFlow.update { currentNotes }
        }
        return newNoteId
    }

    override fun getAll(): Flow<List<Note>> {
        return notesFlow
    }

    override fun getOne(id: Long): Flow<Note?> {
        // No change needed here, it searches by the provided ID.
        // Ensure you don't try to fetch a note with DEFAULT_ID and expect it to be a valid stored ID.
        if (id == DEFAULT_ID) return kotlinx.coroutines.flow.flowOf(null) // Or handle as an error/empty flow
        return notesFlow.map { notes ->
            notes.find { it.id == id }
        }
    }

    override suspend fun delete(id: Long) {
        // No change needed here, it deletes by the provided ID.
        // Ensure you don't try to delete by DEFAULT_ID unless it's a specific state you want to handle.
        if (id == DEFAULT_ID) return // Or handle as an error
        notesFlow.update { currentNotes ->
            currentNotes.filterNot { it.id == id }
        }
    }

    fun setNotes(notes: List<Note>) {
        notesFlow.value = notes
        // Filter out DEFAULT_ID before finding max, as it's not a "real" persisted ID
        val maxId = notes.mapNotNull { it.id }.filter { it != DEFAULT_ID }.maxOrNull() ?: 0L
        nextId = maxId + 1
    }

    fun clearNotes() {
        notesFlow.value = emptyList()
        nextId = 1L // Reset nextId to start from 1
    }
}
