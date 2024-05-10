/*
 *abiola 2024
 */

package com.mshdabiola.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mshdabiola.database.dao.ImageDao
import com.mshdabiola.database.dao.NoteDao
import com.mshdabiola.database.model.ImageEntity
import com.mshdabiola.database.model.NoteEntity

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
@TypeConverters()
abstract class SkeletonDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    abstract fun getImageDao(): ImageDao
//
//    abstract fun getPlayerDao(): PlayerDao
//
//    abstract fun getPawnDao(): PawnDao
}
