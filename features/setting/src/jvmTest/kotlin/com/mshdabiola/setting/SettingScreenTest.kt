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
    private val expectedAppearanceTitle = "Appearance"
    private val expectedFaqTitle = "FAQ"

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

        // Detail pane's specific content should not be initially displayed
        // (assuming Appearance is the default detail if one were forced, but it shouldn't be visible yet)
        composeRule.onNodeWithTag(AppearanceScreenTestTags.SCREEN_ROOT).assertDoesNotExist()
    }

    @Test
    fun settingScreen_clickListItem_navigatesToDetailPaneAndShowsCorrectContent() {
        var onContrastChangedCalledWith: Int? = null
        var onDarkModeChangedCalledWith: DarkThemeConfig? = null

        composeRule.setContent {
            KmtTheme {
                SettingScreen(
                    onDrawer = null,
                    settingState = initialSettingState,
                    onContrastChange = { onContrastChangedCalledWith = it },
                    onDarkModeChange = { onDarkModeChangedCalledWith = it },
                )
            }
        }

        // 1. Click on "Appearance" in the list
        val appearanceItemTag = "${SettingScreenListTestTags.LIST_ITEM_CARD_PREFIX}${SettingNav.Appearance.name}"
        composeRule.onNodeWithTag(appearanceItemTag).performClick()

        // Wait for navigation and animation if necessary.
        // composeRule.mainClock.advanceTimeUntilIdle() // Useful for ListDetailPaneScaffold navigation

        // 2. Verify Detail Pane (AppearanceScreen) is now displayed
        composeRule.onNodeWithTag(SettingDetailScreenTestTags.SCREEN_ROOT).assertIsDisplayed()
        composeRule.onNodeWithTag(AppearanceScreenTestTags.SCREEN_ROOT).assertIsDisplayed()
        composeRule.onNodeWithText(expectedAppearanceTitle).assertIsDisplayed() // Check title in detail screen

        // 3. Verify callbacks are passed and work (optional, but good for integration)
        val targetContrastOptionId = 1
        composeRule.onNodeWithTag("${ContrastTimelineTestTags.OPTION_ITEM_PREFIX}$targetContrastOptionId")
            .performClick()
        assertEquals(targetContrastOptionId, onContrastChangedCalledWith)

        // 4. List pane might still be in the composition depending on scaffold mode (e.g., dual-pane)
        // For single-pane mode, it might not be "displayed" in the sense of fully visible.
        // We'll focus on the detail pane being the primary content now.

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
        // composeRule.mainClock.advanceTimeUntilIdle()

        // Verify detail (FAQ) is shown
        composeRule.onNodeWithTag(SettingDetailScreenTestTags.SCREEN_ROOT).assertIsDisplayed()
        composeRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertIsDisplayed()
        composeRule.onNodeWithText(expectedFaqTitle).assertIsDisplayed()

        // Click the back button in the SettingDetailScreen's top bar
        composeRule.onNodeWithTag(SettingDetailScreenTestTags.BACK_ICON_BUTTON)
            .assertIsDisplayed() // Back button should be there as navigator.canNavigateBack() is true
            .performClick()
        // composeRule.mainClock.advanceTimeUntilIdle()

        // Verify we are back to the List Pane
        composeRule.onNodeWithTag(SettingScreenListTestTags.SCREEN_ROOT).assertIsDisplayed()
        composeRule.onNodeWithTag(SettingScreenListTestTags.SETTINGS_LAZY_COLUMN).assertIsDisplayed()

        // Verify Detail Pane (FAQ content) is no longer displayed
        composeRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertDoesNotExist()
        composeRule.onNodeWithTag(SettingDetailScreenTestTags.SCREEN_ROOT).assertDoesNotExist()
        // Detail screen itself might be gone
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

        // Menu icon is part of SettingListScreen
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
        composeRule.onNodeWithTag(SettingScreenListTestTags.MENU_ICON_BUTTON).assertDoesNotExist()
    }

    @Test
    fun settingScreen_clickReportIssue_doesNotNavigateToDetailAndAttemptsOpenUri() {
        // We can't easily verify openUri() call in a pure JVM test without mocking frameworks
        // or refactoring openUrl to be injectable and mockable.
        // We will verify that it does NOT navigate to a detail screen.

        var openUriAttempted = false
        // Simulate a way to detect openUri call for testing, if openUrl was injectable:
        // val mockOpenUrl: (String) -> () -> Unit = { url -> { openUriAttempted = true } }
        // Then pass this mocked version to SettingScreen if it accepted it.
        // Since it's not, we'll rely on the side effect of NOT navigating.

        composeRule.setContent {
            KmtTheme {
                // If openUrl could be mocked and injected:
                // SettingScreen(onDrawer = null, settingState = initialSettingState, openUrlProvider = mockOpenUrl)
                SettingScreen(onDrawer = null, settingState = initialSettingState)
            }
        }

        val issueItemTag = "${SettingScreenListTestTags.LIST_ITEM_CARD_PREFIX}${SettingNav.Issue.name}"
        composeRule.onNodeWithTag(issueItemTag).performClick()

        // composeRule.mainClock.advanceTimeUntilIdle()

        // Assert that the Detail Pane (e.g., Appearance or any other) is NOT displayed
        composeRule.onNodeWithTag(SettingDetailScreenTestTags.SCREEN_ROOT).assertDoesNotExist()
        composeRule.onNodeWithTag(AppearanceScreenTestTags.SCREEN_ROOT).assertDoesNotExist()
        composeRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertDoesNotExist()

        // List pane should still be the one primarily visible
        composeRule.onNodeWithTag(SettingScreenListTestTags.SCREEN_ROOT).assertIsDisplayed()

        // If openUri call could be tracked:
        // assertTrue(openUriAttempted)
        // For now, the primary verification is the lack of navigation to detail view for "Issue".
    }
}
