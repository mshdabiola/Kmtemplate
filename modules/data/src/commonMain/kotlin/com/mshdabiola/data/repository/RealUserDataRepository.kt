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

import com.mshdabiola.datastore.UserPreferencesRepository
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.UserData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class RealUserDataRepository(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val ioDispatcher: CoroutineDispatcher,

//    private val analyticsHelper: AnalyticsHelper,
//    private val logger: Logger,
) : UserDataRepository {

    override val userData: Flow<UserData> =
        userPreferencesRepository
            .userData

    override suspend fun setContrast(contrast: Int) {
        withContext(ioDispatcher) {
            userPreferencesRepository.setContrast(contrast)
        }
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        withContext(ioDispatcher) { userPreferencesRepository.setDarkThemeConfig(darkThemeConfig) }
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        withContext(ioDispatcher) { userPreferencesRepository.setDynamicColorPreference(useDynamicColor) }
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        withContext(ioDispatcher) { userPreferencesRepository.setShouldHideOnboarding(shouldHideOnboarding) }
    }

    override suspend fun setShouldShowGradientBackground(shouldShowGradientBackground: Boolean) {
        withContext(ioDispatcher) { userPreferencesRepository
            .setShouldShowGradientBackground(shouldShowGradientBackground) }
    }

    override suspend fun setLanguage(language: Int) {
        withContext(ioDispatcher) { userPreferencesRepository.setLanguage(language) }
    }
}
