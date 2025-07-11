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

import co.touchlab.kermit.Logger
import com.mshdabiola.analytics.AnalyticsHelper
import com.mshdabiola.datastore.Store
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.UserData
import kotlinx.coroutines.flow.Flow

internal class OfflineFirstUserDataRepository(
    private val settings: Store,
    private val analyticsHelper: AnalyticsHelper,
    private val logger: Logger,
) : UserDataRepository {
    init {
        logger.d { "OfflineFirstUserDataRepository init" }
    }

    override val userData: Flow<UserData> =
        settings
            .userData

    override suspend fun setContrast(contrast: Int) {
        settings.updateUserData {
            it.copy(contrast = contrast)
        }
        // analyticsHelper.logThemeChanged(themeBrand.name)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        settings.updateUserData {
            it.copy(darkThemeConfig = darkThemeConfig)
        }
        analyticsHelper.logDarkThemeConfigChanged(darkThemeConfig.name)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        settings.updateUserData {
            it.copy(useDynamicColor = useDynamicColor)
        }
        analyticsHelper.logDynamicColorPreferenceChanged(useDynamicColor)
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        settings.updateUserData {
            it.copy(shouldHideOnboarding = shouldHideOnboarding)
        }
        analyticsHelper.logOnboardingStateChanged(shouldHideOnboarding)
    }
}
