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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class SettingListScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    // Sample data matching the structure SettingListScreen expects
    private val sampleSettingsMap = SettingNav.entries.groupBy { it.segment }

    // Expected titles from resources - adjust these if your string resources produce different text
    // In a full KMP test setup, you'd aim for stringResource() to work directly.
    private val expectedScreenTitle = "Settings" // From Res.string.screen_name
    private val expectedSegmentTitles = listOf("General", "Support & Feedback") // From Res.array.segment
    private val expectedGeneralItemTitles = listOf("Appearance") // From Res.array.general
    private val expectedSupportItemTitles = listOf("Report an Issue", "FAQ", "About") // From Res.array.support

    @Test
    fun settingListScreen_topBar_displaysCorrectly_withDrawer() {
        var drawerClicked = false
        composeRule.setContent {
            KmtTheme {
                SettingListScreen(
                    settingsMap = sampleSettingsMap,
                    onDrawer = { drawerClicked = true }, // Provide onDrawer
                    onSettingClick = {},
                )
            }
        }

        composeRule.onNodeWithTag(SettingScreenListTestTags.SCREEN_ROOT).assertIsDisplayed()
        composeRule.onNodeWithTag(SettingScreenListTestTags.TOP_APP_BAR).assertIsDisplayed()

        // Verify title (This assumes stringResource(Res.string.screen_name) resolves to "Settings")
        composeRule.onNodeWithText(expectedScreenTitle).assertIsDisplayed()

        // Verify menu icon is present and clickable
        composeRule.onNodeWithTag(SettingScreenListTestTags.MENU_ICON_BUTTON)
            .assertIsDisplayed()
            .performClick()
        assertTrue(drawerClicked)
    }

    @Test
    fun settingListScreen_topBar_displaysCorrectly_withoutDrawer() {
        composeRule.setContent {
            KmtTheme {
                SettingListScreen(
                    settingsMap = sampleSettingsMap,
                    onDrawer = null, // Key: onDrawer is null
                    onSettingClick = {},
                )
            }
        }

        composeRule.onNodeWithTag(SettingScreenListTestTags.TOP_APP_BAR).assertIsDisplayed()
        composeRule.onNodeWithText(expectedScreenTitle).assertIsDisplayed()

        // Verify menu icon is NOT present
        composeRule.onNodeWithTag(SettingScreenListTestTags.MENU_ICON_BUTTON).assertDoesNotExist()
    }

    @Test
    fun settingListScreen_displaysSectionHeadersAndItems() {
        composeRule.setContent {
            KmtTheme {
                SettingListScreen(
                    settingsMap = sampleSettingsMap,
                    onDrawer = null,
                    onSettingClick = {},
                )
            }
        }

        composeRule.onNodeWithTag(SettingScreenListTestTags.SETTINGS_LAZY_COLUMN).assertIsDisplayed()

        // Verify Section 0: General
        composeRule.onNodeWithTag("${SettingScreenListTestTags.SECTION_HEADER_TEXT_PREFIX}0")
            .assertIsDisplayed()
        composeRule.onNodeWithText(expectedSegmentTitles[0]).assertIsDisplayed() // "General"

        // Verify item in General section (Appearance)
        val appearanceNav = SettingNav.Appearance
        composeRule.onNodeWithTag("${SettingScreenListTestTags.LIST_ITEM_CARD_PREFIX}${appearanceNav.name}")
            .assertIsDisplayed()
        composeRule.onNodeWithTag("${SettingScreenListTestTags.LIST_ITEM_ICON_PREFIX}${appearanceNav.name}")
            .assertIsDisplayed()
        composeRule.onNodeWithTag("${SettingScreenListTestTags.LIST_ITEM_TITLE_TEXT_PREFIX}${appearanceNav.name}")
            .assertIsDisplayed()
        composeRule.onNodeWithText(expectedGeneralItemTitles[0]).assertIsDisplayed() // "Appearance"

        // Verify Section 1: Support
        composeRule.onNodeWithTag("${SettingScreenListTestTags.SECTION_HEADER_TEXT_PREFIX}1")
            .assertIsDisplayed()
        composeRule.onNodeWithText(expectedSegmentTitles[1]).assertIsDisplayed() // "Support & Feedback"

        // Verify items in Support section
        val issueNav = SettingNav.Issue
        composeRule.onNodeWithTag("${SettingScreenListTestTags.LIST_ITEM_CARD_PREFIX}${issueNav.name}")
            .assertIsDisplayed()
        composeRule.onNodeWithText(expectedSupportItemTitles[0]).assertIsDisplayed() // "Report an Issue"

        val faqNav = SettingNav.Faq
        composeRule.onNodeWithTag("${SettingScreenListTestTags.LIST_ITEM_CARD_PREFIX}${faqNav.name}")
            .assertIsDisplayed()
        composeRule.onNodeWithText(expectedSupportItemTitles[1]).assertIsDisplayed() // "FAQ"

        val aboutNav = SettingNav.About
        composeRule.onNodeWithTag("${SettingScreenListTestTags.LIST_ITEM_CARD_PREFIX}${aboutNav.name}")
            .assertIsDisplayed()
        composeRule.onNodeWithText(expectedSupportItemTitles[2]).assertIsDisplayed() // "About"
    }

    @Test
    fun settingListScreen_itemClick_invokesCallback() {
        var clickedSetting: SettingNav? = null
        val targetSetting = SettingNav.Appearance // Choose an item to click

        composeRule.setContent {
            KmtTheme {
                SettingListScreen(
                    settingsMap = sampleSettingsMap,
                    onDrawer = null,
                    onSettingClick = { navItem ->
                        clickedSetting = navItem
                    },
                )
            }
        }

        // Click on the "Appearance" item
        composeRule.onNodeWithTag("${SettingScreenListTestTags.LIST_ITEM_CARD_PREFIX}${targetSetting.name}")
            .performClick()

        // Verify the callback was invoked with the correct SettingNav item
        assertEquals(targetSetting, clickedSetting)
    }
}
