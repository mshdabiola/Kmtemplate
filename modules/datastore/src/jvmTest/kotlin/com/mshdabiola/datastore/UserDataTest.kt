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

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import app.cash.turbine.test
import com.mshdabiola.datastore.di.commonModule
import com.mshdabiola.model.ThemeBrand
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.inject
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import java.io.File
import kotlin.test.assertEquals

class UserDataTest : KoinTest {
    @get:Rule
    val koinTestRule =
        KoinTestRule.create {
            val module =
                module {
                    single { Dispatchers.IO } bind CoroutineDispatcher::class

                    single(qualifier = qualifier("userdata")) {
                        DataStoreFactory.create(
                            storage =
                            OkioStorage(
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
                }
            // Your KoinApplication instance here
            modules(module, commonModule)
        }

    @Test
    fun setUserdata() =
        runTest {
            val stt by inject<Store>()
            stt.updateUserData { it.copy(themeBrand = ThemeBrand.GREEN) }
            stt.userData
                .test {
                    val data = awaitItem()
                    assertEquals(data.themeBrand, ThemeBrand.GREEN)
                    this.cancelAndIgnoreRemainingEvents()
                }
        }
}
