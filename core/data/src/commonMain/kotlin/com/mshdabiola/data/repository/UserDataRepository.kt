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
package com.mshdabiola.data.repository

import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    /**
     * Stream of [UserSettings]
     */
    val userSettings: Flow<UserSettings>

    suspend fun setContrast(contrast: Int)

    /**
     * Sets the desired dark theme config.
     */
    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    /**
     * Sets the preferred dynamic color config.
     */
    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

    /**
     * Sets whether the user has completed the onboarding process.
     */
    suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean)

    /**
 * Update the user's preference for showing a gradient background in the UI.
 *
 * This suspend function persists the preference so the app can show or hide gradient backgrounds
 * according to the user's choice.
 *
 * @param shouldShowGradientBackground True to enable gradient backgrounds, false to disable them.
 */
suspend fun setShouldShowGradientBackground(shouldShowGradientBackground: Boolean)

    /**
 * Update the user's preferred application language.
 *
 * @param language The language/locale identifier to set (e.g. "en", "en-US"). */
suspend fun setLanguage(language: String)

    /**
 * Enable or disable receiving updates from pre-release channels.
 *
 * Persists the user's preference for allowing the app to update from pre-release (e.g., alpha/beta) releases.
 *
 * @param updateFromPreRelease True to enable updates from pre-release channels; false to disable.
 */
suspend fun setUpdateFromPreRelease(updateFromPreRelease: Boolean)

    /**
 * Sets whether the in-app update dialog should be shown to the user.
 *
 * Persists the user's preference for showing the update dialog.
 *
 * @param showUpdateDialog true to show the update dialog, false to hide it
 */
suspend fun setShowUpdateDialog(showUpdateDialog: Boolean)
}
