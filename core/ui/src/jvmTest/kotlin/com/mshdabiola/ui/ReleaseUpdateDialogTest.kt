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
package com.mshdabiola.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.mshdabiola.model.ReleaseInfo
import com.mshdabiola.model.testtag.ReleaseUpdateTags
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalSharedTransitionApi::class)
class ReleaseUpdateDialogTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testReleaseInfo = ReleaseInfo.Success(
        tagName = "v1.0.0",
        releaseName = "Test Release",
        body = "This is a test release body.",
        asset = "test.zip",
    )

    @Test
    fun dialog_isDisplayed_and_showsCorrectInformation() {
        composeTestRule.setContent {
            ReleaseUpdateDialog(
                releaseInfo = testReleaseInfo,
                onDismissRequest = {},
                onDownloadClick = {},
            )
        }

        composeTestRule.onNodeWithTag(ReleaseUpdateTags.RELEASE_UPDATE_DIALOG_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(ReleaseUpdateTags.RELEASE_UPDATE_DIALOG_TITLE_TAG)
            .assertIsDisplayed()
            .assertTextEquals("New Update Available")
        composeTestRule.onNodeWithTag(ReleaseUpdateTags.RELEASE_UPDATE_DIALOG_BODY_TAG)
            .assertIsDisplayed()
            .assertTextEquals(testReleaseInfo.body)
        composeTestRule.onNodeWithTag(ReleaseUpdateTags.RELEASE_UPDATE_DIALOG_CONFIRM_BUTTON_TAG)
            .assertIsDisplayed()
        // .assertTextEquals("Download") // KmtButton does not directly expose text for assertion
        composeTestRule.onNodeWithTag(ReleaseUpdateTags.RELEASE_UPDATE_DIALOG_DISMISS_BUTTON_TAG)
            .assertIsDisplayed()
        // .assertTextEquals("Cancel") // TextButton's child Text is not directly accessible by tag here
    }

    @Test
    fun whenDownloadClicked_callsOnDownloadClickLambda() {
        var downloadClicked = false
        composeTestRule.setContent {
            ReleaseUpdateDialog(
                releaseInfo = testReleaseInfo,
                onDismissRequest = {},
                onDownloadClick = { downloadClicked = true },
            )
        }

        composeTestRule.onNodeWithTag(ReleaseUpdateTags.RELEASE_UPDATE_DIALOG_CONFIRM_BUTTON_TAG)
            .performClick()
        assert(downloadClicked)
    }

    @Test
    fun whenDismissClicked_callsOnDismissRequestLambda() {
        var dismissClicked = false
        composeTestRule.setContent {
            ReleaseUpdateDialog(
                releaseInfo = testReleaseInfo,
                onDismissRequest = { dismissClicked = true },
                onDownloadClick = {},
            )
        }

        composeTestRule.onNodeWithTag(ReleaseUpdateTags.RELEASE_UPDATE_DIALOG_DISMISS_BUTTON_TAG)
            .performClick()
        assert(dismissClicked)
    }
}
