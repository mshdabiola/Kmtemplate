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
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.testtag.ReportBugScreenTestTags
import com.mshdabiola.setting.detailscreen.ReportBugScreen
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ReportBugScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun reportBugScreen_elementsDisplayed() {
        composeRule.setContent {
            KmtTheme {
                ReportBugScreen()
            }
        }

        composeRule.onNodeWithTag(ReportBugScreenTestTags.ROOT_COLUMN).assertIsDisplayed()
        composeRule.onNodeWithTag(ReportBugScreenTestTags.TITLE_TEXT_FIELD).assertIsDisplayed()
        composeRule.onNodeWithTag(ReportBugScreenTestTags.DESCRIPTION_TEXT_FIELD).assertIsDisplayed()
        composeRule.onNodeWithTag(ReportBugScreenTestTags.SUBMIT_EMAIL_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(ReportBugScreenTestTags.SUBMIT_GITHUB_BUTTON).assertIsDisplayed()
    }

    @Test
    fun reportBugScreen_initialButtonStates() {
        composeRule.setContent {
            KmtTheme {
                ReportBugScreen()
            }
        }

        composeRule.onNodeWithTag(ReportBugScreenTestTags.SUBMIT_EMAIL_BUTTON).assertIsNotEnabled()
        composeRule.onNodeWithTag(ReportBugScreenTestTags.SUBMIT_GITHUB_BUTTON).assertIsEnabled()
        // Initially enabled as per logic
    }

    @Test
    fun reportBugScreen_emailButtonEnabled_whenFieldsNotEmpty() {
        composeRule.setContent {
            KmtTheme {
                ReportBugScreen()
            }
        }

        composeRule.onNodeWithTag(ReportBugScreenTestTags.TITLE_TEXT_FIELD)
            .performTextInput("Bug Title")
        composeRule.onNodeWithTag(ReportBugScreenTestTags.DESCRIPTION_TEXT_FIELD)
            .performTextInput("Bug Description")

        composeRule.onNodeWithTag(ReportBugScreenTestTags.SUBMIT_EMAIL_BUTTON).assertIsEnabled()
        composeRule.onNodeWithTag(ReportBugScreenTestTags.SUBMIT_GITHUB_BUTTON).assertIsEnabled()
    }

    @Test
    fun reportBugScreen_submitEmailButton_callsOpenEmail() {
        var emailOpened = false
        var actualTo = ""
        var actualSubject = ""
        var actualBody = ""
        val appName = "Kmtemplate"
        val expectedTitleText = "Test Bug"
        val expectedContentText = "This is a test bug report."

        composeRule.setContent {
            KmtTheme {
                ReportBugScreen(
                    openEmail = { to, subject, body ->
                        emailOpened = true
                        actualTo = to
                        actualSubject = subject
                        actualBody = body
                    },
                )
            }
        }

        composeRule.onNodeWithTag(ReportBugScreenTestTags.TITLE_TEXT_FIELD)
            .performTextInput(expectedTitleText)
        composeRule.onNodeWithTag(ReportBugScreenTestTags.DESCRIPTION_TEXT_FIELD)
            .performTextInput(expectedContentText)
        composeRule.onNodeWithTag(ReportBugScreenTestTags.SUBMIT_EMAIL_BUTTON).performClick()

        assertTrue(emailOpened)
        assertEquals("mshdabiola@gmail.com", actualTo)
        assertEquals("Bug Report of $appName : $expectedTitleText", actualSubject)
        assertEquals(expectedContentText, actualBody)
    }

    @Test
    fun reportBugScreen_submitGitHubButton_callsOpenUrl() {
        var urlOpened = false
        var actualUrl = ""
        val expectedUrl = "https://github.com/mshdabiola/Kmtemplate/issues"

        composeRule.setContent {
            KmtTheme {
                ReportBugScreen(
                    openUrl = { url ->
                        urlOpened = true
                        actualUrl = url
                    },
                )
            }
        }
        // Fields can be empty or filled, GitHub button logic is inverse of Email button
        composeRule.onNodeWithTag(ReportBugScreenTestTags.SUBMIT_GITHUB_BUTTON).performClick()

        assertTrue(urlOpened)
        assertEquals(expectedUrl, actualUrl)
    }
}
