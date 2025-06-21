/*
 * Copyright (C) 2025 MshdAbiola
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
package com.mshdabiola.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
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
                themeBrand = ThemeBrand.DEFAULT,
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
