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
package com.mshdabiola.datastore

import com.mshdabiola.datastore.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesDataSource {
    val userPreferences: Flow<UserPreferences>

    suspend fun setContrast(contrast: Int)

    /**
     * Sets the desired dark theme config.
     */
    suspend fun setDarkThemeConfig(darkThemeConfig: Int)

    /**
     * Sets the preferred dynamic color config.
     */
    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

    /**
     * Sets whether the user has completed the onboarding process.
     */
    suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean)

    /**
     * Sets whether the app should display a gradient background for the user.
     *
     * Persistently updates the stored preference that controls gradient background visibility.
     *
     * @param shouldShowGradientBackground True to show a gradient background, false to disable it.
     */
    suspend fun setShouldShowGradientBackground(shouldShowGradientBackground: Boolean)

    /**
     * Updates the user's preferred language.
     *
     * @param language A language identifier (locale tag or language code) to store as the user's preference.
     */
    suspend fun setLanguage(language: String)

    /**
     * Sets whether the user allows applying updates from pre-release builds.
     *
     * @param updateFromPreRelease True to enable receiving/installing updates from pre-release channels; false to restrict updates to stable releases.
     */
    suspend fun setUpdateFromPreRelease(updateFromPreRelease: Boolean)

    /**
     * Persists whether the in-app update dialog should be shown to the user.
     *
     * Calling this updates the stored user preference so subsequent sessions will reflect the choice.
     *
     * @param showUpdateDialog True to show the update dialog; false to hide it.
     */
    suspend fun setShowUpdateDialog(showUpdateDialog: Boolean)
}
