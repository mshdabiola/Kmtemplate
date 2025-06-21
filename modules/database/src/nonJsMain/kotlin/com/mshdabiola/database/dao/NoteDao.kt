/*
 * Copyright (C) 2024-2025 MshdAbiola
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
package com.mshdabiola.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.database.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Upsert
    suspend fun upsert(noteEntity: NoteEntity): Long

    @Query("SELECT * FROM note_table")
    fun getAll(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_table WHERE id = :id")
    fun getOne(id: Long): Flow<NoteEntity?>

    @Query("DELETE FROM NOTE_TABLE WHERE id = :id")
    suspend fun delete(id: Long)

    @Upsert
    suspend fun insertAll(users: List<NoteEntity>)

//    @Query("SELECT * FROM note_table")
//   suspend fun pagingSource(): PagingSource<Int, NoteEntity>

    @Query("DELETE FROM note_table")
    suspend fun clearAll()
}
