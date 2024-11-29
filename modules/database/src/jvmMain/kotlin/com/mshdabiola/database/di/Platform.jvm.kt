package com.mshdabiola.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.mshabiola.database.util.Constant.databaseName
import com.mshdabiola.database.SkeletonDatabase
import com.mshdabiola.model.generalPath
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual val databaseModule: Module
    get() = module {
        single {
            getRoomDatabase(getDatabaseBuilder())
        }
        includes(daoModules)
    }

fun getDatabaseBuilder(): RoomDatabase.Builder<SkeletonDatabase> {
    val path = File(generalPath, databaseName)
//    if (path.exists().not()) {
//        path.mkdirs()
//    }
    return Room.databaseBuilder<SkeletonDatabase>(
        name = path.absolutePath,
    )
    //   .setDriver(BundledSQLiteDriver())
}
