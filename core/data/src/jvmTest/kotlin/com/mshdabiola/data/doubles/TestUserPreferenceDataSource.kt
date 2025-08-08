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
package com.mshdabiola.data.doubles

import com.mshdabiola.datastore.UserPreferencesDataSource
import com.mshdabiola.datastore.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TestUserPreferenceDataSource : UserPreferencesDataSource {

    private val _userPreferences = MutableStateFlow(
        UserPreferences(
            darkThemeConfig = 0,
            useDynamicColor = false,
            shouldHideOnboarding = false,
            contrast = 0,
            shouldShowGradientBackground = true,
            language = "en",
        ),
    )
    override val userPreferences: Flow<UserPreferences> = _userPreferences.asStateFlow()

    override suspend fun setDarkThemeConfig(darkThemeConfig: Int) {
        _userPreferences.update { it.copy(darkThemeConfig = darkThemeConfig) }
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        _userPreferences.update { it.copy(useDynamicColor = useDynamicColor) }
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        _userPreferences.update { it.copy(shouldHideOnboarding = shouldHideOnboarding) }
    }

    override suspend fun setContrast(contrast: Int) {
        _userPreferences.update { it.copy(contrast = contrast) }
    }

    override suspend fun setShouldShowGradientBackground(shouldShowGradientBackground: Boolean) {
        _userPreferences.update { it.copy(shouldShowGradientBackground = shouldShowGradientBackground) }
    }

    override suspend fun setLanguage(language: String) {
        _userPreferences.update { it.copy(language = language) }
    }
}
