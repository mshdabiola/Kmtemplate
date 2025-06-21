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
