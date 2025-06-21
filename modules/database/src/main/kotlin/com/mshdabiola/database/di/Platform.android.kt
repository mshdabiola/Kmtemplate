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

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mshabiola.database.util.Constant
import com.mshdabiola.database.SkeletonDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val databaseModule: Module
    get() =
        module {
            single {
                getRoomDatabase(getDatabaseBuilder(get()))
            }
            includes(daoModules)
        }

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<SkeletonDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(Constant.DATABASE_NAME)
    return Room.databaseBuilder<SkeletonDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    )
    //  .setDriver(AndroidSQLiteDriver())
}
