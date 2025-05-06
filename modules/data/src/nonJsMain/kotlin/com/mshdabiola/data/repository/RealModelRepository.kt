package com.mshdabiola.data.repository


import com.mshdabiola.model.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

internal class RealModelRepository(
    private val ioDispatcher: CoroutineDispatcher,
) : NoteRepository {
    override suspend fun upsert(note: Note): Long {
        return withContext(ioDispatcher) {
       return@withContext 1L
        }
    }

    override fun getAll(): Flow<List<Note>> {
        return flow { emptyList<Note>() }
    }

    override fun getOne(id: Long): Flow<Note?> {
        return flow { null }
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
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
