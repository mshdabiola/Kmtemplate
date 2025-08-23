/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import com.mshdabiola.datastore.model.UserPreferences
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath

fun createDataStoreUserData(producePath: () -> String): DataStore<UserPreferences> =
    DataStoreFactory.create(
        storage =
        OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = UserDataJsonSerializer,
            producePath = {
                producePath().toPath()
            },
        ),
    )

val json = Json

object UserDataJsonSerializer : OkioSerializer<UserPreferences> {
    override val defaultValue: UserPreferences
        get() =
            UserPreferences()

    override suspend fun readFrom(source: BufferedSource): UserPreferences {
        return json.decodeFromString<UserPreferences>(source.readUtf8())
    }

    override suspend fun writeTo(
        userPreferences: UserPreferences,
        sink: BufferedSink,
    ) {
        sink.use {
            it.writeUtf8(json.encodeToString(UserPreferences.serializer(), userPreferences))
        }
    }
}
