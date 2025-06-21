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
