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
package com.mshdabiola.testing.datastore

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.mshdabiola.datastore.Store
import com.mshdabiola.datastore.StoreImpl
import com.mshdabiola.datastore.UserDataJsonSerializer
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import org.junit.rules.TemporaryFolder
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.File

val dataStoreModule =
    module {
        single {

            val tmpFolder: TemporaryFolder = get()
            tmpFolder.testUserPreferencesDataStore()
        }

        single {
            StoreImpl(
                userdata = get(qualifier = qualifier("userdata")),
                coroutineDispatcher = get(),
            )
        } bind Store::class
    }

fun TemporaryFolder.testUserPreferencesDataStore() =
    DataStoreFactory.create(
        storage =
        OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = UserDataJsonSerializer,
            producePath = {
                val path = File(newFolder(), "data")
                if (path.parentFile?.exists() == false) {
                    path.mkdirs()
                }
                path.toOkioPath()
            },
        ),
    )

//
//    DataStoreFactory.create(
//    storage = OkioStorage(
//        fileSystem = FileSystem.SYSTEM,
//        serializer = UserDataJsonSerializer,
//        producePath = {
//            val path = newFile("user_preferences_test.pb")
//            path.toOkioPath()
//        },
//
//    ),
//    scope = coroutineScope,
// )
