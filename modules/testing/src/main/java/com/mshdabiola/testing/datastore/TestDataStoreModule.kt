/*
 *abiola 2024
 */

package com.mshdabiola.testing.datastore

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.mshdabiola.datastore.Store
import com.mshdabiola.datastore.StoreImpl
import com.mshdabiola.datastore.UserDataJsonSerializer
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.File


val dataStoreModule = module {
    single {

        DataStoreFactory.create(
            storage = OkioStorage(
                fileSystem = FileSystem.SYSTEM,
                serializer = UserDataJsonSerializer,
                producePath = {
                    val path = File(FileSystem.SYSTEM_TEMPORARY_DIRECTORY.toFile(), "data")
                    if (!path.parentFile.exists()) {
                        path.mkdirs()
                    }
                    path.toOkioPath()
                },
            ),
        )

    }

    single {
        StoreImpl(
            userdata = get(qualifier = qualifier("userdata")),
            coroutineDispatcher = get(),
        )
    } bind Store::class
}
