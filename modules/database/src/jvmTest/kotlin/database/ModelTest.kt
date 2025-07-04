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
package database

import app.cash.turbine.test
import com.mshdabiola.database.dao.NoteDao
import com.mshdabiola.database.model.NoteEntity
import kotlinx.coroutines.test.runTest
import org.koin.core.component.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class ModelTest : AbstractTest() {
    @Test
    override fun insert() =
        runTest {
            val modelDao by inject<NoteDao>()

            modelDao.upsert(
                NoteEntity(
                    id = null,
                    title = "abiola",
                    content = "Adisl",
                ),
            )
            modelDao.getAll()
                .test {
                    val list = awaitItem()
                    print(list)
                    assertEquals(1, list.size)
                    this.cancelAndIgnoreRemainingEvents()
                }
        }

    @Test
    override fun delete() {
    }

    @Test
    override fun getOne() {
    }

    @Test
    override fun getAll() {
    }
}
