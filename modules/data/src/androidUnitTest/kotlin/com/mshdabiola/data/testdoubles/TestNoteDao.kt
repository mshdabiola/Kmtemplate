/*
 *abiola 2024
 */

package com.mshdabiola.data.testdoubles

import com.mshdabiola.database.dao.NoteDao
import com.mshdabiola.database.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestNoteDao : NoteDao {
    private val data = MutableList(4) { index ->
        NoteEntity(index.toLong(), "title", "Content")
    }
    override suspend fun upsert(noteEntity: NoteEntity): Long {
        val id = data.size.toLong()
        data.add(noteEntity.copy(id = id))
        return id
    }

    override fun getAll(): Flow<List<NoteEntity>> {
        return flowOf(data)
    }

    override fun getOne(id: Long): Flow<NoteEntity?> {
        return flowOf(data.singleOrNull { it.id == id })
    }

    override suspend fun delete(id: Long) {
        data.removeIf { it.id == id }
    }

    override suspend fun insertAll(users: List<NoteEntity>) {
    }

    override suspend fun clearAll() {
    }
}
