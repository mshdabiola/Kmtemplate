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
package com.mshdabiola.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.mshabiola.database.util.Constant.DATABASE_NAME
import com.mshdabiola.database.SkeletonDatabase
import com.mshdabiola.model.generalPath
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual val databaseModule: Module
    get() =
        module {
            single {
                getRoomDatabase(getDatabaseBuilder())
            }
            includes(daoModules)
        }

fun getDatabaseBuilder(): RoomDatabase.Builder<SkeletonDatabase> {
    val path = File(generalPath, DATABASE_NAME)
//    if (path.exists().not()) {
//        path.mkdirs()
//    }
    return Room.databaseBuilder<SkeletonDatabase>(
        name = path.absolutePath,
    )
    //   .setDriver(BundledSQLiteDriver())
}
