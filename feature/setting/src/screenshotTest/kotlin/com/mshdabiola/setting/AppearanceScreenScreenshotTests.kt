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
package com.mshdabiola.setting

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.DevicePreviews
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.setting.detailscreen.AppearanceScreen

class AppearanceScreenScreenshotTests {

    private val onContrastChange: (Int) -> Unit = {}
    private val onDarkModeChange: (DarkThemeConfig) -> Unit = {}

    @DevicePreviews
    @Composable
    fun AppearanceScreenLight_DefaultContrast_SystemDark() {
        // Using KmtTheme wraps MaterialTheme and provides your app's specific styling
        KmtTheme(darkTheme = false) {
            // Explicitly light theme for this test
            Surface(modifier = Modifier.fillMaxSize()) {
                // Surface provides a background
                AppearanceScreen(
                    modifier = Modifier.fillMaxSize(),
                    settingsState = SettingState(
                        contrast = 1, // Standard Contrast
                        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                    ),
                    onContrastChange = onContrastChange,
                    onDarkModeChange = onDarkModeChange,
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun AppearanceScreenDark_DefaultContrast_SystemDark() {
        KmtTheme(darkTheme = true) {
            // Explicitly dark theme
            Surface(modifier = Modifier.fillMaxSize()) {
                AppearanceScreen(
                    modifier = Modifier.fillMaxSize(),
                    settingsState = SettingState(
                        contrast = 1, // Standard Contrast
                        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                    ),
                    onContrastChange = onContrastChange,
                    onDarkModeChange = onDarkModeChange,
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun AppearanceScreenLight_LowContrast_LightModeSelected() {
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                AppearanceScreen(
                    modifier = Modifier.fillMaxSize(),
                    settingsState = SettingState(
                        contrast = 0, // Low Contrast
                        darkThemeConfig = DarkThemeConfig.LIGHT, // Light mode selected
                    ),
                    onContrastChange = onContrastChange,
                    onDarkModeChange = onDarkModeChange,
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun AppearanceScreenDark_HighContrast_DarkModeSelected() {
        KmtTheme(darkTheme = true) {
            Surface(modifier = Modifier.fillMaxSize()) {
                AppearanceScreen(
                    modifier = Modifier.fillMaxSize(),
                    settingsState = SettingState(
                        contrast = 2, // High Contrast
                        darkThemeConfig = DarkThemeConfig.DARK, // Dark mode selected
                    ),
                    onContrastChange = onContrastChange,
                    onDarkModeChange = onDarkModeChange,
                )
            }
        }
    }

    // Add more previews for other interesting states:
    // e.g., Different contrast levels selected in the timeline

    @DevicePreviews
    @Composable
    fun AppearanceScreenLight_ContrastOption0Selected() {
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                AppearanceScreen(
                    modifier = Modifier.fillMaxSize(),
                    settingsState = SettingState(
                        contrast = 0, // Low contrast selected
                        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                    ),
                    onContrastChange = onContrastChange,
                    onDarkModeChange = onDarkModeChange,
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun AppearanceScreenLight_ContrastOption2Selected() {
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                AppearanceScreen(
                    modifier = Modifier.fillMaxSize(),
                    settingsState = SettingState(
                        contrast = 2, // High contrast selected
                        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                    ),
                    onContrastChange = onContrastChange,
                    onDarkModeChange = onDarkModeChange,
                )
            }
        }
    }
}
