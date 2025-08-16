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
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.UserSettings
import com.mshdabiola.model.testtag.AppearanceScreenTestTags
import com.mshdabiola.setting.detailscreen.AppearanceScreen
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class AppearanceScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val initialSettingsState = UserSettings(
            contrast = 0, // Low contrast (id=0)
            darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
            shouldShowGradientBackground = true,
        )


    @Test
    fun appearanceScreen_initialState_displaysCorrectly() {
        composeRule.setContent {
            KmtTheme {
                AppearanceScreen(
                    userSettings = initialSettingsState,
                    onContrastChange = {},
                    onDarkModeChange = {},
                    onGradientBackgroundChange = {},
                )
            }
        }

        // Verify root
        composeRule.onNodeWithTag(AppearanceScreenTestTags.SCREEN_ROOT).assertIsDisplayed()

        // Verify Contrast Section
        composeRule.onNodeWithTag(AppearanceScreenTestTags.CONTRAST_TITLE).assertIsDisplayed()
        composeRule.onNodeWithTag(
            AppearanceScreenTestTags
                .ContrastTimelineTestTags.TIMELINE_ROOT,
            useUnmergedTree = true,
        )
            .assertIsDisplayed()
        // Check initial contrast selection (id = 0 for Low)
        composeRule.onNodeWithTag(
            AppearanceScreenTestTags
                .ContrastTimelineTestTags.optionBackground(0),
            useUnmergedTree = true,
        )
            .assertIsDisplayed()
        composeRule.onNodeWithTag(
            AppearanceScreenTestTags
                .ContrastTimelineTestTags.optionIcon(0),
            useUnmergedTree = true,
        )
            .assertIsDisplayed()

        // Verify Background Section
        composeRule.onNodeWithTag(AppearanceScreenTestTags.BACKGROUND_TITLE).assertIsDisplayed()
        composeRule.onNodeWithTag(AppearanceScreenTestTags.GRADIENT_BACKGROUND_ROW).assertIsDisplayed()
        composeRule.onNodeWithTag(AppearanceScreenTestTags.GRADIENT_BACKGROUND_TEXT, useUnmergedTree = true)
            .assertIsDisplayed()
        composeRule.onNodeWithTag(AppearanceScreenTestTags.GRADIENT_BACKGROUND_SWITCH).assertIsDisplayed()
        if (initialSettingsState.shouldShowGradientBackground) {
            composeRule.onNodeWithTag(AppearanceScreenTestTags.GRADIENT_BACKGROUND_SWITCH).assertIsOn()
        } else {
            composeRule.onNodeWithTag(AppearanceScreenTestTags.GRADIENT_BACKGROUND_SWITCH).assertIsOff()
        }

        // Verify Dark Mode Section
        composeRule.onNodeWithTag(AppearanceScreenTestTags.DARK_MODE_TITLE).assertIsDisplayed()
        DarkThemeConfig.entries.forEach { config ->
            composeRule.onNodeWithTag(AppearanceScreenTestTags.darkModeOptionRow(config.name))
                .assertIsDisplayed()
            composeRule.onNodeWithTag(AppearanceScreenTestTags.darkModeRadioButton(config.name))
                .assertIsDisplayed()
            composeRule.onNodeWithTag(
                AppearanceScreenTestTags.darkModeOptionText(config.name),
                useUnmergedTree = true,
            )
                .assertIsDisplayed()

            if (config == initialSettingsState.darkThemeConfig) {
                composeRule.onNodeWithTag(AppearanceScreenTestTags.darkModeRadioButton(config.name))
                    .assertIsSelected()
            } else {
                composeRule.onNodeWithTag(AppearanceScreenTestTags.darkModeRadioButton(config.name))
                    .assertIsNotSelected()
            }
        }
    }

    @Test
    fun appearanceScreen_selectContrastOption_invokesCallbackAndUpdatesSelection() {
        var callbackContrast: Int? = null
        val targetContrastId = 1 // Standard Contrast

        composeRule.setContent {
            var currentSettings by remember { mutableStateOf(initialSettingsState) }
            KmtTheme {
                AppearanceScreen(
                    userSettings = currentSettings,
                    onContrastChange = { newContrast ->
                        callbackContrast = newContrast
                        currentSettings = currentSettings.copy(contrast = newContrast)
                    },
                    onDarkModeChange = {},
                    onGradientBackgroundChange = {},
                )
            }
        }

        composeRule.onNodeWithTag(
            AppearanceScreenTestTags.ContrastTimelineTestTags.optionItem(targetContrastId),
            useUnmergedTree = true,
        )
            .performClick()

        assertEquals(targetContrastId, callbackContrast)
        // Verify UI updates (the new option item should now be visually selected)
        composeRule.onNodeWithTag(
            AppearanceScreenTestTags.ContrastTimelineTestTags.optionBackground(targetContrastId),
            useUnmergedTree = true,
        ).assertIsDisplayed()
        composeRule.onNodeWithTag(
            AppearanceScreenTestTags.ContrastTimelineTestTags.optionIcon(targetContrastId),
            useUnmergedTree = true,
        ).assertIsDisplayed()
    }

    @Test
    fun appearanceScreen_toggleGradientBackground_invokesCallbackAndUpdatesSwitch() {
        var callbackGradient: Boolean? = null
        val expectedGradientValue = !initialSettingsState.shouldShowGradientBackground

        composeRule.setContent {
            var currentSettings by remember { mutableStateOf(initialSettingsState) }
            KmtTheme {
                AppearanceScreen(
                    userSettings = currentSettings,
                    onContrastChange = {},
                    onDarkModeChange = {},
                    onGradientBackgroundChange = { newGradientState ->
                        callbackGradient = newGradientState
                        currentSettings = currentSettings.copy(shouldShowGradientBackground = newGradientState)
                    },
                )
            }
        }

        // Click the row to toggle, or directly on the switch if preferred
        composeRule.onNodeWithTag(AppearanceScreenTestTags.GRADIENT_BACKGROUND_ROW).performClick()
        // Or, if clicking the switch directly: composeRule.onNodeWithTag(AppearanceScreenTestTags.GRADIENT_BACKGROUND_SWITCH).performClick()

        assertEquals(expectedGradientValue, callbackGradient)
        if (expectedGradientValue) {
            composeRule.onNodeWithTag(AppearanceScreenTestTags.GRADIENT_BACKGROUND_SWITCH).assertIsOn()
        } else {
            composeRule.onNodeWithTag(AppearanceScreenTestTags.GRADIENT_BACKGROUND_SWITCH).assertIsOff()
        }
    }

    @Test
    fun appearanceScreen_selectDarkModeOption_invokesCallbackAndUpdatesSelection() {
        var callbackDarkMode: DarkThemeConfig? = null
        val targetDarkModeConfig = DarkThemeConfig.DARK

        composeRule.setContent {
            var currentSettings by remember { mutableStateOf(initialSettingsState) }
            KmtTheme {
                AppearanceScreen(
                    userSettings = currentSettings,
                    onContrastChange = {},
                    onDarkModeChange = { newConfig ->
                        callbackDarkMode = newConfig
                        currentSettings = currentSettings.copy(darkThemeConfig = newConfig)
                    },
                    onGradientBackgroundChange = {},
                )
            }
        }

        composeRule.onNodeWithTag(AppearanceScreenTestTags.darkModeOptionRow(targetDarkModeConfig.name))
            .performClick()

        assertEquals(targetDarkModeConfig, callbackDarkMode)
        composeRule.onNodeWithTag(AppearanceScreenTestTags.darkModeRadioButton(targetDarkModeConfig.name))
            .assertIsSelected()

        // Verify other radio buttons are not selected
        DarkThemeConfig.entries.filter { it != targetDarkModeConfig }.forEach { otherConfig ->
            composeRule.onNodeWithTag(AppearanceScreenTestTags.darkModeRadioButton(otherConfig.name))
                .assertIsNotSelected()
        }
    }
}
