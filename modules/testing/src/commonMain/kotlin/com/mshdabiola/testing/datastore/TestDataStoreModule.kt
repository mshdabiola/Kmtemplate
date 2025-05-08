/*
 *abiola 2024
 */

package com.mshdabiola.testing.datastore

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.mshdabiola.datastore.Store
import com.mshdabiola.datastore.StoreImpl
import com.mshdabiola.datastore.UserDataJsonSerializer
import kotlinx.coroutines.CoroutineScope
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
            tmpFolder.testUserPreferencesDataStore(
                coroutineScope = get(),
            )
        }

        single {
            StoreImpl(
                userdata = get(qualifier = qualifier("userdata")),
                coroutineDispatcher = get(),
            )
        } bind Store::class
    }

fun TemporaryFolder.testUserPreferencesDataStore(coroutineScope: CoroutineScope) =
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
