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
package com.mshdabiola.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.UserData
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath

internal const val PREFERENCES_DATA_STORE_FILE_NAME = "meetings.preferences_pb"

fun createDataStoreUserData(producePath: () -> String): DataStore<UserData> =
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

object UserDataJsonSerializer : OkioSerializer<UserData> {
    override val defaultValue: UserData
        get() =
            UserData(
                contrast = 0,
                darkThemeConfig = DarkThemeConfig.LIGHT,
                useDynamicColor = false,
                shouldHideOnboarding = false,
            )

    override suspend fun readFrom(source: BufferedSource): UserData {
        return json.decodeFromString<UserData>(source.readUtf8())
    }

    override suspend fun writeTo(
        userData: UserData,
        sink: BufferedSink,
    ) {
        sink.use {
            it.writeUtf8(json.encodeToString(UserData.serializer(), userData))
        }
    }
}
