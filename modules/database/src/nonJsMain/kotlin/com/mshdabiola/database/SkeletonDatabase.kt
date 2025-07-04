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
