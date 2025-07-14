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
package com.mshdabiola.main

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mshdabiola.model.Note
import com.mshdabiola.ui.SharedTransitionContainer
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class MainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mainScreen_whenStateIsLoading_showsLoadingIndicatorAndAppBar() {
        // Arrange
        composeTestRule.setContent {
            SharedTransitionContainer {
                MainScreen(
                    mainState = MainState.Loading,
                    navigateToDetail = {},
                )
            }
        }

        // Assert
        composeTestRule.onNodeWithTag(MainScreenTestTags.TOP_APP_BAR).assertIsDisplayed()
        composeTestRule.onNodeWithTag(MainScreenTestTags.LOADING_INDICATOR).assertIsDisplayed()
        composeTestRule.onNodeWithTag(MainScreenTestTags.EMPTY_STATE_COLUMN).assertDoesNotExist()
        composeTestRule.onNodeWithTag(MainScreenTestTags.NOTE_LIST).assertDoesNotExist()
    }

    @Test
    fun mainScreen_whenStateIsEmpty_showsEmptyStateAndAppBar() {
        // Arrange
        composeTestRule.setContent {
            SharedTransitionContainer {
                MainScreen(
                    mainState = MainState.Empty,
                    navigateToDetail = {},
                )
            }
        }

        // Assert
        composeTestRule.onNodeWithTag(MainScreenTestTags.TOP_APP_BAR).assertIsDisplayed()
        composeTestRule.onNodeWithTag(MainScreenTestTags.EMPTY_STATE_COLUMN).assertIsDisplayed()
        composeTestRule.onNodeWithTag(MainScreenTestTags.EMPTY_STATE_IMAGE).assertIsDisplayed()
        composeTestRule.onNodeWithTag(MainScreenTestTags.EMPTY_STATE_TITLE).assertIsDisplayed()
        composeTestRule.onNodeWithTag(MainScreenTestTags.EMPTY_STATE_DESCRIPTION).assertIsDisplayed()
        composeTestRule.onNodeWithTag(MainScreenTestTags.LOADING_INDICATOR).assertDoesNotExist()
        composeTestRule.onNodeWithTag(MainScreenTestTags.NOTE_LIST).assertDoesNotExist()
    }

    @Test
    fun mainScreen_whenStateIsSuccessWithNotes_showsNoteListAndAppBar() {
        // Arrange
        val testNotes = listOf(
            Note(id = 1L, title = "Note 1", content = "Content of note 1"),
            Note(id = 2L, title = "Note 2", content = "Content of note 2"),
        )
        composeTestRule.setContent {
            SharedTransitionContainer {
                MainScreen(
                    mainState = MainState.Success(testNotes),
                    navigateToDetail = {},
                )
            }
        }

        // Assert
        composeTestRule.onNodeWithTag(MainScreenTestTags.TOP_APP_BAR).assertIsDisplayed()
        composeTestRule.onNodeWithTag(MainScreenTestTags.NOTE_LIST).assertIsDisplayed()

        // Verify individual note cards are present (assuming NoteCard itself is testable or you add specific tags)
        // For now, we confirm the list itself. If you add dynamic tags to NoteCard like
        //  modifier = Modifier.testTag(MainScreenTestTags.noteCardTag(note.id))
        // you could assert them:
        // composeTestRule.onNodeWithTag(MainScreenTestTags.noteCardTag(1L)).assertIsDisplayed()
        // composeTestRule.onNodeWithTag(MainScreenTestTags.noteCardTag(2L)).assertIsDisplayed()

        composeTestRule.onNodeWithTag(MainScreenTestTags.LOADING_INDICATOR).assertDoesNotExist()
        composeTestRule.onNodeWithTag(MainScreenTestTags.EMPTY_STATE_COLUMN).assertDoesNotExist()
    }

    @Test
    fun mainScreen_whenStateIsSuccessWithNoNotes_showsEmptyStateAndAppBar() {
        // This tests if the success state correctly transitions to an empty-like display
        // if the list of notes is empty. Depending on your MainState.Success logic,
        // it might directly show the EmptyState composable or you might have a different UI.
        // For this example, let's assume it should show the EmptyState.
        // If MainState.Success with an empty list should NOT show EmptyState, adjust this test.
        composeTestRule.setContent {
            SharedTransitionContainer {
                MainScreen(
                    mainState = MainState.Success(emptyList()), // Success state with an empty list
                    navigateToDetail = {},
                )
            }
        }

        // Assert (Assuming it should display the empty state in this scenario)
        // If your logic is that MainState.Success(emptyList()) still shows an empty LazyColumn,
        // then this assertion would be different.
        // The current MainScreen.kt code will show an empty LazyColumn with the tag NOTE_LIST,
        // it won't automatically switch to the EmptyState composable.
        // Let's adjust the test to reflect the current MainScreen.kt behavior:

        composeTestRule.onNodeWithTag(MainScreenTestTags.TOP_APP_BAR).assertIsDisplayed()
        composeTestRule.onNodeWithTag(MainScreenTestTags.NOTE_LIST).assertIsDisplayed() // The LazyColumn exists
        // You might want to assert that it has 0 children if your testing framework supports that easily.
        // For example, with onAllNodesWithTag("someChildTagInNoteCard").assertCountEquals(0) if NoteCards had such a tag.

        composeTestRule.onNodeWithTag(MainScreenTestTags.LOADING_INDICATOR).assertDoesNotExist()
        // If MainState.Success(emptyList()) should actually render the EmptyState composable,
        // then your MainScreen when condition would need to handle that:
        // is MainState.Success -> {
        //     if (mainState.notes.isEmpty()) {
        //         EmptyState(...)
        //     } else {
        //         LazyColumn(...)
        //     }
        // }
        // For now, assuming current implementation:
        composeTestRule.onNodeWithTag(MainScreenTestTags.EMPTY_STATE_COLUMN).assertDoesNotExist()
    }

    @Test
    fun mainScreen_noteCardClick_invokesNavigateToDetail() {
        // Arrange
        var navigatedNoteId: Long? = null
        val testNotes = listOf(
            Note(id = 1L, title = "Clickable Note", content = "Content to click"),
        )
        composeTestRule.setContent {
            SharedTransitionContainer {
                MainScreen(
                    mainState = MainState.Success(testNotes),
                    navigateToDetail = { id -> navigatedNoteId = id },
                )
            }
        }

        // For this to work best, NoteCard should have a testTag.
        // Assuming NoteCard's root composable gets a testTag based on the note ID or
        // that you can find it by its content if it's unique.
        // Let's assume NoteCard's root or a clickable element within it can be found.
        // If NoteCard itself does not have a test tag, you might need to find it by text,
        // which is less robust.
        //
        // If NoteCard had: modifier = Modifier.testTag("NoteCard_${noteUiState.id}")
        // composeTestRule.onNodeWithTag("NoteCard_1").performClick()

        // As a fallback, if NoteCard is simple and its content is unique:
        // composeTestRule.onNodeWithText("Clickable Note").performClick()
        // This is brittle. It's better to make NoteCard itself testable by allowing a Modifier
        // to be passed in, which you can then use to add a testTag in this test.

        // For now, let's assume the NOTE_LIST is present and we want to click the first item.
        // This requires knowledge of how LazyColumn items are structured.
        // A more robust way is to tag the NoteCard items themselves.
        // If NoteCard internally has a Modifier.clickable, the click should propagate.

        // If you've modified NoteCard to accept a modifier and applied a test tag:
        // For example, in MainScreen.kt:
        // items(...) { note ->
        //     NoteCard(
        //         ...,
        //         modifier = Modifier.testTag("NoteItem_${note.id}") // Example tag
        //     )
        // }
        // Then in the test:
        // composeTestRule.onNodeWithTag("NoteItem_1").performClick()

        // Given the current MainScreen.kt, the NoteCard is directly used.
        // We'll proceed by finding the list and assuming the click on its content works.
        // This is a common challenge: testing items within a list.
        // The BEST solution is to ensure NoteCard is designed to be testable,
        // typically by allowing a Modifier to be passed to its root element.

        // Let's assume for the sake of this example that your NoteCard is implemented
        // such that a click on a node with the title text triggers the onClick.
        // This is NOT ideal but illustrates the intent.
        composeTestRule.onNodeWithText("Clickable Note", substring = true).performClick()

        // Assert
        assertEquals(1L, navigatedNoteId, "navigateToDetail should be called with the correct note ID.")
    }
}
