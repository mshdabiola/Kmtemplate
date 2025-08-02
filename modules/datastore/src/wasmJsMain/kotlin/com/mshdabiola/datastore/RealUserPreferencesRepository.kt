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

import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.UserData
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RealUserPreferencesRepository : UserPreferencesRepository {
    private val store: KStore<UserData> = storeOf(key = "userdata", default = UserData())
    override val userData: Flow<UserData>
        get() = store.updates.map { it ?: UserData() }

    override suspend fun setContrast(contrast: Int) {
        store.update { it?.copy(contrast = contrast) }
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
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

    override suspend fun setLanguage(language: Int) {
        store.update { it?.copy(language = language) }
    }
}
