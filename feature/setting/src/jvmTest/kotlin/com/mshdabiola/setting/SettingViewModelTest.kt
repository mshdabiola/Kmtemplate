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
package com.mshdabiola.setting

import app.cash.turbine.test
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.Platform
import com.mshdabiola.model.ReleaseInfo
import com.mshdabiola.model.UserSettings
import com.mshdabiola.testing.fake.repository.FakeNetworkRepository // Assuming this exists
import com.mshdabiola.testing.fake.repository.FakeUserDataRepository
import com.mshdabiola.testing.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class SettingViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SettingViewModel
    private lateinit var userDataRepository: FakeUserDataRepository
    private lateinit var networkRepository: FakeNetworkRepository // Assuming this exists
    private lateinit var platform: Platform

    private val initialUserSettings = UserSettings()
    private val testPlatform = Platform.Desktop("testOs", "1.0")

    @Before
    fun setUp() {
        userDataRepository = FakeUserDataRepository()
        userDataRepository.setFakeUserData(initialUserSettings)
        networkRepository = FakeNetworkRepository() // Assuming this can be instantiated
        platform = testPlatform
        viewModel = SettingViewModel(userDataRepository, networkRepository, platform)
    }

    @Test
    fun `initial state is correct`() = runTest(mainDispatcherRule.testDispatcher) {
        val expectedInitialState = SettingState(
            userSettings = initialUserSettings,
            releaseInfo = null, // Initially no release info
            platform = Platform.Desktop("desktop", "11"),
        )

        viewModel.settingState.test {
            assertEquals(expectedInitialState, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `setContrast updates repository and state`() = runTest(mainDispatcherRule.testDispatcher) {
        val newContrast = 1
        val updatedUserSettings = initialUserSettings.copy(contrast = newContrast)
        val expectedStateAfterUpdate = SettingState(
            userSettings = updatedUserSettings,
            releaseInfo = null,
            platform = testPlatform,
        )
        viewModel.setContrast(newContrast)

        viewModel.settingState.test {
            // The stateFlow combines multiple flows, initial value might be emitted first,
            // then the one after userSettings flow emits, then the one after setContrast if it's fast.
            // We are interested in the state after the change is applied.
            // Using skipItems or awaitItemMatching can be more robust depending on exact flow behavior.
            assertEquals(initialUserSettings.contrast, awaitItem().userSettings.contrast) // initial or intermediate
            val finalState = awaitItem() // State after update
            assertEquals(newContrast, finalState.userSettings.contrast)
            assertEquals(expectedStateAfterUpdate, finalState)
            assertEquals(newContrast, userDataRepository.userSettings.first().contrast)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `setDarkThemeConfig updates repository and state`() = runTest(mainDispatcherRule.testDispatcher) {
        val newDarkThemeConfig = DarkThemeConfig.DARK
        val updatedUserSettings = initialUserSettings.copy(darkThemeConfig = newDarkThemeConfig)
        val expectedStateAfterUpdate = SettingState(
            userSettings = updatedUserSettings,
            releaseInfo = null,
            platform = testPlatform,
        )
        viewModel.setDarkThemeConfig(newDarkThemeConfig)
        viewModel.settingState.test {
            assertEquals(initialUserSettings.darkThemeConfig, awaitItem().userSettings.darkThemeConfig)
            val finalState = awaitItem()
            assertEquals(newDarkThemeConfig, finalState.userSettings.darkThemeConfig)
            assertEquals(expectedStateAfterUpdate, finalState)
            assertEquals(newDarkThemeConfig, userDataRepository.userSettings.first().darkThemeConfig)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `setGradientBackground updates repository and state`() = runTest(mainDispatcherRule.testDispatcher) {
        val newGradientBackground = false
        val updatedUserSettings = initialUserSettings.copy(shouldShowGradientBackground = newGradientBackground)
        val expectedStateAfterUpdate = SettingState(
            userSettings = updatedUserSettings,
            releaseInfo = null,
            platform = testPlatform,
        )

        viewModel.setGradientBackground(newGradientBackground)
        viewModel.settingState.test {
            assertEquals(
                initialUserSettings.shouldShowGradientBackground,
                awaitItem().userSettings.shouldShowGradientBackground,
            )
            val finalState = awaitItem()
            assertEquals(newGradientBackground, finalState.userSettings.shouldShowGradientBackground)
            assertEquals(expectedStateAfterUpdate, finalState)
            assertEquals(newGradientBackground, userDataRepository.userSettings.first().shouldShowGradientBackground)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `setLanguage updates repository and state`() = runTest(mainDispatcherRule.testDispatcher) {
        val newLanguage = "fr-FR"
        val updatedUserSettings = initialUserSettings.copy(language = newLanguage)
        val expectedStateAfterUpdate = SettingState(
            userSettings = updatedUserSettings,
            releaseInfo = null,
            platform = testPlatform,
        )
        viewModel.setLanguage(newLanguage)

        viewModel.settingState.test {
            assertEquals(initialUserSettings.language, awaitItem().userSettings.language)
            val finalState = awaitItem()
            assertEquals(newLanguage, finalState.userSettings.language)
            assertEquals(expectedStateAfterUpdate, finalState)
            assertEquals(newLanguage, userDataRepository.userSettings.first().language)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `setUpdateFromPreRelease updates repository and state`() = runTest(mainDispatcherRule.testDispatcher) {
        val newUpdateFromPreRelease = true
        val updatedUserSettings = initialUserSettings.copy(updateFromPreRelease = newUpdateFromPreRelease)
        val expectedStateAfterUpdate = SettingState(
            userSettings = updatedUserSettings,
            releaseInfo = null,
            platform = testPlatform,
        )
        viewModel.setUpdateFromPreRelease(newUpdateFromPreRelease)

        viewModel.settingState.test {
            assertEquals(initialUserSettings.updateFromPreRelease, awaitItem().userSettings.updateFromPreRelease)
            val finalState = awaitItem()
            assertEquals(newUpdateFromPreRelease, finalState.userSettings.updateFromPreRelease)
            assertEquals(expectedStateAfterUpdate, finalState)
            assertEquals(newUpdateFromPreRelease, userDataRepository.userSettings.first().updateFromPreRelease)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `setShowDialog updates repository and state`() = runTest(mainDispatcherRule.testDispatcher) {
        val newShowDialog = true
        val updatedUserSettings = initialUserSettings.copy(showUpdateDialog = newShowDialog)
        val expectedStateAfterUpdate = SettingState(
            userSettings = updatedUserSettings,
            releaseInfo = null,
            platform = testPlatform,
        )
        viewModel.setShowDialog(newShowDialog)

        viewModel.settingState.test {
            assertEquals(initialUserSettings.showUpdateDialog, awaitItem().userSettings.showUpdateDialog)
            val finalState = awaitItem()
            assertEquals(newShowDialog, finalState.userSettings.showUpdateDialog)
            assertEquals(expectedStateAfterUpdate, finalState)
            assertEquals(newShowDialog, userDataRepository.userSettings.first().showUpdateDialog)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `checkForUpdate success updates releaseInfo in state`() = runTest(mainDispatcherRule.testDispatcher) {
        val testReleaseInfo = ReleaseInfo.Success("v1.0.0", "Release 1", "Body", "asset.apk")
        networkRepository.setNextReleaseInfo(testReleaseInfo) // Configure fake repository

        val currentVersion = "0.9.0"
        viewModel.checkForUpdate(currentVersion)

        val expectedStateAfterUpdate = SettingState(
            userSettings = initialUserSettings, // User settings remain unchanged
            releaseInfo = testReleaseInfo,
            platform = testPlatform,
        )

        viewModel.settingState.test {
            // Initial state or state before releaseInfoFlow emits
            assertNull(awaitItem().releaseInfo)

            val finalState = awaitItem() // State after releaseInfoFlow emits
            assertEquals(testReleaseInfo, finalState.releaseInfo)
            assertEquals(expectedStateAfterUpdate, finalState)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `checkForUpdate error updates releaseInfo in state`() = runTest(mainDispatcherRule.testDispatcher) {
        val testErrorReleaseInfo = ReleaseInfo.Error("Failed to fetch")
        networkRepository.setNextReleaseInfo(testErrorReleaseInfo) // Configure fake repository

        val currentVersion = "0.9.0"
        viewModel.checkForUpdate(currentVersion)

        val expectedStateAfterUpdate = SettingState(
            userSettings = initialUserSettings, // User settings remain unchanged
            releaseInfo = testErrorReleaseInfo,
            platform = testPlatform,
        )

        viewModel.settingState.test {
            assertNull(awaitItem().releaseInfo)
            val finalState = awaitItem()
            assertEquals(testErrorReleaseInfo, finalState.releaseInfo)
            assertEquals(expectedStateAfterUpdate, finalState)
            cancelAndConsumeRemainingEvents()
        }
    }
}
