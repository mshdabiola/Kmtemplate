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
package com.mshdabiola.data.doubles

import com.mshdabiola.database.dao.NoteDao
import com.mshdabiola.database.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestNoteDao : NoteDao {
    override suspend fun upsert(noteEntity: NoteEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun insert(noteEntity: NoteEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun update(noteEntity: NoteEntity) {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<NoteEntity>> {
        TODO("Not yet implemented")
    }

    override fun getOne(id: Long): Flow<NoteEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(users: List<NoteEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun clearAll() {
        TODO("Not yet implemented")
    }

}
