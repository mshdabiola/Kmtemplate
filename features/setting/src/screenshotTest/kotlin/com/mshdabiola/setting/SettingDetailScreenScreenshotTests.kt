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
package com.mshdabiola.setting

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.DevicePreviews
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.DarkThemeConfig

// Note: SettingDetailScreenScreenshotTests was originally testing AppearanceScreen.
// We are now repurposing it to test SettingDetailScreen itself.
class SettingDetailScreenScreenshotTests {

    private val sampleOnBack: () -> Unit = {}
    private val onContrastChange: (Int) -> Unit = {}
    private val onDarkModeChange: (DarkThemeConfig) -> Unit = {}

    private val defaultSettingState = SettingState(
        contrast = 1, // Standard Contrast
        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
    )

    // --- Appearance Section ---

    @DevicePreviews
    @Composable
    fun SettingDetail_Appearance_Light_WithBack() {
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SettingDetailScreen(
                    onBack = sampleOnBack,
                    settingNav = SettingNav.Appearance,
                    settingState = defaultSettingState,
                    onContrastChange = onContrastChange,
                    onDarkModeChange = onDarkModeChange,
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun SettingDetail_Appearance_Dark_WithBack_LowContrast_DarkModeSelected() {
        KmtTheme(darkTheme = true) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SettingDetailScreen(
                    onBack = sampleOnBack,
                    settingNav = SettingNav.Appearance,
                    settingState = SettingState(
                        contrast = 0, // Low Contrast
                        darkThemeConfig = DarkThemeConfig.DARK, // Dark mode selected in settings
                    ),
                    onContrastChange = onContrastChange,
                    onDarkModeChange = onDarkModeChange,
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun SettingDetail_Appearance_Light_NoBack() {
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SettingDetailScreen(
                    onBack = null, // No back action
                    settingNav = SettingNav.Appearance,
                    settingState = defaultSettingState,
                    onContrastChange = onContrastChange,
                    onDarkModeChange = onDarkModeChange,
                )
            }
        }
    }

    // --- FAQ Section ---

    @DevicePreviews
    @Composable
    fun SettingDetail_Faq_Light_WithBack() {
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SettingDetailScreen(
                    onBack = sampleOnBack,
                    settingNav = SettingNav.Faq,
                    settingState = defaultSettingState, // Not used by FaqScreen
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun SettingDetail_Faq_Dark_NoBack() {
        KmtTheme(darkTheme = true) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SettingDetailScreen(
                    onBack = null, // No back action
                    settingNav = SettingNav.Faq,
                    settingState = defaultSettingState,
                )
            }
        }
    }

    // --- About Section ---

    @DevicePreviews
    @Composable
    fun SettingDetail_About_Light_WithBack() {
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SettingDetailScreen(
                    onBack = sampleOnBack,
                    settingNav = SettingNav.About,
                    settingState = defaultSettingState, // Not used by AboutScreen
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun SettingDetail_About_Dark_NoBack() {
        KmtTheme(darkTheme = true) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SettingDetailScreen(
                    onBack = null, // No back action
                    settingNav = SettingNav.About,
                    settingState = defaultSettingState,
                )
            }
        }
    }

    // --- Issue Section (Placeholder) ---
    // This will show the screen with the title "Report an Issue" (or similar from your resources)
    // and an empty content area as per your `when` statement's TODO.

    @DevicePreviews
    @Composable
    fun SettingDetail_Issue_Light_WithBack() {
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SettingDetailScreen(
                    onBack = sampleOnBack,
                    settingNav = SettingNav.Issue,
                    settingState = defaultSettingState,
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun SettingDetail_Issue_Dark_NoBack() {
        KmtTheme(darkTheme = true) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SettingDetailScreen(
                    onBack = null, // No back action
                    settingNav = SettingNav.Issue,
                    settingState = defaultSettingState,
                )
            }
        }
    }
}
