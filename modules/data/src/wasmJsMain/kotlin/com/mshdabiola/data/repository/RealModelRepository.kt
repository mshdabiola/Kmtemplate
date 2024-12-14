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
            .map { notes1 -> notes1.first { it.id == id } }
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
