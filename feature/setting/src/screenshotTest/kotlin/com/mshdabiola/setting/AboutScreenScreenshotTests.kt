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
import com.mshdabiola.setting.detailscreen.AboutScreen // Import the correct AboutScreen

class AboutScreenScreenshotTests {

    // AboutScreen does not take callbacks or complex state for its core display,
    // so we mainly test light and dark themes.
    // The internal click handlers for email, privacy policy, etc.,
    // don't change the visual appearance for a static screenshot.

    @DevicePreviews
    @Composable
    fun AboutScreen_LightMode() {
        KmtTheme(darkTheme = false) {
            // Explicitly light theme
            Surface(modifier = Modifier.fillMaxSize()) {
                // Surface provides a background
                AboutScreen(
                    modifier = Modifier.fillMaxSize(),
                    // openEmail, openPrivacyPolicy, openTermsAndConditions
                    // are not passed as parameters to AboutScreen directly in your implementation,
                    // they are created within the Composable. For screenshot tests, this is fine
                    // as we are only concerned with the visual output.
                )
            }
        }
    }

    @DevicePreviews
    @Composable
    fun AboutScreen_DarkMode() {
        KmtTheme(darkTheme = true) {
            // Explicitly dark theme
            Surface(modifier = Modifier.fillMaxSize()) {
                AboutScreen(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
