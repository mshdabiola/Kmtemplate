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
import com.mshdabiola.ui.SharedTransitionContainer // Import SharedTransitionContainer

class SettingScreenScreenshotTests {

    private val sampleOnDrawer: () -> Unit = {}
    private val onContrastChange: (Int) -> Unit = {}
    private val onDarkModeChange: (DarkThemeConfig) -> Unit = {}

    private val defaultSettingState = SettingState(
        contrast = 1, // Standard Contrast
        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
    )

    // Test initial state (List Pane visible) with drawer
    @DevicePreviews
    @Composable
    fun SettingScreen_Initial_Light_WithDrawer() {
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SharedTransitionContainer {
                    // As used in SettingScreenPreview
                    SettingScreen(
                        modifier = Modifier.fillMaxSize(),
                        onDrawer = sampleOnDrawer,
                        settingState = defaultSettingState,
                        onContrastChange = onContrastChange,
                        onDarkModeChange = onDarkModeChange,
                    )
                }
            }
        }
    }

    @DevicePreviews
    @Composable
    fun SettingScreen_Initial_Dark_WithDrawer() {
        KmtTheme(darkTheme = true) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SharedTransitionContainer {
                    SettingScreen(
                        modifier = Modifier.fillMaxSize(),
                        onDrawer = sampleOnDrawer,
                        settingState = defaultSettingState,
                        onContrastChange = onContrastChange,
                        onDarkModeChange = onDarkModeChange,
                    )
                }
            }
        }
    }

    // Test initial state (List Pane visible) without drawer
    @DevicePreviews
    @Composable
    fun SettingScreen_Initial_Light_NoDrawer() {
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SharedTransitionContainer {
                    SettingScreen(
                        modifier = Modifier.fillMaxSize(),
                        onDrawer = null, // No drawer
                        settingState = defaultSettingState,
                        onContrastChange = onContrastChange,
                        onDarkModeChange = onDarkModeChange,
                    )
                }
            }
        }
    }

    @DevicePreviews
    @Composable
    fun SettingScreen_Initial_Dark_NoDrawer() {
        KmtTheme(darkTheme = true) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SharedTransitionContainer {
                    SettingScreen(
                        modifier = Modifier.fillMaxSize(),
                        onDrawer = null, // No drawer
                        settingState = defaultSettingState,
                        onContrastChange = onContrastChange,
                        onDarkModeChange = onDarkModeChange,
                    )
                }
            }
        }
    }

    // Test initial state with a different SettingState (e.g., for Appearance)
    // This primarily tests that the state is passed down,
    // the actual rendering of that state is covered by SettingDetailScreenScreenshotTests
    @DevicePreviews
    @Composable
    fun SettingScreen_Initial_Light_WithDrawer_SpecificSettingState() {
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SharedTransitionContainer {
                    SettingScreen(
                        modifier = Modifier.fillMaxSize(),
                        onDrawer = sampleOnDrawer,
                        settingState = SettingState(
                            contrast = 0, // Low Contrast
                            darkThemeConfig = DarkThemeConfig.DARK,
                        ),
                        onContrastChange = onContrastChange,
                        onDarkModeChange = onDarkModeChange,
                    )
                }
            }
        }
    }

    // --- Considerations for Detail Pane Visibility ---
    // Testing the SettingScreen when the detail pane is actively shown due to navigation
    // is complex for static screenshot tests because it involves the behavior of
    // `rememberListDetailPaneScaffoldNavigator`.
    //
    // For screenshot purposes, you've already tested:
    // 1. `SettingListScreen.kt` in `SettingListScreenScreenshotTests.kt`
    // 2. `SettingDetailScreen.kt` (with various SettingNav options) in `SettingDetailScreenScreenshotTests.kt`
    //
    // These tests for `SettingScreen.kt` will primarily verify its initial layout and the
    // correct setup of `ListDetailPaneScaffold` showing the list pane by default.
    // If you need to verify specific two-pane layouts on larger screens,
    // your `@DevicePreviews` would need to be configured for tablet/desktop sizes,
    // and the ListDetailPaneScaffold's adaptiveness would handle showing both panes if appropriate.
}
