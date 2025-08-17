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
import com.mshdabiola.model.ReleaseInfo
import com.mshdabiola.model.UserSettings
import com.mshdabiola.testing.fake.repository.FakeNetworkRepository // Import the shared fake
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

    private lateinit var userDataRepository: FakeUserDataRepository
    private lateinit var networkRepository: FakeNetworkRepository // Added FakeNetworkRepository
    private lateinit var viewModel: MainAppViewModel
    private lateinit var logger: Logger

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userDataRepository = FakeUserDataRepository()
        networkRepository = FakeNetworkRepository() // Initialize FakeNetworkRepository
        logger = testLogger
        viewModel = MainAppViewModel(
            userDataRepository = userDataRepository,
            networkRepository = networkRepository, // Pass FakeNetworkRepository
            logger = logger,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState is Loading initially then Success with initial repo data`() = runTest(testDispatcher) {
        val initialRepoData = userDataRepository.userSettingsSource.value // This will have defaults for new fields

        viewModel.uiState.test(timeout = 3.seconds) {
            assertEquals(Loading, awaitItem())

            testDispatcher.scheduler.advanceUntilIdle()

            val successState = awaitItem()
            assertTrue(
                "UI state should be Success, but was $successState",
                successState is Success,
            )
            assertEquals(initialRepoData, (successState as Success).userSettings)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState transitions to Success when UserDataRepository emits new data`() = runTest(testDispatcher) {
        val newTestUserSettings = UserSettings(
            contrast = 1,
            darkThemeConfig = DarkThemeConfig.DARK,
            useDynamicColor = true,
            updateFromPreRelease = true, // Added
            showUpdateDialog = true, // Added
        )

        viewModel.uiState.test(timeout = 3.seconds) {
            assertEquals(Loading, awaitItem())

            testDispatcher.scheduler.advanceUntilIdle()
            val initialSuccessState = awaitItem()
            assertTrue(initialSuccessState is Success)

            userDataRepository.setFakeUserData(newTestUserSettings)
            testDispatcher.scheduler.advanceUntilIdle()

            val newSuccessState = awaitItem()
            assertTrue(
                "UI state should be Success with new data, but was $newSuccessState",
                newSuccessState is Success,
            )
            assertEquals(newTestUserSettings, (newSuccessState as Success).userSettings)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState updates correctly on subsequent UserData emissions`() = runTest(testDispatcher) {
        val initialDataFromRepo = userDataRepository.userSettingsSource.value

        val updatedUserSettings1 = UserSettings(
            contrast = 0,
            darkThemeConfig = DarkThemeConfig.LIGHT,
            useDynamicColor = true,
            shouldHideOnboarding = false,
            updateFromPreRelease = false, // Added
            showUpdateDialog = true, // Added
        )
        val updatedUserSettings2 = UserSettings(
            contrast = 2,
            darkThemeConfig = DarkThemeConfig.DARK,
            useDynamicColor = true,
            shouldHideOnboarding = true,
            updateFromPreRelease = true, // Added
            showUpdateDialog = false, // Added
        )

        viewModel.uiState.test {
            assertEquals(Loading, awaitItem())

            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(Success(initialDataFromRepo), awaitItem())

            userDataRepository.setFakeUserData(updatedUserSettings1)
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(Success(updatedUserSettings1), awaitItem())

            userDataRepository.setFakeUserData(updatedUserSettings2)
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(Success(updatedUserSettings2), awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getLatestReleaseInfo returns Success when network call is successful and showUpdateDialog is true`() =
        runTest(testDispatcher) {
            userDataRepository.setFakeUserData(UserSettings(showUpdateDialog = true, updateFromPreRelease = true))
            val expectedReleaseInfo = ReleaseInfo.NewUpdate(
                tagName = "v1.0.0",
                releaseName = "Test Release",
                body = "This is a test release.",
                asset = "test.apk",
            )
            networkRepository.setNextReleaseInfo(expectedReleaseInfo)

            val result = viewModel.getLatestReleaseInfo("0.0.1").await()

            assertEquals(expectedReleaseInfo, result)
        }

    @Test
    fun `getLatestReleaseInfo returns Error when network call fails even if showUpdateDialog is true`() =
        runTest(testDispatcher) {
            userDataRepository.setFakeUserData(UserSettings(showUpdateDialog = true, updateFromPreRelease = true))
            val errorMessage = "Network error"
            networkRepository.setShouldThrowError(true, errorMessage)

            val result = viewModel.getLatestReleaseInfo("0.0.1").await()

            assertTrue(result is ReleaseInfo.Error)
            assertEquals(errorMessage, (result as ReleaseInfo.Error).message)
        }

    @Test
    fun `getLatestReleaseInfo returns Error with specific message when showUpdateDialog is false`() =
        runTest(testDispatcher) {
            userDataRepository.setFakeUserData(UserSettings(showUpdateDialog = false))
            // Network call should not happen, so we don't need to mock networkRepository

            val result = viewModel.getLatestReleaseInfo("0.0.1").await()

            assertTrue(result is ReleaseInfo.Error)
            assertEquals("Update dialog is disabled", (result as ReleaseInfo.Error).message)
        }

    @Test
    fun `getLatestReleaseInfo considers updateFromPreRelease setting`() = runTest(testDispatcher) {
        val currentVersion = "1.0.0"
        val expectedReleaseInfo = ReleaseInfo.NewUpdate(
            tagName = "v1.1.0-alpha",
            releaseName = "Pre-release",
            body = "Test pre-release",
            asset = "pre.apk",
        )
        networkRepository.setNextReleaseInfo(expectedReleaseInfo) // This will be used for both calls

        // Scenario 1: updateFromPreRelease = true
        userDataRepository.setFakeUserData(UserSettings(showUpdateDialog = true, updateFromPreRelease = true))
        val resultWithPreRelease = viewModel.getLatestReleaseInfo(currentVersion).await()
        assertEquals(expectedReleaseInfo, resultWithPreRelease)

        // Scenario 2: updateFromPreRelease = false
        // We'll set a different success response for the second call to ensure the mock is being hit with new params
        val expectedStableReleaseInfo = ReleaseInfo.NewUpdate(
            tagName = "v1.0.1",
            releaseName = "Stable Release",
            body = "Test stable release.",
            asset = "stable.apk",
        )
        networkRepository.setNextReleaseInfo(expectedStableReleaseInfo)
        userDataRepository.setFakeUserData(UserSettings(showUpdateDialog = true, updateFromPreRelease = false))
        val resultWithoutPreRelease = viewModel.getLatestReleaseInfo(currentVersion).await()
        assertEquals(expectedStableReleaseInfo, resultWithoutPreRelease)
    }
}
