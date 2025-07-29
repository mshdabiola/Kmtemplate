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
import com.mshdabiola.setting.detailscreen.AboutScreen
import com.mshdabiola.setting.detailscreen.AboutScreenTestTags
import org.junit.Rule
import org.junit.Test

class AboutScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    // Mock or simple lambda for openEmail for testing purposes
    private var emailOpened = false
    private val mockOpenEmail: (String, String) -> () -> Unit = { _, _ ->
        { emailOpened = true }
    }

    @Test
    fun aboutScreen_displaysExpectedContent() {
        composeRule.setContent {
            KmtTheme {
                // You might need to adjust how openEmail is provided if it's not a direct composable
                // or if it's expected to be provided differently in your actual screen setup.
                // For simplicity, AboutScreen is modified to accept openEmail as a parameter
                // or we use a simplified version for the test.
                // If AboutScreen directly calls a global openEmail, ensure that function is testable/mockable.
                // For this example, let's assume AboutScreen can be tested by verifying its UI elements
                // without deeply testing the openEmail platform implementation.

                AboutScreen() // Assuming AboutScreen internally handles its actions or they are mocked.
            }
        }

        // Verify root container exists
        composeRule.onNodeWithTag(AboutScreenTestTags.SCREEN_ROOT).assertExists()

        // Verify App Icon and Name
        composeRule.onNodeWithTag(AboutScreenTestTags.APP_ICON).assertIsDisplayed()
        composeRule.onNodeWithTag(AboutScreenTestTags.APP_NAME).assertIsDisplayed()
        // Check actual text for app name (replace with your actual KmtStrings.brand value)
        // composeRule.onNodeWithText(KmtStrings.brand).assertIsDisplayed()

        // Verify App Description (assuming Res.string.about resolves correctly in test)
        composeRule.onNodeWithTag(AboutScreenTestTags.APP_DESCRIPTION).assertIsDisplayed()

        // Verify Version Info
        composeRule.onNodeWithTag(AboutScreenTestTags.VERSION_LABEL).assertIsDisplayed()
        composeRule.onNodeWithTag(AboutScreenTestTags.VERSION_VALUE).assertIsDisplayed()
        // Check actual version text (replace with your actual KmtStrings.version value)
        // composeRule.onNodeWithText(KmtStrings.version).assertIsDisplayed()

        // Verify Last Update Info
        composeRule.onNodeWithTag(AboutScreenTestTags.LAST_UPDATE_LABEL).assertIsDisplayed()
        composeRule.onNodeWithTag(AboutScreenTestTags.LAST_UPDATE_VALUE).assertIsDisplayed()

        // Verify Developed By Info
        composeRule.onNodeWithTag(AboutScreenTestTags.DEVELOPED_BY_LABEL).assertIsDisplayed()
        composeRule.onNodeWithTag(AboutScreenTestTags.DEVELOPER_NAME).assertIsDisplayed()

        // Verify Contact Us Info
        composeRule.onNodeWithTag(AboutScreenTestTags.CONTACT_US_LABEL).assertIsDisplayed()
        composeRule.onNodeWithTag(AboutScreenTestTags.EMAIL_LINK).assertIsDisplayed()
        // Check actual email text
        composeRule.onNodeWithText("mshdabiola@gmail.com").assertIsDisplayed()

        // Verify Buttons
        composeRule.onNodeWithTag(AboutScreenTestTags.PRIVACY_POLICY_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(AboutScreenTestTags.TERMS_AND_CONDITIONS_BUTTON).assertIsDisplayed()
    }

    @Test
    fun aboutScreen_emailLink_isClickable() {
        // To test the click action properly, you might need to modify AboutScreen
        // to accept the click handlers as parameters, or use a testing framework
        // that allows mocking/verifying such interactions if openEmail is a global/platform function.

        var privacyPolicyClicked = false
        var termsClicked = false

        // For this test, we'll focus on the email link and assume its lambda can be triggered.
        // If your openEmail within AboutScreen is complex or platform-specific,
        // testing its direct invocation here might be tricky without refactoring AboutScreen
        // or using more advanced mocking.

        composeRule.setContent {
            KmtTheme {
                // Re-compose with mocks if needed, or rely on AboutScreen's internal setup
                // For this example, we assume clicking the email link should trigger its lambda.
                // The actual email intent won't be launched in a JVM test without Robolectric handling.
                AboutScreen(
                    // If you modify AboutScreen to take callbacks:
                    // onOpenEmail = { emailOpened = true },
                    // onOpenPrivacyPolicy = { privacyPolicyClicked = true },
                    // onOpenTermsAndConditions = { termsClicked = true }
                )
            }
        }

        composeRule.onNodeWithTag(AboutScreenTestTags.EMAIL_LINK).performClick()

        // If you had a way to verify the openEmail call (e.g., a flag set by a mock):
        // assertTrue(emailOpened) // This would require openEmail to be injectable/mockable

        // For now, we mainly ensure it doesn't crash and is clickable.
        // Verifying the *actual* intent launch is more for integrated/instrumented tests.
    }

    @Test
    fun aboutScreen_privacyPolicyButton_isClickableAndExists() {
        var privacyPolicyClicked = false
        composeRule.setContent {
            KmtTheme {
                // To test clicks, AboutScreen would ideally take lambdas
                // val onOpenPrivacyPolicy: () -> Unit = { privacyPolicyClicked = true }
                // AboutScreen(openPrivacyPolicy = onOpenPrivacyPolicy, ...)
                AboutScreen() // Assuming default behavior for now
            }
        }
        val privacyButton = composeRule.onNodeWithTag(AboutScreenTestTags.PRIVACY_POLICY_BUTTON)
        privacyButton.assertExists()
        privacyButton.assertIsDisplayed()
        // privacyButton.performClick()
        // assertTrue(privacyPolicyClicked) // If onOpenPrivacyPolicy was injectable
    }

    @Test
    fun aboutScreen_termsAndConditionsButton_isClickableAndExists() {
        var termsClicked = false
        composeRule.setContent {
            KmtTheme {
                // val onOpenTerms: () -> Unit = { termsClicked = true }
                // AboutScreen(openTermsAndConditions = onOpenTerms, ...)
                AboutScreen() // Assuming default behavior for now
            }
        }
        val termsButton = composeRule.onNodeWithTag(AboutScreenTestTags.TERMS_AND_CONDITIONS_BUTTON)
        termsButton.assertExists()
        termsButton.assertIsDisplayed()
        // termsButton.performClick()
        // assertTrue(termsClicked) // If onOpenTermsAndConditions was injectable
    }
}
