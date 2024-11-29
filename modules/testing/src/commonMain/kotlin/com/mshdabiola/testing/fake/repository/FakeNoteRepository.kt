/*
 *abiola 2024
 */

package com.mshdabiola.testing.fake.repository

import com.mshdabiola.data.repository.NoteRepository
import com.mshdabiola.model.Note
import com.mshdabiola.testing.fake.notes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeNoteRepository : NoteRepository {

    private val data = MutableStateFlow(notes)
    override suspend fun upsert(note: Note): Long {
        data.update {
            if (note.id == -1L) {
                it.toMutableList().apply {
                    add(note)
                }
            } else {
                val idx = it.indexOfFirst { it.id == note.id }
                it.toMutableList().apply {
                    add(idx, note)
                }
            }
        }

        return 1
    }

    override fun getAll(): Flow<List<Note>> {
        return data
    }

    override fun getOne(id: Long): Flow<Note?> {
        return data.map { it.firstOrNull { it.id == id } }
    }

    override suspend fun delete(id: Long) {
        data.update {
            it.toMutableList().apply {
                removeIf { it.id == id }
            }
        }
    }
}
