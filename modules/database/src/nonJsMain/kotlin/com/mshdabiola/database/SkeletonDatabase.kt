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
package com.mshdabiola.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.mshdabiola.database.dao.ImageDao
import com.mshdabiola.database.dao.NoteDao
import com.mshdabiola.database.model.ImageEntity
import com.mshdabiola.database.model.NoteEntity

// fun createDatabase(): MusicDatabase {     return Room. inMemoryDatabaseBuilder<MusicDatabase>(         factory = MusicDatabaseConstructor::initialize     ).build() }
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object SkeletonsDatabaseCtor : RoomDatabaseConstructor<SkeletonDatabase>

@Database(
    entities = [NoteEntity::class, ImageEntity::class],
    version = 1,
//    autoMigrations = [
//        //AutoMigration(from = 2, to = 3, spec = DatabaseMigrations.Schema2to3::class),
//
//                     ]
//    ,
    exportSchema = true,
)
@ConstructedBy(SkeletonsDatabaseCtor::class) // NEW
abstract class SkeletonDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao

    abstract fun getImageDao(): ImageDao
//
//    abstract fun getPlayerDao(): PlayerDao
//
//    abstract fun getPawnDao(): PawnDao
}
