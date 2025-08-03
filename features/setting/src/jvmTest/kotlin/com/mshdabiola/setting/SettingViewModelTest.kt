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
import com.mshdabiola.model.UserSettings
import com.mshdabiola.testing.fake.repository.FakeUserDataRepository
import com.mshdabiola.testing.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SettingViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SettingViewModel
    private lateinit var userDataRepository: FakeUserDataRepository

    private val initialUserSettings = UserSettings()

    @Before
    fun setUp() {
        userDataRepository = FakeUserDataRepository()
        userDataRepository.setFakeUserData(initialUserSettings)
        viewModel = SettingViewModel(userDataRepository)
    }

    @Test
    fun `initial state is correct`() = runTest(mainDispatcherRule.testDispatcher) {
        val expectedInitialState = SettingState(
            contrast = initialUserSettings.contrast,
            darkThemeConfig = initialUserSettings.darkThemeConfig,
            gradientBackground = initialUserSettings.shouldShowGradientBackground,
            language = initialUserSettings.language,
        )

        viewModel.settingState.test {
            assertEquals(expectedInitialState, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `setContrast updates repository and state`() = runTest(mainDispatcherRule.testDispatcher) {
        val newContrast = 1
        val expectedStateAfterUpdate = SettingState(
            contrast = newContrast,
            darkThemeConfig = initialUserSettings.darkThemeConfig,
            gradientBackground = initialUserSettings.shouldShowGradientBackground,
            language = initialUserSettings.language,
        )
        viewModel.setContrast(newContrast)

        viewModel.settingState.test {
            skipItems(1)
            assertEquals(expectedStateAfterUpdate, awaitItem())
            assertEquals(newContrast, userDataRepository.userSettings.first().contrast)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `setDarkThemeConfig updates repository and state`() = runTest(mainDispatcherRule.testDispatcher) {
        val newDarkThemeConfig = DarkThemeConfig.DARK
        val expectedStateAfterUpdate = SettingState(
            contrast = initialUserSettings.contrast,
            darkThemeConfig = newDarkThemeConfig,
            gradientBackground = initialUserSettings.shouldShowGradientBackground,
            language = initialUserSettings.language,
        )
        viewModel.setDarkThemeConfig(newDarkThemeConfig)
        viewModel.settingState.test {
            skipItems(1)

            assertEquals(expectedStateAfterUpdate, awaitItem())
            assertEquals(newDarkThemeConfig, userDataRepository.userSettings.first().darkThemeConfig)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `setGradientBackground updates repository and state`() = runTest(mainDispatcherRule.testDispatcher) {
        val newGradientBackground = false
        val expectedStateAfterUpdate = SettingState(
            contrast = initialUserSettings.contrast,
            darkThemeConfig = initialUserSettings.darkThemeConfig,
            gradientBackground = newGradientBackground,
            language = initialUserSettings.language,
        )

        viewModel.settingState.test {
            viewModel.setGradientBackground(newGradientBackground)

            assertEquals(newGradientBackground, userDataRepository.userSettings.first().shouldShowGradientBackground)
            assertEquals(expectedStateAfterUpdate, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `setLanguage updates repository and state`() = runTest(mainDispatcherRule.testDispatcher) {
        val newLanguage = "fr-FR"
        val expectedStateAfterUpdate = SettingState(
            contrast = initialUserSettings.contrast,
            darkThemeConfig = initialUserSettings.darkThemeConfig,
            gradientBackground = initialUserSettings.shouldShowGradientBackground,
            language = newLanguage,
        )
        viewModel.setLanguage(newLanguage)

        viewModel.settingState.test {
            skipItems(1)

            assertEquals(expectedStateAfterUpdate, awaitItem())
            assertEquals(newLanguage, userDataRepository.userSettings.first().language)

            cancelAndConsumeRemainingEvents()
        }
    }
}
