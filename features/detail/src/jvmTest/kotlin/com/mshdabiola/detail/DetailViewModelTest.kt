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
package com.mshdabiola.detail

import androidx.compose.foundation.text.input.clearText
import app.cash.turbine.test
import com.mshdabiola.data.repository.NoteRepository
import com.mshdabiola.testing.fake.testDataModule
import com.mshdabiola.testing.util.MainDispatcherRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DetailViewModelTest : KoinTest {
    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @get:Rule(order = 2)
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule(order = 3)
    val koinTestRule =
        KoinTestRule.create {
            this.modules(testDataModule)
        }
    private val noteRepository by inject<NoteRepository>()

    @Test
    fun init() =
        runTest(mainDispatcherRule.testDispatcher) {
            val viewModel =
                DetailViewModel(
                    1,
                    noteRepository,
                )

            viewModel
                .state
                .test {
                    var state = awaitItem()

                    assertTrue(state is DetailState.Loading)

                    state = awaitItem()

                    assertTrue(state is DetailState.Success)

                    assertEquals(
                        1,
                        state.id,
                    )

                    val note = noteRepository.getOne(1).first()
                    assertEquals(
                        note?.title,
                        viewModel.title.text.toString(),
                    )
                    assertEquals(
                        note?.content,
                        viewModel.content.text.toString(),
                    )

                    cancelAndIgnoreRemainingEvents()
                }
        }

    @Test
    fun update() =
        runTest(mainDispatcherRule.testDispatcher) {
            val viewModel =
                DetailViewModel(
                    1,
                    noteRepository,
                )

            viewModel
                .state
                .test {
                    var state = awaitItem()

                    assertTrue(state is DetailState.Loading)

                    state = awaitItem()

                    assertTrue(state is DetailState.Success)

                    assertEquals(
                        1,
                        state.id,
                    )

                    viewModel.title.clearText()
                    viewModel.title.edit {
                        append("new title")
                    }
                    viewModel.content.clearText()
                    viewModel.content.edit {
                        append("new content")
                    }
                    delay(1000)

                    val note = noteRepository.getOne(1).first()
                    assertEquals(
                        "new title",
                        note?.title,
                    )
                    assertEquals(
                        "new content",
                        note?.content,
                    )

                    cancelAndIgnoreRemainingEvents()
                }
        }

    @Test
    fun init_new() =
        runTest(mainDispatcherRule.testDispatcher) {
            val viewModel =
                DetailViewModel(
                    -1,
                    noteRepository,
                )

            viewModel
                .state
                .test {
                    var state = awaitItem()

                    assertTrue(state is DetailState.Loading)

                    state = awaitItem()

                    assertTrue(state is DetailState.Success)

                    assertEquals(
                        -1,
                        state.id,
                    )

                    assertEquals(
                        "",
                        viewModel.title.text.toString(),
                    )
                    assertEquals(
                        "",
                        viewModel.content.text.toString(),
                    )

                    cancelAndIgnoreRemainingEvents()
                }
        }

    @Test
    fun addNew() =
        runTest(mainDispatcherRule.testDispatcher) {
            val viewModel =
                DetailViewModel(
                    -1,
                    noteRepository,
                )

            viewModel
                .state
                .test {
                    var state = awaitItem()

                    assertTrue(state is DetailState.Loading)

                    state = awaitItem()

                    assertTrue(state is DetailState.Success)

                    assertEquals(
                        -1,
                        state.id,
                    )

                    viewModel.title.clearText()
                    viewModel.title.edit {
                        append("new title")
                    }
                    viewModel.content.clearText()
                    viewModel.content.edit {
                        append("new content")
                    }
                    delay(1000)

                    val note = noteRepository.getAll().first().last()
                    assertEquals(
                        "new title",
                        note.title,
                    )
                    assertEquals(
                        "new content",
                        note.content,
                    )

                    cancelAndIgnoreRemainingEvents()
                }
        }
}
