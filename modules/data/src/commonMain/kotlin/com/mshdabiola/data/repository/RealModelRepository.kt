package com.mshdabiola.data.repository

import com.mshdabiola.data.model.asNote
import com.mshdabiola.data.model.asNoteEntity
import com.mshdabiola.database.dao.ImageDao
import com.mshdabiola.database.dao.NoteDao
import com.mshdabiola.model.Note
import com.mshdabiola.network.INetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class RealModelRepository constructor(
    private val noteDao: NoteDao,
    private val iNetworkDataSource: INetworkDataSource,
    private val imageDao: ImageDao,
    private val ioDispatcher: CoroutineDispatcher,
) : NoteRepository {
    override suspend fun upsert(note: Note): Long {
        return withContext(ioDispatcher) {
            noteDao.upsert(note.asNoteEntity())
        }
    }

    override fun getAll(): Flow<List<Note>> {
        return noteDao
            .getAll()
            .map { noteEntities ->
                noteEntities.map {
                    it.asNote()
                }
            }
            .flowOn(ioDispatcher)
    }

    override fun getOne(id: Long): Flow<Note?> {
        return noteDao
            .getOne(id)
            .map { it?.asNote() }
            .flowOn(ioDispatcher)
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            noteDao.delete(id)
        }
    }

//    @OptIn(ExperimentalPagingApi::class)
//    override fun imagePagingData(): Flow<PagingData<Image>> {
//
//        return Pager(
//            PagingConfig(4),
//            remoteMediator = ModeRemoteMediator(iNetworkDataSource,imageDao)
//        ){
//            imageDao.pagingSource()
//        }
//            .flow
//            .map { pagingData -> pagingData.map { it.asImage() } }
//
//
//    }
//
//    override fun notePagingData(): Flow<PagingData<Note>>{
//        return Pager(PagingConfig(20)){
//            noteDao.pagingSource()
//        }
//            .flow
//            .map { pagingData -> pagingData.map { it.asNote() } }
//
//    }
}
