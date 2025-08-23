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
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RealUserPreferencesDataSource : UserPreferencesDataSource {
    private val store: KStore<UserPreferences> = storeOf(key = "userdata", default = UserPreferences())
    override val userPreferences: Flow<UserPreferences>
        get() = store.updates.map { it ?: UserPreferences() }

    override suspend fun setContrast(contrast: Int) {
        store.update { it?.copy(contrast = contrast) }
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: Int) {
        store.update { it?.copy(darkThemeConfig = darkThemeConfig) }
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        store.update { it?.copy(useDynamicColor = useDynamicColor) }
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        store.update { it?.copy(shouldHideOnboarding = shouldHideOnboarding) }
    }

    override suspend fun setShouldShowGradientBackground(shouldShowGradientBackground: Boolean) {
        store.update { it?.copy(shouldShowGradientBackground = shouldShowGradientBackground) }
    }

    /**
     * Persistently updates the user's preferred language.
     *
     * Sets the `language` field in the stored UserPreferences to the provided value.
     *
     * @param language The language identifier to persist (e.g., "en", "fr", "es").
     *
     * If there are no existing preferences in the store (current value is `null`), no change is made.
     */
    override suspend fun setLanguage(language: String) {
        store.update { it?.copy(language = language) }
    }

    /**
     * Persistently sets whether the user opts in to updates from pre-release versions.
     *
     * Updates the stored UserPreferences' `updateFromPreRelease` flag to the provided value.
     *
     * @param updateFromPreRelease True to enable receiving pre-release updates; false to disable.
     */
    override suspend fun setUpdateFromPreRelease(updateFromPreRelease: Boolean) {
        store.update { it?.copy(updateFromPreRelease = updateFromPreRelease) }
    }

    /**
     * Persistently sets whether the update dialog should be shown to the user.
     *
     * Updates the stored UserPreferences' `showUpdateDialog` flag to the provided value.
     *
     * @param showUpdateDialog true to show the update dialog, false to hide it
     */
    override suspend fun setShowUpdateDialog(showUpdateDialog: Boolean) {
        store.update { it?.copy(showUpdateDialog = showUpdateDialog) }
    }
}
