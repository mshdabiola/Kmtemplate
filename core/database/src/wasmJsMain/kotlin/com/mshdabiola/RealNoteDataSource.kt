package com.mshdabiola

import com.mshdabiola.database.dao.NoteDao
import com.mshdabiola.database.model.NoteEntity
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

internal class RealNoteDataSource : NoteDao {
    private val noteDataSource: KStore<List<NoteEntity>> = storeOf(key = "userdata", default = listOf())

    override suspend fun upsert(noteEntity: NoteEntity): Long {

        if (noteEntity.id == null) {
            val list = noteDataSource.get() ?: listOf()
            val newId = (list.maxByOrNull { it.id ?: 0 }?.id ?: 0) + 1
            noteDataSource.update { it?.plus(noteEntity.copy(id = newId)) ?: listOf(noteEntity.copy(id = newId)) }
            return newId
        }else{
            val list = noteDataSource.get() ?: listOf()
            val index = list.indexOfFirst { it.id == noteEntity.id }
            if (index != -1) {
                val newList = list.toMutableList()
                newList[index] = noteEntity
                noteDataSource.update { newList }
                return noteEntity.id
            }else{
                noteDataSource.update { it?.plus(noteEntity) ?: listOf(noteEntity) }
                return noteEntity.id
            }
        }
    }

    override suspend fun insert(noteEntity: NoteEntity): Long {
        val list = noteDataSource.get() ?: listOf()
        val newId = (list.maxByOrNull { it.id ?: 0 }?.id ?: 0) + 1
        noteDataSource.update { it?.plus(noteEntity.copy(id = newId)) ?: listOf(noteEntity.copy(id = newId)) }
        return newId
    }

    override suspend fun update(noteEntity: NoteEntity) {
        val list = noteDataSource.get() ?: listOf()
        val index = list.indexOfFirst { it.id == noteEntity.id }
        if (index != -1) {
            val newList = list.toMutableList()
            newList[index] = noteEntity
            noteDataSource.update { newList }
        }else{
            noteDataSource.update { it?.plus(noteEntity) ?: listOf(noteEntity) }
        }
    }

    override fun getAll(): Flow<List<NoteEntity>> {
        return noteDataSource
            .updates
            .mapNotNull { it }
    }

    override fun getOne(id: Long): Flow<NoteEntity?> {
        return noteDataSource
            .updates
            .mapNotNull { list -> list?.firstOrNull { it.id == id } }
    }

    override suspend fun delete(id: Long) {
       noteDataSource.update { it?.filter { it.id != id } ?: listOf() }
    }

    override suspend fun insertAll(notes: List<NoteEntity>) {
        for (note in notes) {
            upsert(note)
        }
    }

    override suspend fun clearAll() {
        noteDataSource.update { listOf() }
    }
}
