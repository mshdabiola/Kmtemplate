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
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.mshdabiola.ui.SharedTransitionContainer
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class DetailScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun detailScreen_elements_areDisplayed() {
        // Arrange
        composeRule.setContent {
            SharedTransitionContainer {
                // Assuming DetailState.Success takes an id, and title/detail are part of the state
                // and are TextFieldState. You might need to adjust DetailState and how it's initialized.
                // For simplicity, I'm using rememberTextFieldState directly here,
                // but ideally, this would come from a ViewModel or a more complex state holder.
                val titleState = rememberTextFieldState("Initial Title")
                val contentState = rememberTextFieldState("Initial Content")
                val mockDetailState = DetailState(title = titleState, detail = contentState)

                DetailScreen(
                    id = 1L, // Example ID
                    state = mockDetailState,
                    // sharedTransitionScope and animatedContentScope are now implicitly handled
                    // by DetailScreen if you're using LocalSharedTransitionScope.current etc.
                    // If you need to pass them explicitly, ensure your DetailScreen composable accepts them.
                    onBack = {},
                    onDelete = {},
                )
            }
        }

        // Assert
        composeRule.onNodeWithTag(DetailScreenTestTags.TOP_APP_BAR).assertExists()
        composeRule.onNodeWithTag(DetailScreenTestTags.BACK_BUTTON).assertExists()
        composeRule.onNodeWithTag(DetailScreenTestTags.DELETE_BUTTON).assertExists()
        composeRule.onNodeWithTag(DetailScreenTestTags.TITLE_TEXT_FIELD).assertExists()
        composeRule.onNodeWithTag(DetailScreenTestTags.CONTENT_TEXT_FIELD).assertExists()
        composeRule.onNodeWithTag(DetailScreenTestTags.SCREEN_ROOT).assertExists()
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun detailScreen_backButton_isClickable() {
        var backClicked = false
        // Arrange
        composeRule.setContent {
            SharedTransitionContainer {
                val titleState = rememberTextFieldState()
                val contentState = rememberTextFieldState()
                val mockDetailState = DetailState(title = titleState, detail = contentState)

                DetailScreen(
                    id = 1L,
                    state = mockDetailState,
                    onBack = { backClicked = true },
                    onDelete = {},
                )
            }
        }

        // Act
        composeRule.onNodeWithTag(DetailScreenTestTags.BACK_BUTTON).performClick()

        // Assert
        assertTrue(backClicked, "Back button should be clickable and trigger onBack lambda")
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun detailScreen_deleteButton_isClickable() {
        var deleteClicked = false
        // Arrange
        composeRule.setContent {
            SharedTransitionContainer {
                val titleState = rememberTextFieldState()
                val contentState = rememberTextFieldState()
                val mockDetailState = DetailState(title = titleState, detail = contentState)

                DetailScreen(
                    id = 1L,
                    state = mockDetailState,
                    onBack = {},
                    onDelete = { deleteClicked = true },
                )
            }
        }

        // Act
        composeRule.onNodeWithTag(DetailScreenTestTags.DELETE_BUTTON).performClick()

        // Assert
        assertTrue(deleteClicked, "Delete button should be clickable and trigger onDelete lambda")
    }
}
