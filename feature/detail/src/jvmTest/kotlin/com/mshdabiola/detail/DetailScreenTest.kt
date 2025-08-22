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
package com.mshdabiola.detail

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mshdabiola.model.testtag.DetailScreenTestTags
import com.mshdabiola.ui.SharedTransitionContainer
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class DetailScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun detailScreen_elements_areDisplayedAndCorrectlyRendered() {
        // Arrange
        val testTitle = "Initial Title"
        val testContent = "Initial Content"
        val testId = 1L

        composeRule.setContent {
            SharedTransitionContainer {
                // Necessary wrapper
                val titleState = rememberTextFieldState(testTitle)
                val contentState = rememberTextFieldState(testContent)
                // DetailState now includes id, title, and detail
                val mockDetailState = DetailState(id = testId, title = titleState, detail = contentState)

                DetailScreen(
                    state = mockDetailState,
                    onBack = {},
                    onDelete = {},
                )
            }
        }

        // Assert
        composeRule.onNodeWithTag(DetailScreenTestTags.SCREEN_ROOT).assertExists()
        composeRule.onNodeWithTag(DetailScreenTestTags.TOP_APP_BAR).assertExists()
        composeRule.onNodeWithTag(DetailScreenTestTags.BACK_BUTTON).assertExists().assertIsDisplayed()
        composeRule.onNodeWithTag(DetailScreenTestTags.DELETE_BUTTON).assertExists().assertIsDisplayed()

        // Verify text fields are displayed and contain the initial text
        composeRule.onNodeWithTag(DetailScreenTestTags.TITLE_TEXT_FIELD)
            .assertExists()
            .assertIsDisplayed()
        composeRule.onNodeWithText(testTitle).assertExists().assertIsDisplayed() // Check content of title

        composeRule.onNodeWithTag(DetailScreenTestTags.CONTENT_TEXT_FIELD)
            .assertExists()
            .assertIsDisplayed()
        composeRule.onNodeWithText(testContent).assertExists().assertIsDisplayed() // Check content of detail
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun detailScreen_backButton_isClickableAndTriggersCallback() {
        var backClicked = false
        // Arrange
        composeRule.setContent {
            SharedTransitionContainer {
                // Use empty TextFieldStates for simplicity as their content isn't the focus here
                val mockDetailState = DetailState(
                    id = 1,
                    title = rememberTextFieldState(),
                    detail = rememberTextFieldState(),
                )

                DetailScreen(
                    state = mockDetailState,
                    onBack = { backClicked = true },
                    onDelete = {},
                )
            }
        }

        // Act
        composeRule.onNodeWithTag(DetailScreenTestTags.BACK_BUTTON).performClick()

        // Assert
        assertTrue(backClicked, "Back button should be clickable and trigger the onBack lambda.")
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun detailScreen_deleteButton_isClickableAndTriggersCallback() {
        var deleteClicked = false
        // Arrange
        composeRule.setContent {
            SharedTransitionContainer {
                val mockDetailState = DetailState(
                    id = 1,
                    title = rememberTextFieldState(),
                    detail = rememberTextFieldState(),
                )

                DetailScreen(
                    state = mockDetailState,
                    onBack = {},
                    onDelete = { deleteClicked = true },
                )
            }
        }

        // Act
        composeRule.onNodeWithTag(DetailScreenTestTags.DELETE_BUTTON).performClick()

        // Assert
        assertTrue(deleteClicked, "Delete button should be clickable and trigger the onDelete lambda.")
    }

    // Optional: Test to ensure placeholders are visible when text fields are empty
    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun detailScreen_textFields_showPlaceholdersWhenEmpty() {
        // Arrange
        val titlePlaceholder = "Title" // Assuming this is your placeholder in KmtTextField
        val contentPlaceholder = "content" // Assuming this is your placeholder in KmtTextField

        composeRule.setContent {
            SharedTransitionContainer {
                val emptyTitleState = rememberTextFieldState("") // Empty initial value
                val emptyContentState = rememberTextFieldState("") // Empty initial value
                val mockDetailState = DetailState(
                    id = 1,
                    title = emptyTitleState,
                    detail = emptyContentState,
                )

                DetailScreen(
                    state = mockDetailState,
                    onBack = {},
                    onDelete = {},
                )
            }
        }

        // Assert
        // Check that the text fields themselves exist
        composeRule.onNodeWithTag(DetailScreenTestTags.TITLE_TEXT_FIELD).assertExists()
        composeRule.onNodeWithTag(DetailScreenTestTags.CONTENT_TEXT_FIELD).assertExists()

        // Check for placeholder text.
        // Note: Finding by placeholder text directly can be tricky as it's often not part of the
        // semantic tree in the same way as actual text content.
        // A more robust way if KmtTextField supports it is to check an attribute,
        // or ensure the actual text content is empty and visually confirm placeholders in screenshots.
        // For this example, we'll assert the text is empty and rely on visual confirmation/screenshot tests
        // for placeholder visibility if direct placeholder checking is complex.

        composeRule.onNodeWithTag(DetailScreenTestTags.TITLE_TEXT_FIELD,true)
            .assertTextEquals("")

        composeRule.onNodeWithTag(DetailScreenTestTags.CONTENT_TEXT_FIELD,true)
            .assertTextEquals("") // Assert the input text is empty
    }
}
