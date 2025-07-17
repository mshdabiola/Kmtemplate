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

import com.mshdabiola.data.model.asNote
import com.mshdabiola.data.model.asNoteEntity
import com.mshdabiola.database.dao.NoteDao
import com.mshdabiola.model.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class RealModelRepository(
    private val noteDao: NoteDao,
    private val ioDispatcher: CoroutineDispatcher,
) : NoteRepository {
    override suspend fun upsert(note: Note): Long {
        return withContext(ioDispatcher) {
            val entity=note.asNoteEntity()
            if (entity.id==null){
                noteDao.insert(entity)
            }else{
                noteDao.update(entity)
                note.id
            }

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
