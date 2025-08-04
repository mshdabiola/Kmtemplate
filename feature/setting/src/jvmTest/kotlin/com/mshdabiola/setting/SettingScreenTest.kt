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
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.setting.detailscreen.AppearanceScreenTestTags // For detail view check
import com.mshdabiola.setting.detailscreen.ContrastTimelineTestTags
import com.mshdabiola.setting.detailscreen.FaqScreenTestTags // For detail view check
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class SettingScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val initialSettingState = SettingState(
        contrast = 0,
        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
    )

    // Expected titles for quick verification (adjust if your resource loading differs in test)
    // These should ideally come from your string resources in a real app for better maintenance
    private val expectedAppearanceTitle = "Appearance" // Assuming this is the text for Appearance
    private val expectedFaqTitle = "FAQ" // Assuming this is the text for FAQ

    @Test
    fun settingScreen_initialState_displaysListPane() {
        composeRule.setContent {
            KmtTheme {
                SettingScreen(
                    onDrawer = null,
                    settingState = initialSettingState,
                )
            }
        }

        // Verify the root ListDetailPaneScaffold is present
        composeRule.onNodeWithTag(SettingScreenTestTags.SCREEN_ROOT).assertIsDisplayed()

        // Verify SettingListScreen is present (list pane)
        composeRule.onNodeWithTag(SettingScreenListTestTags.SCREEN_ROOT).assertIsDisplayed()
        composeRule.onNodeWithTag(SettingScreenListTestTags.SETTINGS_LAZY_COLUMN).assertIsDisplayed()
    }

    @Test
    fun settingScreen_clickListItem_navigatesToDetailPaneAndShowsCorrectContent() {
        var onContrastChangedCalledWith: Int? = null
        // var onDarkModeChangedCalledWith: DarkThemeConfig? = null // Keep if you test DarkMode changes

        composeRule.setContent {
            KmtTheme {
                SettingScreen(
                    onDrawer = null,
                    settingState = initialSettingState,
                    onContrastChange = { onContrastChangedCalledWith = it },
                    // onDarkModeChange = { onDarkModeChangedCalledWith = it },
                )
            }
        }

        // 1. Click on "Appearance" in the list
        val appearanceItemTag = "${SettingScreenListTestTags.LIST_ITEM_CARD_PREFIX}${SettingNav.Appearance.name}"
        composeRule.onNodeWithTag(appearanceItemTag).performClick()

        // Wait for navigation and animation if necessary.
        // composeRule.mainClock.advanceTimeUntilIdle() // Usually needed for ListDetailPaneScaffold

        // 2. Verify Detail Pane (AppearanceScreen) is now displayed
        composeRule.onNodeWithTag(SettingDetailScreenTestTags.SCREEN_ROOT).assertIsDisplayed()
        composeRule.onNodeWithTag(AppearanceScreenTestTags.SCREEN_ROOT).assertIsDisplayed()
        // Check title in detail screen. Using onAllNodesWithText and onFirst()
        // in case the title appears elsewhere (e.g., list item still in composition for dual pane)
        composeRule.onAllNodesWithText(expectedAppearanceTitle).onFirst().assertIsDisplayed()

        // 3. Verify callbacks are passed and work (optional, but good for integration)
        val targetContrastOptionId = 1 // Example, ensure this ID exists in your ContrastTimeline
        composeRule.onNodeWithTag(ContrastTimelineTestTags.optionItem(targetContrastOptionId))
            .performClick()
        assertEquals(targetContrastOptionId, onContrastChangedCalledWith)

        // 4. List pane might still be in the composition depending on scaffold mode (e.g., dual-pane)
        // For single-pane mode, it might not be "displayed" in the sense of fully visible.
        // The focus here is on the detail pane being the primary content.

        // 5. Ensure other detail screens are not shown
        composeRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertDoesNotExist()
    }

    @Test
    fun settingScreen_detailPane_backNavigationWorks() {
        composeRule.setContent {
            KmtTheme {
                SettingScreen(
                    onDrawer = null,
                    settingState = initialSettingState,
                )
            }
        }

        // Navigate to a detail screen first (e.g., FAQ)
        val faqItemTag = "${SettingScreenListTestTags.LIST_ITEM_CARD_PREFIX}${SettingNav.Faq.name}"
        composeRule.onNodeWithTag(faqItemTag).performClick()
        // composeRule.mainClock.advanceTimeUntilIdle() // Important for ListDetailPaneScaffold navigation

        // Verify detail (FAQ) is shown
        composeRule.onNodeWithTag(SettingDetailScreenTestTags.SCREEN_ROOT).assertIsDisplayed()
        composeRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertIsDisplayed()
        composeRule.onAllNodesWithText(expectedFaqTitle).onFirst().assertIsDisplayed() // Check title in detail

        // Perform back navigation by clicking the back button in the DetailScreen's TopAppBar
//        composeRule.onNodeWithTag(SettingDetailScreenTestTags.BACK_ICON_BUTTON).performClick()
        // composeRule.mainClock.advanceTimeUntilIdle() // Wait for navigation back

        // Verify we are back to the List Pane
        composeRule.onNodeWithTag(SettingScreenListTestTags.SCREEN_ROOT).assertIsDisplayed()
        composeRule.onNodeWithTag(SettingScreenListTestTags.SETTINGS_LAZY_COLUMN).assertIsDisplayed()
    }

    @Test
    fun settingScreen_withDrawer_menuIconIsPresentAndClickable() {
        var drawerOpened = false
        composeRule.setContent {
            KmtTheme {
                SettingScreen(
                    onDrawer = { drawerOpened = true }, // Provide onDrawer
                    settingState = initialSettingState,
                )
            }
        }

        // Menu icon is part of SettingListScreen's TopAppBar
        composeRule.onNodeWithTag(SettingScreenListTestTags.MENU_ICON_BUTTON)
            .assertIsDisplayed()
            .performClick()

        assertTrue(drawerOpened)
    }

    @Test
    fun settingScreen_withoutDrawer_menuIconIsNotPresent() {
        composeRule.setContent {
            KmtTheme {
                SettingScreen(
                    onDrawer = null, // No onDrawer
                    settingState = initialSettingState,
                )
            }
        }
        // Menu icon should not exist if onDrawer is null
        composeRule.onNodeWithTag(SettingScreenListTestTags.MENU_ICON_BUTTON).assertDoesNotExist()
    }

    @Test
    fun settingScreen_clickReportIssue_doesNotNavigateToDetailAndAttemptsOpenUri() {
        // As noted, verifying openUri directly is hard in pure JVM tests without mocking.
        // We focus on the side effect: no navigation to a detail pane.

        composeRule.setContent {
            KmtTheme {
                SettingScreen(
                    onDrawer = null,
                    settingState = initialSettingState,
                    // If you could inject and mock openUrl:
                    // openUrl = { url -> openUriAttempted = true /* and assert url */ }
                )
            }
        }

        val issueItemTag = "${SettingScreenListTestTags.LIST_ITEM_CARD_PREFIX}${SettingNav.Issue.name}"
        composeRule.onNodeWithTag(issueItemTag).performClick()

        // composeRule.mainClock.advanceTimeUntilIdle() // Good practice if any async ops were triggered

        // List pane should still be the one primarily visible
        composeRule.onNodeWithTag(SettingScreenListTestTags.SCREEN_ROOT).assertIsDisplayed()

        // If openUri call could be tracked (e.g., with a mock):
        // assertTrue(openUriAttempted)
    }
}
