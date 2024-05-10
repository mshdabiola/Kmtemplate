package com.mshdabiola.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mshdabiola.database.SkeletonDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val databaseModule: Module
    get() = module {
        single {
            getRoomDatabase(getDatabaseBuilder(get()))
        }
        includes(daoModules)
    }

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<SkeletonDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("skeleton.db")
    return Room.databaseBuilder<SkeletonDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    )
}
