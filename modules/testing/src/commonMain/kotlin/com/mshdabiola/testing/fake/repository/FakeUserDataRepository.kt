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
package com.mshdabiola.testing.fake.repository

import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Fake implementation of the [UserDataRepository] that allows for controlled emission of UserData.
 *
 * This is useful for tests that depend on UserData state.
 */
class FakeUserDataRepository : UserDataRepository {

    // Internal MutableStateFlow to hold and update UserData
    private val _userData = MutableStateFlow(
        UserData( // Default values for UserData
            contrast = 0, // Example default
            darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM, // Example default
            useDynamicColor = false, // Example default
            shouldHideOnboarding = false, // Example default
        ),
    )

    /**
     * The Flow of [UserData] that can be collected by consumers.
     */
    override val userData: Flow<UserData> = _userData.asStateFlow()

    /**
     * A backing [MutableStateFlow] that can be used to manually emit new UserData
     * values in tests.
     */
    val userDataSource: MutableStateFlow<UserData> = _userData

    override suspend fun setContrast(contrast: Int) {
        _userData.update { currentUserData ->
            currentUserData.copy(contrast = contrast)
        }
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        _userData.update { currentUserData ->
            currentUserData.copy(darkThemeConfig = darkThemeConfig)
        }
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        _userData.update { currentUserData ->
            currentUserData.copy(useDynamicColor = useDynamicColor)
        }
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        _userData.update { currentUserData ->
            currentUserData.copy(shouldHideOnboarding = shouldHideOnboarding)
        }
    }

    override suspend fun setShouldShowGradientBackground(shouldShowGradientBackground: Boolean) {
        _userData.update { currentUserData ->
            currentUserData.copy(shouldShowGradientBackground = shouldShowGradientBackground)
        }
    }

    override suspend fun setLanguage(language: Int) {
        _userData.update { currentUserData ->
            currentUserData.copy(language = language)
        }
    }

    /**
     * Helper function to directly set the entire UserData object, useful for test setup.
     */
    fun setFakeUserData(newUserData: UserData) {
        _userData.value = newUserData
    }
}
