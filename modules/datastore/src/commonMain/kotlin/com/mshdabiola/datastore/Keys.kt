package com.mshdabiola.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.mshdabiola.datastore.model.UserDataJsonSerializer
import com.mshdabiola.datastore.model.UserDataSer
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
