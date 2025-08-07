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
package com.mshdabiola.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.mshdabiola.database.KmtDatabase
import com.mshdabiola.database.util.Constant
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

actual val databaseModule: Module
    get() =
        module {
            single {
                getDatabaseBuilder(get())
            }
            includes(daoModules)
        }

fun getDatabaseBuilder(context: Context): KmtDatabase {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(Constant.DATABASE_NAME)
    return Room.databaseBuilder<KmtDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    )
        .setDriver(AndroidSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
