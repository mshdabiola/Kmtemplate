package com.mshdabiola.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import com.mshdabiola.datastore.model.UserDataSer
import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath

internal const val dataStoreFileName = "meetings.preferences_pb"

fun createDataStoreUserData(
    producePath: () -> String,
): DataStore<UserDataSer> = DataStoreFactory.create(
    storage = OkioStorage(
        fileSystem = FileSystem.SYSTEM,
        serializer = UserDataJsonSerializer,
        producePath = {
            producePath().toPath()
        },
    ),
)




val json = Json

internal object UserDataJsonSerializer : OkioSerializer<UserDataSer> {

    override val defaultValue: UserDataSer
        get() = UserDataSer(
            themeBrand = ThemeBrand.DEFAULT,
            darkThemeConfig = DarkThemeConfig.LIGHT,
            useDynamicColor = false,
            shouldHideOnboarding = false,
            contrast = Contrast.Normal,
        )

    override suspend fun readFrom(source: BufferedSource): UserDataSer {
        return json.decodeFromString<UserDataSer>(source.readUtf8())
    }

    override suspend fun writeTo(userDataSer: UserDataSer, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(json.encodeToString(UserDataSer.serializer(), userDataSer))
        }
    }
}
