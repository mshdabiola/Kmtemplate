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
package com.mshdabiola.kmtemplate

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.mshdabiola.designsystem.theme.KmtTheme // Assuming your theme is needed for proper rendering
import com.mshdabiola.kmtemplate.ui.SplashScreen
import com.mshdabiola.kmtemplate.ui.SplashScreenTestTags // Import the test tags
import org.junit.Rule
import org.junit.Test

class SplashScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun splashScreen_displaysAllElementsCorrectly() {
        // Set the content for the test
        composeTestRule.setContent {
            KmtTheme {
                // Wrap with your app's theme for consistent styling and preview
                SplashScreen()
            }
        }

        // 1. Verify the root Surface is present and displayed
        composeTestRule.onNodeWithTag(SplashScreenTestTags.SCREEN_ROOT)
            .assertExists("Root Surface of SplashScreen should exist")
            .assertIsDisplayed()

        // 2. Verify the brand Image is present and displayed
        composeTestRule.onNodeWithTag(SplashScreenTestTags.BRAND_IMAGE)
            .assertExists("Brand Image on SplashScreen should exist")
            .assertIsDisplayed()

        // 3. Verify the brand Text is present and displayed
        composeTestRule.onNodeWithTag(SplashScreenTestTags.BRAND_TEXT)
            .assertExists("Brand Text on SplashScreen should exist")
            .assertIsDisplayed()

        // Optional: You could also assert the text content if KmtStrings.brand is easily accessible
        // and doesn't rely on Android resources that might be tricky in pure JVM tests.
        // If KmtStrings.brand is a simple const val String:
        // composeTestRule.onNodeWithText(com.mshdabiola.designsystem.strings.KmtStrings.brand).assertIsDisplayed()
        // However, relying on the test tag for the text presence is generally robust.
    }
}
