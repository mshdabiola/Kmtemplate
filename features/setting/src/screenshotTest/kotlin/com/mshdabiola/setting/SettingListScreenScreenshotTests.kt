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

class SettingListScreenScreenshotTests {

    private val onSettingClick: (SettingNav) -> Unit = {}
    private val sampleOnDrawer: () -> Unit = {}

    // Prepare the settingsMap similar to how it's done in SettingScreen
    private val settingsMap = SettingNav.entries.groupBy { it.segment }

    @DevicePreviews
    @Composable
    fun SettingListScreen_Light_WithDrawer() {
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SettingListScreen(
                    modifier = Modifier.fillMaxSize(),
                    settingsMap = settingsMap,
                    onDrawer = sampleOnDrawer, // Provide the drawer callback
                    onSettingClick = onSettingClick,
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun SettingListScreen_Dark_WithDrawer() {
        KmtTheme(darkTheme = true) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SettingListScreen(
                    modifier = Modifier.fillMaxSize(),
                    settingsMap = settingsMap,
                    onDrawer = sampleOnDrawer, // Provide the drawer callback
                    onSettingClick = onSettingClick,
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun SettingListScreen_Light_NoDrawer() {
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SettingListScreen(
                    modifier = Modifier.fillMaxSize(),
                    settingsMap = settingsMap,
                    onDrawer = null, // Null to hide the drawer icon
                    onSettingClick = onSettingClick,
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun SettingListScreen_Dark_NoDrawer() {
        KmtTheme(darkTheme = true) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SettingListScreen(
                    modifier = Modifier.fillMaxSize(),
                    settingsMap = settingsMap,
                    onDrawer = null, // Null to hide the drawer icon
                    onSettingClick = onSettingClick,
                )
            }
        }
    }

    // You could also test with an empty settingsMap if that's a valid state,
    // though the current implementation derives it from SettingNav.entries.
    // @DevicePreviews
    // @Composable
    // fun SettingListScreen_Empty_Light() {
    //     KmtTheme(darkTheme = false) {
    //         Surface(modifier = Modifier.fillMaxSize()) {
    //             SettingListScreen(
    //                 modifier = Modifier.fillMaxSize(),
    //                 settingsMap = emptyMap(), // Test with no settings
    //                 onDrawer = null,
    //                 onSettingClick = onSettingClick,
    //             )
    //         }
    //     }
    // }
}
