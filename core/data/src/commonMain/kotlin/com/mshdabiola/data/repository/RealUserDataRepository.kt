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
package com.mshdabiola.data.repository

import com.mshdabiola.data.asUserSettings
import com.mshdabiola.datastore.UserPreferencesDataSource
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.UserSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class RealUserDataRepository(
    private val userPreferencesRepository: UserPreferencesDataSource,
    private val ioDispatcher: CoroutineDispatcher,

//    private val analyticsHelper: AnalyticsHelper,
//    private val logger: Logger,
) : UserDataRepository {

    override val userSettings: Flow<UserSettings> =
        userPreferencesRepository
            .userPreferences
            .map { it.asUserSettings() }

    override suspend fun setContrast(contrast: Int) {
        withContext(ioDispatcher) {
            userPreferencesRepository.setContrast(contrast)
        }
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        withContext(ioDispatcher) { userPreferencesRepository.setDarkThemeConfig(darkThemeConfig.ordinal) }
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        withContext(ioDispatcher) { userPreferencesRepository.setDynamicColorPreference(useDynamicColor) }
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        withContext(ioDispatcher) { userPreferencesRepository.setShouldHideOnboarding(shouldHideOnboarding) }
    }

    override suspend fun setShouldShowGradientBackground(shouldShowGradientBackground: Boolean) {
        withContext(ioDispatcher) {
            userPreferencesRepository
                .setShouldShowGradientBackground(shouldShowGradientBackground)
        }
    }

    /**
     * Persist the user's selected language preference.
     *
     * @param language The language identifier (e.g., locale code or language tag) used by the app to select localization. */
    override suspend fun setLanguage(language: String) {
        withContext(ioDispatcher) { userPreferencesRepository.setLanguage(language) }
    }

    /**
     * Persist the user's preference for updating from pre-release builds.
     *
     * This suspending function stores the provided flag in the underlying preferences on the IO dispatcher.
     *
     * @param updateFromPreRelease True to enable updating from pre-release builds; false to disable.
     */
    override suspend fun setUpdateFromPreRelease(updateFromPreRelease: Boolean) {
        withContext(ioDispatcher) { userPreferencesRepository.setUpdateFromPreRelease(updateFromPreRelease) }
    }

    /**
     * Persist the user's preference for showing the update dialog.
     *
     * @param showUpdateDialog True to show the update dialog when appropriate; false to suppress it.
     */
    override suspend fun setShowUpdateDialog(showUpdateDialog: Boolean) {
        withContext(ioDispatcher) { userPreferencesRepository.setShowUpdateDialog(showUpdateDialog) }
    }
}
