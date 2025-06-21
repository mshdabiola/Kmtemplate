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

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mshdabiola.database.SkeletonDatabase
import com.mshdabiola.database.di.daoModules
import com.mshdabiola.database.di.getRoomDatabase
import org.junit.Rule
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

abstract class AbstractTest : KoinTest {
    @get:Rule
    val koinTestRule =
        KoinTestRule.create {
            val module =
                module {
                    single {
                        val db =
                            Room
                                .inMemoryDatabaseBuilder<SkeletonDatabase>()
                                .setDriver(BundledSQLiteDriver())
                        getRoomDatabase(db)
                    }
                }
            // Your KoinApplication instance here
            modules(module, daoModules)
        }

    abstract fun insert()

    abstract fun delete()

    abstract fun getOne()

    abstract fun getAll()
}
