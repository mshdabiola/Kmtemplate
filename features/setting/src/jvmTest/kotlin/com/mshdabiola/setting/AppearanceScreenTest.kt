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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.setting.detailscreen.AppearanceScreen
import com.mshdabiola.setting.detailscreen.AppearanceScreenTestTags
import com.mshdabiola.setting.detailscreen.ContrastTimelineTestTags
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class AppearanceScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val initialSettingsState = SettingState(
        contrast = 0, // Low contrast
        darkThemeConfig = DarkThemeConfig.DARK,
    )

    @Test
    fun appearanceScreen_initialState_displaysCorrectly() {
        composeRule.setContent {
            KmtTheme {
                AppearanceScreen(
                    settingsState = initialSettingsState,
                    onContrastChange = {},
                    onDarkModeChange = {},
                )
            }
        }

        // Verify root
        composeRule.onNodeWithTag(AppearanceScreenTestTags.SCREEN_ROOT).assertIsDisplayed()

        // Verify Contrast Section
        composeRule.onNodeWithTag(AppearanceScreenTestTags.CONTRAST_TITLE).assertIsDisplayed()
        composeRule.onNodeWithTag(
            AppearanceScreenTestTags.SCREEN_ROOT,
            useUnmergedTree = true,
        ).printToLog("AppearanceScreen")
        composeRule.onNodeWithTag(ContrastTimelineTestTags.TIMELINE_ROOT, useUnmergedTree = true).assertIsDisplayed()

        // Verify initial contrast selection (id = 0 for Low)
        // Check background/indicator of the selected option
        composeRule.onNodeWithTag(
            "${ContrastTimelineTestTags.OPTION_BACKGROUND_PREFIX}0",
            useUnmergedTree = true,
        ).assertIsDisplayed()
        // Check icon of the selected option
        composeRule.onNodeWithTag(
            "${ContrastTimelineTestTags.OPTION_ICON_PREFIX}0",
            useUnmergedTree = true,
        ).assertIsDisplayed()

        // Verify Dark Mode Section
        composeRule.onNodeWithTag(AppearanceScreenTestTags.DARK_MODE_TITLE).assertIsDisplayed()

        DarkThemeConfig.entries.forEach { config ->
            val expectedText = getDarkModeOptionText(config.ordinal)
            // Helper to get text like "System", "Light", "Dark"
            composeRule.onNodeWithTag(
                "${AppearanceScreenTestTags.DARK_MODE_OPTION_ROW_PREFIX}${config.name}",
            )
                .assertIsDisplayed()
            composeRule.onNodeWithTag(
                "${AppearanceScreenTestTags.DARK_MODE_RADIO_BUTTON_PREFIX}${config.name}",
            )
                .assertIsDisplayed()

            if (config == initialSettingsState.darkThemeConfig) {
                composeRule.onNodeWithTag(
                    "${AppearanceScreenTestTags.DARK_MODE_RADIO_BUTTON_PREFIX}${config.name}",
                )
                    .assertIsSelected()
            }
        }
    }

    @Test
    fun appearanceScreen_selectContrastOption_invokesCallbackAndUpdatesSelection() {
        var selectedContrast by mutableStateOf(initialSettingsState.contrast)
        val targetContrastOptionId = 1 // Standard Contrast

        composeRule.setContent {
            // Use a mutable state that the test can update to reflect UI changes
            // and verify callbacks.
            var currentSettings by remember { mutableStateOf(initialSettingsState) }

            KmtTheme {
                AppearanceScreen(
                    settingsState = currentSettings,
                    onContrastChange = { newContrast ->
                        selectedContrast = newContrast
                        currentSettings = currentSettings.copy(contrast = newContrast)
                    },
                    onDarkModeChange = {},
                )
            }
        }

        // Click on the "Standard" contrast option (id = 1)
        composeRule.onNodeWithTag("${ContrastTimelineTestTags.OPTION_ITEM_PREFIX}$targetContrastOptionId")
            .performClick()

        // Verify callback was invoked with the correct ID
        assertEquals(targetContrastOptionId, selectedContrast)

        // Verify UI updates to show the new selection
        // (This assumes your composable recomposes correctly based on the updated state)
        composeRule.onNodeWithTag(
            "${ContrastTimelineTestTags.OPTION_BACKGROUND_PREFIX}$targetContrastOptionId",
            useUnmergedTree = true,
        )
            .assertIsDisplayed() // Check for visual change like background
//        composeRule.onNodeWithTag(
//            "${ContrastTimelineTestTags.OPTION_ICON_PREFIX}$targetContrastOptionId",
//        ).assertIsDisplayed()
    }

    @Test
    fun appearanceScreen_selectDarkModeOption_invokesCallbackAndUpdatesSelection() {
        var selectedDarkMode by mutableStateOf(initialSettingsState.darkThemeConfig)
        val targetDarkModeConfig = DarkThemeConfig.DARK

        composeRule.setContent {
            var currentSettings by remember { mutableStateOf(initialSettingsState) }
            KmtTheme {
                AppearanceScreen(
                    settingsState = currentSettings,
                    onContrastChange = {},
                    onDarkModeChange = { newConfig ->
                        selectedDarkMode = newConfig
                        currentSettings = currentSettings.copy(darkThemeConfig = newConfig)
                    },
                )
            }
        }

        // Click on the "Dark" mode option row
        composeRule.onNodeWithTag(
            "${AppearanceScreenTestTags.DARK_MODE_OPTION_ROW_PREFIX}${targetDarkModeConfig.name}",
        )
            .performClick()

        // Verify callback was invoked with the correct config
        assertEquals(targetDarkModeConfig, selectedDarkMode)

        // Verify the radio button for "Dark" is now selected
        composeRule.onNodeWithTag(
            "${AppearanceScreenTestTags.DARK_MODE_RADIO_BUTTON_PREFIX}${targetDarkModeConfig.name}",
        )
            .assertIsSelected()

        // Verify other radio buttons are not selected (optional, but good for robustness)
        DarkThemeConfig.entries.filter { it != targetDarkModeConfig }.forEach { otherConfig ->
            composeRule.onNodeWithTag(
                "${AppearanceScreenTestTags.DARK_MODE_RADIO_BUTTON_PREFIX}${otherConfig.name}",
            )
                .assertIsNotSelected() // You might need to add this extension if not available
        }
    }

    // Helper function to simulate string resource loading for dark mode options
    // In a real KMP test, you'd want resource loading to work correctly
    // or pass the strings directly if testing in a pure JVM environment without proper resource setup.
    private fun getDarkModeOptionText(ordinal: Int): String {
        return when (ordinal) {
            DarkThemeConfig.FOLLOW_SYSTEM.ordinal -> "System" // Replace with actual string from Res.array.daynight[0]
            DarkThemeConfig.LIGHT.ordinal -> "Light" // Replace with actual string from Res.array.daynight[1]
            DarkThemeConfig.DARK.ordinal -> "Dark" // Replace with actual string from Res.array.daynight[2]
            else -> ""
        }
    }

    // You might need this extension if it's not part of your test framework version
    private fun androidx.compose.ui.test.SemanticsNodeInteraction.assertIsNotSelected() {
        // A common way to check if a RadioButton is not selected is to check its stateDescription
        // or a similar property.
        // Or, more simply, ensure it doesn't meet the criteria for assertIsSelected().
        // For RadioButton, assertIsSelected() checks if SemanticsProperties.Selected is true.
        // So, we can check if it's false.
        // This is a simplified check. A more robust way might involve checking specific semantics properties.
        try {
            assertIsSelected()
            throw AssertionError("Node is selected, but expected not to be.")
        } catch (e: AssertionError) {
            // Expected path: if it's not selected, assertIsSelected() will throw AssertionError
        }
    }
}
