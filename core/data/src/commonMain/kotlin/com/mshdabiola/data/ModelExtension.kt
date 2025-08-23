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
package com.mshdabiola.data

import com.mshdabiola.datastore.model.UserPreferences
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.UserSettings

/**
 * Converts this UserPreferences instance into a UserSettings value.
 *
 * Maps each stored preference field to the corresponding UserSettings property. The integer
 * `darkThemeConfig` is resolved into a DarkThemeConfig enum by indexing `DarkThemeConfig.entries`;
 * if the stored index is out of range the function falls back to `DarkThemeConfig.FOLLOW_SYSTEM`.
 *
 * @return A new UserSettings populated from this UserPreferences.
 */
fun UserPreferences.asUserSettings() = UserSettings(
    useDynamicColor = useDynamicColor,
    shouldHideOnboarding = shouldHideOnboarding,
    shouldShowGradientBackground = shouldShowGradientBackground,
    language = language,
    darkThemeConfig = DarkThemeConfig.entries
        .getOrElse(darkThemeConfig) { DarkThemeConfig.FOLLOW_SYSTEM },
    contrast = contrast,
    showUpdateDialog = showUpdateDialog,
    updateFromPreRelease = updateFromPreRelease,
)

/**
 * Convert this UserSettings instance to a UserPreferences datastore model.
 *
 * Copies all settings fields; the enum `darkThemeConfig` is stored as its ordinal
 * integer. Includes `showUpdateDialog` and `updateFromPreRelease`.
 *
 * @return A new UserPreferences populated from this UserSettings.
 */
fun UserSettings.asUserPreferences() = UserPreferences(
    useDynamicColor = useDynamicColor,
    shouldHideOnboarding = shouldHideOnboarding,
    shouldShowGradientBackground = shouldShowGradientBackground,
    language = language,
    darkThemeConfig = darkThemeConfig.ordinal,
    contrast = contrast,
    showUpdateDialog = showUpdateDialog,
    updateFromPreRelease = updateFromPreRelease,
)
