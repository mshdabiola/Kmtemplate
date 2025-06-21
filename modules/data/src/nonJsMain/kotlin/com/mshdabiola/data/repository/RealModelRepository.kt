/*
 * Copyright (C) 2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
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
