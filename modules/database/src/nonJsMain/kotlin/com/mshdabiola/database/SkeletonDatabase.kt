/*
 *abiola 2024
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
