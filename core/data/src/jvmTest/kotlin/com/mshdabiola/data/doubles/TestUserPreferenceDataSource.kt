/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
            language = "en-US",
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

    /**
     * Update the stored user language preference.
     *
     * Updates the in-memory UserPreferences' `language` field and emits the change to observers of [userPreferences].
     *
     * @param language The language tag to set (e.g., "en-US", BCP‑47 style). */
    override suspend fun setLanguage(language: String) {
        _userPreferences.update { it.copy(language = language) }
    }

    /**
     * Set whether the user opts into receiving pre-release (beta) updates.
     *
     * Updates the stored user preferences so observers of `userPreferences` will receive the new value.
     *
     * @param updateFromPreRelease True to enable pre-release updates; false to disable.
     */
    override suspend fun setUpdateFromPreRelease(updateFromPreRelease: Boolean) {
        _userPreferences.update { it.copy(updateFromPreRelease = updateFromPreRelease) }
    }

    /**
     * Update the stored preference that controls whether the update dialog is shown.
     *
     * This suspending function atomically updates the in-memory UserPreferences instance so observers of
     * the exposed preferences flow receive the new value.
     *
     * @param showUpdateDialog True to indicate the update dialog should be shown, false to hide it.
     */
    override suspend fun setShowUpdateDialog(showUpdateDialog: Boolean) {
        _userPreferences.update { it.copy(showUpdateDialog = showUpdateDialog) }
    }
}
