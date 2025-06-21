/*
 * Copyright (C) 2024-2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package com.mshdabiola.data.repository

import co.touchlab.kermit.Logger
import com.mshdabiola.analytics.AnalyticsHelper
import com.mshdabiola.datastore.Store
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
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

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        settings.updateUserData {
            it.copy(themeBrand)
        }
        analyticsHelper.logThemeChanged(themeBrand.name)
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
