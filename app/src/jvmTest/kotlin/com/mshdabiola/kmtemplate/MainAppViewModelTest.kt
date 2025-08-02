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
package com.mshdabiola.kmtemplate

import app.cash.turbine.test
import co.touchlab.kermit.Logger
import com.mshdabiola.kmtemplate.MainActivityUiState.Loading
import com.mshdabiola.kmtemplate.MainActivityUiState.Success
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.UserData
import com.mshdabiola.testing.fake.repository.FakeUserDataRepository // Import the shared fake
import com.mshdabiola.testing.util.testLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class MainAppViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    // Use the FakeUserDataRepository from the :testing module
    private lateinit var userDataRepository: FakeUserDataRepository
    private lateinit var viewModel: MainAppViewModel
    private lateinit var logger: Logger

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userDataRepository = FakeUserDataRepository() // Initialize the shared fake
        logger = testLogger
        viewModel = MainAppViewModel(userDataRepository = userDataRepository, logger)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState is Loading initially then Success with initial repo data`() = runTest(testDispatcher) {
        // Get the initial data that FakeUserDataRepository will emit
        val initialRepoData = userDataRepository.userDataSource.value // Access the initial state

        viewModel.uiState.test(timeout = 3.seconds) {
            assertEquals(Loading, awaitItem())

            testDispatcher.scheduler.advanceUntilIdle() // Allow collection and map to run

            val successState = awaitItem()
            assertTrue(
                "UI state should be Success, but was $successState",
                successState is Success,
            )
            assertEquals(initialRepoData, (successState as Success).userData)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState transitions to Success when UserDataRepository emits new data`() = runTest(testDispatcher) {
        val newTestUserData = UserData(
            contrast = 1,
            darkThemeConfig = DarkThemeConfig.DARK,
            useDynamicColor = true,
        )

        viewModel.uiState.test(timeout = 3.seconds) {
            // 1. Consume the initial Loading state
            assertEquals(Loading, awaitItem())

            // 2. Consume the initial Success state from repository's default emission
            testDispatcher.scheduler.advanceUntilIdle()
            val initialSuccessState = awaitItem()
            assertTrue(initialSuccessState is Success)
            // Can assert (initialSuccessState as Success).userData == userDataRepository.userDataSource.value

            // 3. Emit new UserData using the FakeUserDataRepository's method
            userDataRepository.setFakeUserData(newTestUserData) // Use the method from your fake
            testDispatcher.scheduler.advanceUntilIdle()

            // 4. Verify the uiState is now Success with the new data
            val newSuccessState = awaitItem()
            assertTrue(
                "UI state should be Success with new data, but was $newSuccessState",
                newSuccessState is Success,
            )
            assertEquals(newTestUserData, (newSuccessState as Success).userData)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState updates correctly on subsequent UserData emissions`() = runTest(testDispatcher) {
        val initialDataFromRepo = userDataRepository.userDataSource.value

        val updatedUserData1 = UserData(
            contrast = 0,
            darkThemeConfig = DarkThemeConfig.LIGHT,
            useDynamicColor = true,
            shouldHideOnboarding = false,
        )
        val updatedUserData2 = UserData(
            contrast = 2,
            darkThemeConfig = DarkThemeConfig.DARK,
            useDynamicColor = true,
            shouldHideOnboarding = true,
        )

        viewModel.uiState.test {
            assertEquals(Loading, awaitItem())

            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(Success(initialDataFromRepo), awaitItem())

            userDataRepository.setFakeUserData(updatedUserData1)
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(Success(updatedUserData1), awaitItem())

            userDataRepository.setFakeUserData(updatedUserData2)
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(Success(updatedUserData2), awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
