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

import androidx.datastore.core.DataStore
import com.mshdabiola.datastore.model.UserPreferences
import kotlinx.coroutines.flow.Flow

class RealUserPreferencesDataSource(
    private val userdata: DataStore<UserPreferences>,
) : UserPreferencesDataSource {
    override val userPreferences: Flow<UserPreferences>
        get() =
            userdata
                .data

    override suspend fun setContrast(contrast: Int) {
        userdata.updateData {
            it.copy(contrast = contrast)
        }
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: Int) {
        userdata.updateData { it.copy(darkThemeConfig = darkThemeConfig) }
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        userdata.updateData { it.copy(useDynamicColor = useDynamicColor) }
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        userdata.updateData { it.copy(shouldHideOnboarding = shouldHideOnboarding) }
    }

    override suspend fun setShouldShowGradientBackground(shouldShowGradientBackground: Boolean) {
        userdata.updateData { it.copy(shouldShowGradientBackground = shouldShowGradientBackground) }
    }

    /**
     * Persistently updates the user's preferred language in the DataStore.
     *
     * @param language The language identifier to store for the user preferences (e.g., a language or locale tag).
     */
    override suspend fun setLanguage(language: String) {
        userdata.updateData { it.copy(language = language) }
    }

    /**
     * Persist the user's preference for receiving updates from pre-release channels.
     *
     * This is a suspend function that updates the stored UserPreferences, setting the
     * `updateFromPreRelease` flag to the provided value.
     *
     * @param updateFromPreRelease true to enable receiving pre-release updates; false to disable.
     */
    override suspend fun setUpdateFromPreRelease(updateFromPreRelease: Boolean) {
        userdata.updateData { it.copy(updateFromPreRelease = updateFromPreRelease) }
    }

    /**
     * Persist the user's preference for showing the update dialog.
     *
     * Updates the stored UserPreferences by setting the `showUpdateDialog` flag.
     *
     * @param showUpdateDialog true to show the update dialog to the user; false to hide it.
     */
    override suspend fun setShowUpdateDialog(showUpdateDialog: Boolean) {
        userdata.updateData { it.copy(showUpdateDialog = showUpdateDialog) }
    }
}
