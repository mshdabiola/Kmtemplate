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
