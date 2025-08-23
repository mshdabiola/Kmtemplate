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
import com.mshdabiola.setting.detailscreen.FaqScreen // Import FaqScreen

class FaqScreenScreenshotTests {

    @DevicePreviews
    @Composable
    fun FaqScreen_WithItems_LightMode() {
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                // FaqScreen internally defines its own list of FaqItems.
                // For this test, we rely on that internal list.
                FaqScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }

    @DevicePreviews
    @Composable
    fun FaqScreen_WithItems_DarkMode() {
        KmtTheme(darkTheme = true) {
            Surface(modifier = Modifier.fillMaxSize()) {
                FaqScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }

    // To test the empty state, we would ideally pass an empty list to FaqScreen.
    // However, the current FaqScreen.kt defines its `questions` list internally.
    // To properly screenshot test the empty state, FaqScreen would need to accept
    // the list of FaqItems as a parameter.

    // If FaqScreen were modified to accept `items: List<FaqItem>`:
    /*
    @DevicePreviews
    @Composable
    fun FaqScreen_EmptyState_LightMode() {
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                FaqScreen(
                    modifier = Modifier.fillMaxSize(),
                    // items = emptyList() // Assuming FaqScreen is modified
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun FaqScreen_EmptyState_DarkMode() {
        KmtTheme(darkTheme = true) {
            Surface(modifier = Modifier.fillMaxSize()) {
                FaqScreen(
                    modifier = Modifier.fillMaxSize(),
                    // items = emptyList() // Assuming FaqScreen is modified
                )
            }
        }
    }
     */

    // For now, the existing tests will cover the non-empty state in light/dark modes.
    // The internal previews within FaqScreen.kt already cover some of these states,
    // but screenshot tests provide automated regression checking.
}
