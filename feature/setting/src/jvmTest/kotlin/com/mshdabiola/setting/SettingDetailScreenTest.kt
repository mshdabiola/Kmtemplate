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

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.Platform
import com.mshdabiola.model.UserSettings
import com.mshdabiola.model.testtag.AboutScreenTestTags
import com.mshdabiola.model.testtag.AppearanceScreenTestTags
import com.mshdabiola.model.testtag.FaqScreenTestTags
import com.mshdabiola.model.testtag.LanguageScreenTestTags
import com.mshdabiola.model.testtag.ReportBugScreenTestTags
import com.mshdabiola.model.testtag.SettingDetailScreenTestTags
import com.mshdabiola.model.testtag.UpdateScreenTestTags
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertNotNull

class SettingDetailScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val sampleSettingState = SettingState(
        userSettings = UserSettings(),
        platform = Platform.Web,
    )

    private fun getExpectedTitle(settingNav: SettingNav): String {
        return when (settingNav) {
            SettingNav.Appearance -> "Appearance"
            SettingNav.Faq -> "FAQ"
            SettingNav.About -> "About"
            SettingNav.ReportBug -> "Report bug"
            SettingNav.Language -> "Language"
            SettingNav.Update -> "Check for update"
        }
    }

    @Test
    fun settingDetailScreen_topBar_displaysCorrectly() {
        composeRule.setContent {
            KmtTheme {
                SettingDetailScreen(
                    onBack = {},
                    settingNav = SettingNav.Appearance, // Any nav for this test
                    settingState = sampleSettingState,
                )
            }
        }
        composeRule.onNodeWithTag(SettingDetailScreenTestTags.SCREEN_ROOT).assertIsDisplayed()
        composeRule.onNodeWithTag(SettingDetailScreenTestTags.TOP_APP_BAR).assertIsDisplayed()
    }

    @Test
    fun settingDetailScreen_backButton_displaysAndWorks_whenOnBackIsNotNull() {
        var backClicked = false
        composeRule.setContent {
            KmtTheme {
                SettingDetailScreen(
                    onBack = { backClicked = true },
                    settingNav = SettingNav.Faq, // Any nav
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
    fun settingDetailScreen_backButton_doesNotExist_whenOnBackIsNull() {
        composeRule.setContent {
            KmtTheme {
                SettingDetailScreen(
                    onBack = null,
                    settingNav = SettingNav.About, // Any nav
                    settingState = sampleSettingState,
                )
            }
        }
        composeRule.onNodeWithTag(SettingDetailScreenTestTags.BACK_ICON_BUTTON).assertDoesNotExist()
    }

    @Test
    fun settingDetailScreen_showsFaqScreen_whenNavIsFaq() {
        val currentNav = SettingNav.Faq
        composeRule.setContent {
            KmtTheme {
                SettingDetailScreen(
                    onBack = {},
                    settingNav = currentNav,
                    settingState = sampleSettingState,
                )
            }
        }
        composeRule.onNodeWithText(getExpectedTitle(currentNav)).assertIsDisplayed()
        composeRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertIsDisplayed()
        composeRule.onNodeWithTag(AppearanceScreenTestTags.SCREEN_ROOT).assertDoesNotExist()
        composeRule.onNodeWithTag(AboutScreenTestTags.SCREEN_ROOT).assertDoesNotExist()
        composeRule.onNodeWithTag(LanguageScreenTestTags.LANGUAGE_LIST).assertDoesNotExist()
        composeRule.onNodeWithTag(ReportBugScreenTestTags.ROOT_COLUMN).assertDoesNotExist()
        composeRule.onNodeWithTag(UpdateScreenTestTags.ROOT_COLUMN).assertDoesNotExist()
    }

    @Test
    fun settingDetailScreen_showsAboutScreen_andHandlesCallbacks_whenNavIsAbout() {
        val currentNav = SettingNav.About
        var privacyPolicyUrlOpened: String? = null
        var termsUrlOpened: String? = null
        var contactEmailOpened: Triple<String, String, String>? = null

        composeRule.setContent {
            KmtTheme {
                SettingDetailScreen(
                    onBack = {},
                    settingNav = currentNav,
                    settingState = sampleSettingState,
                    openUrl = { url ->
                        if (url.contains("PRIVACY")) privacyPolicyUrlOpened = url
                        if (url.contains("TERMS")) termsUrlOpened = url
                    },
                    openEmail = { to, subject, body -> contactEmailOpened = Triple(to, subject, body) },
                )
            }
        }
        composeRule.onNodeWithText(getExpectedTitle(currentNav)).assertIsDisplayed()
        composeRule.onNodeWithTag(AboutScreenTestTags.SCREEN_ROOT).assertIsDisplayed()

        // Assuming AboutScreen has these buttons/links with the specified tags
        composeRule.onNodeWithTag(AboutScreenTestTags.PRIVACY_POLICY_BUTTON).performClick()
        assertNotNull(privacyPolicyUrlOpened)

        composeRule.onNodeWithTag(AboutScreenTestTags.TERMS_AND_CONDITIONS_BUTTON).performClick()
        assertNotNull(termsUrlOpened)

        composeRule.onNodeWithTag(AboutScreenTestTags.EMAIL_LINK).performClick()
        assertNotNull(contactEmailOpened)

        composeRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertDoesNotExist()
        composeRule.onNodeWithTag(AppearanceScreenTestTags.SCREEN_ROOT).assertDoesNotExist()
    }

    @Test
    fun settingDetailScreen_showsAppearanceScreen_andHandlesCallbacks_whenNavIsAppearance() {
        val currentNav = SettingNav.Appearance
        var contrastChangedValue: Int? = null
        var darkModeChangedValue: DarkThemeConfig? = null
        var gradientChangedValue: Boolean? = null

        composeRule.setContent {
            KmtTheme {
                SettingDetailScreen(
                    onBack = {},
                    settingNav = currentNav,
                    settingState = sampleSettingState,
                    onContrastChange = { contrastChangedValue = it },
                    onDarkModeChange = { darkModeChangedValue = it },
                    onGradientBackgroundChange = { gradientChangedValue = it },
                )
            }
        }
        composeRule.onNodeWithText(getExpectedTitle(currentNav)).assertIsDisplayed()
        composeRule.onNodeWithTag(AppearanceScreenTestTags.SCREEN_ROOT).assertIsDisplayed()

        val targetContrastOptionId = 1 // Example
        composeRule.onNodeWithTag(AppearanceScreenTestTags.ContrastTimelineTestTags.optionItem(targetContrastOptionId))
            .performClick()
        assertEquals(targetContrastOptionId, contrastChangedValue)

        val targetDarkModeConfig = DarkThemeConfig.DARK
        composeRule.onNodeWithTag(AppearanceScreenTestTags.darkModeOptionRow(targetDarkModeConfig.name))
            .performClick()
        assertEquals(targetDarkModeConfig, darkModeChangedValue)

        // // Assuming AppearanceScreen has a switch for gradient background
        composeRule.onNodeWithTag(AppearanceScreenTestTags.GRADIENT_BACKGROUND_ROW).performClick()
        assertEquals(true, gradientChangedValue) // Or based on initial state

        composeRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertDoesNotExist()
    }

    @Test
    fun settingDetailScreen_showsLanguageScreen_andHandlesCallback_whenNavIsLanguage() {
        val currentNav = SettingNav.Language
        var languageChangedTo: String? = null
        val targetLanguage = "en-US" // Example language code

        composeRule.setContent {
            KmtTheme {
                SettingDetailScreen(
                    onBack = {},
                    settingNav = currentNav,
                    settingState = sampleSettingState,
                    onLanguageChange = { languageChangedTo = it },
                )
            }
        }
        composeRule.onNodeWithText(getExpectedTitle(currentNav)).assertIsDisplayed()
        composeRule.onNodeWithTag(LanguageScreenTestTags.LANGUAGE_LIST).assertIsDisplayed()

        // Assuming LanguageScreen has options with tags like LanguageOption_en
        composeRule.onNodeWithTag(LanguageScreenTestTags.languageItem(targetLanguage))
            .performClick()
        assertEquals(targetLanguage, languageChangedTo)

        composeRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertDoesNotExist()
    }

    @Test
    fun settingDetailScreen_showsReportBugScreen_andHandlesCallbacks_whenNavIsReportBug() {
        val currentNav = SettingNav.ReportBug
        var emailOpened: Triple<String, String, String>? = null
        var urlOpened: String? = null

        composeRule.setContent {
            KmtTheme {
                SettingDetailScreen(
                    onBack = {},
                    settingNav = currentNav,
                    settingState = sampleSettingState,
                    openEmail = { to, subj, body -> emailOpened = Triple(to, subj, body) },
                    openUrl = { urlOpened = it },
                )
            }
        }
//        getExpectedTitle(currentNav),true
        composeRule.onNodeWithTag(SettingDetailScreenTestTags.TOP_APP_BAR).assertIsDisplayed()
        composeRule.onNodeWithTag(SettingDetailScreenTestTags.TOP_APP_BAR_TITLE)
            .assertTextEquals(getExpectedTitle(currentNav))
        // Check for ReportBugScreen root if it has one, or specific content.
        // For now, ensure it's the active context by checking others aren't shown.
        composeRule.onNodeWithTag(ReportBugScreenTestTags.ROOT_COLUMN).assertIsDisplayed()

        // Assuming ReportBugScreen has buttons/links for these actions
        composeRule.onNodeWithTag(ReportBugScreenTestTags.TITLE_TEXT_FIELD)
            .performTextInput("Title")
        composeRule.onNodeWithTag(ReportBugScreenTestTags.DESCRIPTION_TEXT_FIELD)
            .performTextInput("Title")
        composeRule.onNodeWithTag(ReportBugScreenTestTags.SUBMIT_EMAIL_BUTTON)
            .performClick()
        assertNotNull(emailOpened)
        composeRule.onNodeWithTag(ReportBugScreenTestTags.SUBMIT_GITHUB_BUTTON)
            .performClick()
        assertNotNull(urlOpened)

        composeRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertDoesNotExist()
    }

    @Test
    fun settingDetailScreen_showsUpdateScreen_andHandlesCallbacks_whenNavIsUpdate() {
        val currentNav = SettingNav.Update
        var updateDialogSet: Boolean? = null
        var preReleaseSet: Boolean? = null
        var checkForUpdateClicked = false

        composeRule.setContent {
            KmtTheme {
                SettingDetailScreen(
                    onBack = {},
                    settingNav = currentNav,
                    settingState = sampleSettingState,
                    onSetUpdateDialog = { updateDialogSet = it },
                    onSetUpdateFromPreRelease = { preReleaseSet = it },
                    onCheckForUpdate = { checkForUpdateClicked = true },
                )
            }
        }
        composeRule.onNodeWithTag(SettingDetailScreenTestTags.TOP_APP_BAR_TITLE)
            .assertTextEquals(getExpectedTitle(currentNav))
        composeRule.onNodeWithTag(UpdateScreenTestTags.ROOT_COLUMN).assertIsDisplayed()

        // Assuming UpdateScreen has UI elements for these actions
        composeRule.onNodeWithTag(UpdateScreenTestTags.SHOW_UPDATE_DIALOG_SWITCH).performClick()
        assertNotNull(updateDialogSet) // Check actual value based on initial state

        composeRule.onNodeWithTag(UpdateScreenTestTags.JOIN_BETA_RELEASE_SWITCH).performClick()
        assertNotNull(preReleaseSet) // Check actual value

        composeRule.onNodeWithTag(UpdateScreenTestTags.CHECK_FOR_UPDATE_BUTTON).performClick()
        assertTrue(checkForUpdateClicked)

        composeRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertDoesNotExist()
    }
}
