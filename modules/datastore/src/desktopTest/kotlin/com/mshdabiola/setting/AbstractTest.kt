package com.mshdabiola.setting

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.mshdabiola.datastore.di.commonModule
import com.mshdabiola.datastore.model.UserDataJsonSerializer
import okio.FileSystem
import org.junit.Rule
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

abstract class AbstractTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        val module = module {
            single {
                val userDataSerDataStore = DataStoreFactory.create(
                    storage = OkioStorage(
                        fileSystem = FileSystem.SYSTEM,
                        serializer = UserDataJsonSerializer,
                        producePath = {
                            FileSystem.SYSTEM_TEMPORARY_DIRECTORY
                        },
                    ),
                )

                userDataSerDataStore
            }
        }
        // Your KoinApplication instance here
        modules(module, commonModule)
    }
}
