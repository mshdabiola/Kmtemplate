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

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.UserSettings
import com.mshdabiola.model.testtag.AboutScreenTestTags
import com.mshdabiola.model.testtag.AppearanceScreenTestTags
import com.mshdabiola.model.testtag.FaqScreenTestTags
import com.mshdabiola.model.testtag.SettingDetailScreenTestTags
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class SettingDetailScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val sampleSettingState = SettingState(
        userSettings = UserSettings(
            contrast = 0,
            darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
        ))

    // Helper to get expected titles. In a real scenario with complex resource setup for tests,
    // you might predefine these or ensure stringArrayResource works as expected.
    private fun getExpectedTitle(settingNav: SettingNav): String {
        // This is a simplified lookup. Your actual stringArrayResource logic is more complex.
        // For testing, you'd ideally have a way to mock or provide these resource values
        // or ensure Robolectric can load them correctly.
        return when (settingNav) {
            SettingNav.Appearance -> "Appearance" // Expected from generalArrayString[0]
            SettingNav.Faq -> "FAQ" // Expected from supportArrayString[0]
            SettingNav.About -> "About" // Expected from supportArrayString[1]
            SettingNav.Issue -> "Report an Issue" // Expected from supportArrayString[2]
            SettingNav.Language -> "Language" // Expected from generalArrayString[1]
            // Add other cases as needed
        }
    }

    @Test
    fun settingDetailScreen_topBarAndTitle_displaysCorrectly() {
        val currentNav = SettingNav.Appearance
        val expectedTitle = getExpectedTitle(currentNav) // Or directly use the known string if simpler

        composeRule.setContent {
            KmtTheme {
                SettingDetailScreen(
                    onBack = {}, // Provide a non-null onBack to show the back button
                    settingNav = currentNav,
                    settingState = sampleSettingState,
                )
            }
        }

        composeRule.onNodeWithTag(SettingDetailScreenTestTags.SCREEN_ROOT).assertIsDisplayed()
        composeRule.onNodeWithTag(SettingDetailScreenTestTags.TOP_APP_BAR).assertIsDisplayed()

        // Check title text - this relies on stringArrayResource working or using a known string.
        // If resource loading is an issue in tests, checking for the node by tag might be more robust
        // for the title's container, and then a child text node if needed.
        composeRule.onNodeWithText(expectedTitle).assertIsDisplayed()
    }

    @Test
    fun settingDetailScreen_backButton_displaysWhenOnBackIsNotNull() {
        var backClicked = false
        composeRule.setContent {
            KmtTheme {
                SettingDetailScreen(
                    onBack = { backClicked = true },
                    settingNav = SettingNav.Faq,
                    settingState = sampleSettingState,
                )
            }
        }

        composeRule.onNodeWithTag(SettingDetailScreenTestTags.BACK_ICON_BUTTON)
            .assertIsDisplayed()
            .performClick()

        assertTrue(backClicked)
    }

    @Test
    fun settingDetailScreen_backButton_doesNotExistWhenOnBackIsNull() {
        composeRule.setContent {
            KmtTheme {
                SettingDetailScreen(
                    onBack = null, // Key: onBack is null
                    settingNav = SettingNav.About,
                    settingState = sampleSettingState,
                )
            }
        }

        composeRule.onNodeWithTag(SettingDetailScreenTestTags.BACK_ICON_BUTTON).assertDoesNotExist()
    }

    @Test
    fun settingDetailScreen_showsFaqScreen_whenNavIsFaq() {
        composeRule.setContent {
            KmtTheme {
                SettingDetailScreen(
                    onBack = {},
                    settingNav = SettingNav.Faq,
                    settingState = sampleSettingState,
                )
            }
        }
        // Check for a known tag within FaqScreen
        composeRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertIsDisplayed()
        // Ensure other screens aren't shown (optional, but good for clarity)
        composeRule.onNodeWithTag(AppearanceScreenTestTags.SCREEN_ROOT).assertDoesNotExist()
        composeRule.onNodeWithTag(AboutScreenTestTags.SCREEN_ROOT).assertDoesNotExist()
    }

    @Test
    fun settingDetailScreen_showsAboutScreen_whenNavIsAbout() {
        composeRule.setContent {
            KmtTheme {
                SettingDetailScreen(
                    onBack = {},
                    settingNav = SettingNav.About,
                    settingState = sampleSettingState,
                )
            }
        }
        composeRule.onNodeWithTag(AboutScreenTestTags.SCREEN_ROOT).assertIsDisplayed()
        composeRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertDoesNotExist()
        composeRule.onNodeWithTag(AppearanceScreenTestTags.SCREEN_ROOT).assertDoesNotExist()
    }

    @Test
    fun settingDetailScreen_showsAppearanceScreen_whenNavIsAppearance() {
        var contrastChangedValue: Int? = null
        var darkModeChangedValue: DarkThemeConfig? = null

        composeRule.setContent {
            KmtTheme {
                SettingDetailScreen(
                    onBack = {},
                    settingNav = SettingNav.Appearance,
                    settingState = sampleSettingState,
                    onContrastChange = { contrastChangedValue = it },
                    onDarkModeChange = { darkModeChangedValue = it },
                )
            }
        }
        composeRule.onNodeWithTag(AppearanceScreenTestTags.SCREEN_ROOT).assertIsDisplayed()
        // Verify callbacks are passed down (by interacting with AppearanceScreen if necessary)
        // For example, click a contrast option within AppearanceScreen:
        // This assumes ContrastTimelineTestTags.OPTION_ITEM_PREFIX1 is a valid tag in AppearanceScreen
        val targetContrastOptionId = 1 // Example: Standard Contrast
        composeRule.onNodeWithTag(
            AppearanceScreenTestTags.ContrastTimelineTestTags
                .optionItem(targetContrastOptionId),
        )
            .performClick()
        assertEquals(targetContrastOptionId, contrastChangedValue)

        // Similarly for dark mode:
        val targetDarkModeConfig = DarkThemeConfig.DARK
        composeRule.onNodeWithTag(
            AppearanceScreenTestTags.darkModeOptionRow(targetDarkModeConfig.name),
        )
            .performClick()
        assertEquals(targetDarkModeConfig, darkModeChangedValue)

        composeRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertDoesNotExist()
        composeRule.onNodeWithTag(AboutScreenTestTags.SCREEN_ROOT).assertDoesNotExist()
    }

    @Test
    fun settingDetailScreen_issueScreen_placeholderBehavior() {
        // This test assumes that when settingNav is Issue, nothing specific is rendered
        // from FaqScreen, AboutScreen, or AppearanceScreen, as per the TODO.
        // If Issue had its own specific content/tag, you'd check for that.
        composeRule.setContent {
            KmtTheme {
                SettingDetailScreen(
                    onBack = {},
                    settingNav = SettingNav.Issue, // The "Issue" case in the when statement
                    settingState = sampleSettingState,
                )
            }
        }

        // Verify that the other known content screens are NOT displayed
        composeRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertDoesNotExist()
        composeRule.onNodeWithTag(AboutScreenTestTags.SCREEN_ROOT).assertDoesNotExist()
        composeRule.onNodeWithTag(AppearanceScreenTestTags.SCREEN_ROOT).assertDoesNotExist()

        // You could add a tag to the Column in SettingDetailScreen to assert its presence,
        // or if "Issue" was meant to display some specific placeholder UI, test for that UI.
        // For now, we mainly ensure it doesn't crash and doesn't show other screens' content.
        composeRule.onNodeWithTag(SettingDetailScreenTestTags.SCREEN_ROOT).assertIsDisplayed()
        // The root should still be there
    }
}
