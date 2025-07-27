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
import com.mshdabiola.model.UserData
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
    private lateinit var userDataRepository: FakeUserDataRepository // Use the concrete fake type

    // Initial data for the fake repository
    private val initialUserData = UserData(
        contrast = 0,
        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
        // Add other UserData fields with defaults if they exist
        useDynamicColor = false,
        shouldHideOnboarding = false,

    )

    @Before
    fun setUp() {
        userDataRepository = FakeUserDataRepository() // Initialize your fake repository
        // Set initial data for the fake repository before creating the ViewModel
        userDataRepository.setFakeUserData(initialUserData)

        viewModel = SettingViewModel(userDataRepository)
    }

    @Test
    fun `initial state is correct`() = runTest(mainDispatcherRule.testDispatcher) {
        // The stateIn will emit initialValue first, then the mapped userData
        val expectedInitialState = SettingState(
            contrast = initialUserData.contrast,
            darkThemeConfig = DarkThemeConfig.DARK,
        )

        // viewModel.settingState will emit SettingState() first (initialValue of stateIn),
        // then the actual mapped value from userDataRepository.userData
        // We can skip the initial empty one if we know the repository has data.
        assertEquals(expectedInitialState, viewModel.settingState.value) // or .first() if it's hot

        // More robustly with Turbine, accounting for the initial emission from stateIn
        viewModel.settingState.test {
            // The first emission could be the default SettingState() if the flow is cold
            // and repository.userData hasn't emitted yet when stateIn starts.
            // However, since we set userData in FakeUserDataRepository before VM init,
            // it should quickly pick up the actual data.
            // If FakeUserDataRepository.userData is a StateFlow/SharedFlow seeded with data,
            // this should pass directly.
            // If it's a cold flow, the behavior might differ slightly on the first emit.

            // Let's assume FakeUserDataRepository.userData immediately provides the set data
            // or the default SettingState() is acceptable as the first item before real data
            val firstItem = awaitItem()
             // This means the mapped initialUserData was emitted directly
                assertEquals(expectedInitialState, firstItem)


            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `setContrast updates repository and state`() = runTest(mainDispatcherRule.testDispatcher) {
        val newContrast = 0
        val expectedStateAfterUpdate = SettingState(
            contrast = newContrast,
            darkThemeConfig = initialUserData.darkThemeConfig, // Dark theme config remains unchanged
        )

        viewModel.settingState.test {
            // Skip initial state(s)
            // The number of initial states to skip might depend on how stateIn and the repo flow are configured.
            // If the repo.userData is a StateFlow, it might emit current then new.
            // If repo.userData is a simple Flow, stateIn will take its initialValue then the first from upstream.

            // Consume the current state before the update
            awaitItem() // This might be the default SettingState() or the initial mapped UserData

            // If the first awaitItem() was the default SettingState(), consume the actual initial data state
            if (viewModel.settingState.value.contrast != initialUserData.contrast) {
                awaitItem()
            }

            viewModel.setContrast(newContrast)

            // Verify UserDataRepository was called
            assertEquals(newContrast, userDataRepository.userData.first().contrast)

            // Assert the new state is emitted
            // The update in FakeUserDataRepository should trigger a new emission
            assertEquals(expectedStateAfterUpdate, awaitItem())

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `setDarkThemeConfig updates repository and state`() = runTest(mainDispatcherRule.testDispatcher) {
        val newDarkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM
        val expectedStateAfterUpdate = SettingState(
            contrast = initialUserData.contrast, // Contrast remains unchanged
            darkThemeConfig = newDarkThemeConfig,
        )

        viewModel.settingState.test {
            // Consume the current state before the update
            awaitItem()


            viewModel.setDarkThemeConfig(newDarkThemeConfig)

            // Verify UserDataRepository was called
            assertEquals(newDarkThemeConfig, userDataRepository.userData.first().darkThemeConfig)

            // Assert the new state is emitted
            assertEquals(expectedStateAfterUpdate, awaitItem())

            cancelAndConsumeRemainingEvents()
        }
    }
}
