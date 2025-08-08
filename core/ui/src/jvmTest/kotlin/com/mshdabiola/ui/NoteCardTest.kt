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
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mshdabiola.model.Note
import com.mshdabiola.model.testtag.NoteCardTestTags
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalSharedTransitionApi::class)
class NoteCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testNote = Note(id = 1L, title = "Test Title", content = "This is test content.")

    @Test
    fun noteCard_displaysTitleAndContent() {
        composeTestRule.setContent {
            SharedTransitionContainer {
                // Required by sharedBounds
                NoteCard(noteUiState = testNote, onClick = {})
            }
        }

        // Verify title is displayed with correct text using test tag
        composeTestRule.onNodeWithTag(NoteCardTestTags.TITLE, useUnmergedTree = true)
            .assertIsDisplayed()
            .assertTextEquals(testNote.title) // androidx.compose.ui.test.assertTextEquals

        // Verify content is displayed with correct text using test tag
        composeTestRule.onNodeWithTag(NoteCardTestTags.CONTENT, useUnmergedTree = true)
            .assertIsDisplayed()
            .assertTextEquals(testNote.content)

        // Alternative: Verify text directly if tags weren't available (less robust)
        composeTestRule.onNodeWithText(testNote.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(testNote.content).assertIsDisplayed()
    }

    @Test
    fun noteCard_onClickIsTriggered() {
        var clickedNoteId: Long? = null
        val expectedNoteId = testNote.id

        composeTestRule.setContent {
            SharedTransitionContainer {
                // Required by sharedBounds
                NoteCard(
                    noteUiState = testNote,
                    onClick = { id ->
                        clickedNoteId = id
                    },
                )
            }
        }

        // Perform click on the root element of the NoteCard
        composeTestRule.onNodeWithTag(NoteCardTestTags.ROOT)
            .performClick()

        // Verify that the onClick lambda was called with the correct ID
        assertEquals(expectedNoteId, clickedNoteId)
    }

    @Test
    fun noteCard_rootElementExists() {
        composeTestRule.setContent {
            SharedTransitionContainer {
                // Required by sharedBounds
                NoteCard(noteUiState = testNote, onClick = {})
            }
        }
        // Verify the root element (ListItem) is present using its test tag
        composeTestRule.onNodeWithTag(NoteCardTestTags.ROOT).assertExists()
    }
}
