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

class TestUserPreferenceDataSource : UserPreferencesDataSource{
    override val userPreferences: Flow<UserPreferences>
        get() = TODO("Not yet implemented")

    override suspend fun setContrast(contrast: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun setShouldShowGradientBackground(shouldShowGradientBackground: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun setLanguage(language: String) {
        TODO("Not yet implemented")
    }

}
