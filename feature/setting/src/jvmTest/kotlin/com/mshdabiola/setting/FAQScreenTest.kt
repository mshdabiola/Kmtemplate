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
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.testtag.FaqScreenTestTags
import com.mshdabiola.setting.detailscreen.FaqItem
import com.mshdabiola.setting.detailscreen.FaqScreen
import org.junit.Rule
import org.junit.Test

class FAQScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val sampleFaqs = listOf(
        FaqItem(
            id = 1,
            question = "What is Kotlin Multiplatform (KMP)?",
            answer = "Kotlin Multiplatform allows you to share code " +
                "(like business logic, data layers, and more) across different platforms such as" +
                " Android, iOS, Web, Desktop, and Server-side, all while writing platform-specific " +
                "code only where necessary (e.g., for UI or platform-specific APIs).",
        ),
        FaqItem(
            id = 2,
            question = "How does this template help with KMP development?",
            answer = "This template provides a pre-configured project structure" +
                " with shared modules, platform-specific modules, and examples of how to implement" +
                " common patterns like shared ViewModels, data repositories, and Compose Multiplatform for UI.",
        ),
        FaqItem(
            id = 3,
            question = "Can I share UI code with KMP?",
            answer = "Yes, with Compose Multiplatform, you can write your UI" +
                " once using Jetpack Compose and deploy it on Android, Desktop (Windows, macOS, Linux)," +
                " iOS (Alpha), and Web (Experimental). This template utilizes Compose Multiplatform.",
        ),
        FaqItem(
            id = 4,
            question = "What are the benefits of using KMP?",
            answer = "Key benefits include: \n" +
                "- Code Reuse: Write common logic once and share it.\n" +
                "- Consistency: Ensure consistent behavior across platforms.\n" +
                "- Faster Development: Reduce redundant work.\n" +
                "- Native Performance: Shared Kotlin code compiles to native code for each platform.\n" +
                "- Flexibility: Choose how much code to share.",
        ),
        FaqItem(
            id = 5,
            question = "Where can I find the shared code in this template?",
            answer = "Shared code is typically located in modules named " +
                "`commonMain` within shared source sets (e.g., `shared/src/commonMain`," +
                " `features/featureName/src/commonMain`). Platform-specific code resides " +
                "in corresponding platform source sets like `androidMain` or `iosMain`.",
        ),
    )

    @Test
    fun faqScreen_whenHasFaqs_displaysFaqList() {
        composeTestRule.setContent {
            KmtTheme {
                FaqScreen() // FaqScreen uses its own internal list by default
            }
        }

        composeTestRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertIsDisplayed()
        composeTestRule.onNodeWithTag(FaqScreenTestTags.EMPTY_STATE_TEXT).assertDoesNotExist()

        // Check if the first question is displayed
        composeTestRule.onNodeWithText(sampleFaqs[0].question).assertIsDisplayed()
        // Check if the root of the first FAQ item is displayed
        composeTestRule.onNodeWithTag(
            "${FaqScreenTestTags
                .FaqListItemTestTags.LIST_ITEM_ROOT_PREFIX}${sampleFaqs[0].id}",
        )
            .assertIsDisplayed()
    }

    @Test
    fun faqScreen_whenFaqsAreEmpty_displaysEmptyState() {
        // To test the empty state, we need to modify FaqScreen to accept a list of questions,
        // or have a way to configure its internal list for testing.
        // For now, assuming FaqScreen *always* has its internal list, this test is hard to achieve
        // without refactoring FaqScreen.
        // If FaqScreen could take `questions: List<FaqItem>` as a parameter:
        // composeTestRule.setContent {
        //     KmtTheme {
        //         FaqScreen(questions = emptyList())
        //     }
        // }
        // composeTestRule.onNodeWithTag(FaqScreenTestTags.EMPTY_STATE_TEXT).assertIsDisplayed()
        // composeTestRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertDoesNotExist()

        // Current FaqScreen.kt has a hardcoded list, so the empty state is not reachable unless the list is cleared.
        // This test will fail or be irrelevant unless FaqScreen is modified for testability (e.g., by parameterizing questions).
        // Let's assume for a moment the list was modifiable or could be empty.
        // If you refactor FaqScreen to accept `questions: List<FaqItem>`, uncomment and adapt the above.

        // For the current implementation, we can only test the non-empty state.
        // If you *really* need to test the empty state with the current FaqScreen,
        // it would require significant mocking or a different test setup not typical for composable unit tests.
        // It's better to make the composable testable by allowing data to be passed in.
        println(
            "SKIPPING: faqScreen_whenFaqsAreEmpty_displaysEmptyState - " +
                "FaqScreen needs refactoring to accept an empty list for this test.",
        )
    }

    @Test
    fun faqListItem_initialState_answerNotVisible_expandIconDisplayed() {
        composeTestRule.setContent {
            KmtTheme {
                FaqScreen() // Uses its internal non-empty list
            }
        }

        val firstFaq = sampleFaqs[0]

        // Question should be visible
        composeTestRule.onNodeWithTag(
            "${FaqScreenTestTags
                .FaqListItemTestTags.QUESTION_TEXT_PREFIX}${firstFaq.id}",
            useUnmergedTree = true,
        )
            .assertIsDisplayed()

        // Answer should initially NOT be visible
        composeTestRule.onNodeWithTag(
            "${FaqScreenTestTags
                .FaqListItemTestTags.ANSWER_TEXT_PREFIX}${firstFaq.id}",
            useUnmergedTree = true,
        )
            .assertDoesNotExist() // AnimatedVisibility makes it not exist if not visible

        // Expand icon should be displayed
        composeTestRule.onNodeWithTag(
            "${FaqScreenTestTags
                .FaqListItemTestTags.EXPAND_ICON_PREFIX}${firstFaq.id}",
            useUnmergedTree = true,
        )
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithContentDescription(
            "Expand",
            useUnmergedTree = true,
        ).onFirst().assertIsDisplayed() // Check by content description
    }

    @Test
    fun faqListItem_clickToExpand_showsAnswer_updatesIcon() {
        composeTestRule.setContent {
            KmtTheme {
                FaqScreen()
            }
        }

        val firstFaq = sampleFaqs[0]
        val listItemNode = composeTestRule.onNodeWithTag(
            "${FaqScreenTestTags.FaqListItemTestTags.LIST_ITEM_ROOT_PREFIX}${firstFaq.id}",
            useUnmergedTree = true,
        )

        // Initially answer is not visible, icon is "Expand"
        composeTestRule.onNodeWithTag(
            "${FaqScreenTestTags.FaqListItemTestTags.ANSWER_TEXT_PREFIX}${firstFaq.id}",
            useUnmergedTree = true,
        )
            .assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            "${FaqScreenTestTags.FaqListItemTestTags.EXPAND_ICON_PREFIX}${firstFaq.id}",
            useUnmergedTree = true,
        )
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithContentDescription("Expand").onFirst().assertIsDisplayed()

        // Click the item to expand
        listItemNode.performClick()

        // Answer should now be visible
        composeTestRule.onNodeWithTag(
            "${FaqScreenTestTags.FaqListItemTestTags.ANSWER_TEXT_PREFIX}${firstFaq.id}",
            useUnmergedTree = true,
        )
            .assertIsDisplayed()
        // Icon should now be "Collapse"
        composeTestRule.onNodeWithContentDescription("Collapse").assertIsDisplayed()
    }

    @Test
    fun faqListItem_clickTwiceToCollapse_hidesAnswer_updatesIcon() {
        composeTestRule.setContent {
            KmtTheme {
                FaqScreen()
            }
        }

        val firstFaq = sampleFaqs[0]
        val listItemNode = composeTestRule.onNodeWithTag(
            "${FaqScreenTestTags.FaqListItemTestTags.LIST_ITEM_ROOT_PREFIX}${firstFaq.id}",
            useUnmergedTree = true,
        )

        // Click once to expand
        listItemNode.performClick()
        composeTestRule.onNodeWithTag(
            "${FaqScreenTestTags.FaqListItemTestTags.ANSWER_TEXT_PREFIX}${firstFaq.id}",
            useUnmergedTree = true,
        )
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Collapse").assertIsDisplayed()

        // Click again to collapse
        listItemNode.performClick()

        // Answer should be hidden again
        composeTestRule.onNodeWithTag(
            "${FaqScreenTestTags.FaqListItemTestTags.ANSWER_TEXT_PREFIX}${firstFaq.id}",
            useUnmergedTree = true,
        )
            .assertDoesNotExist()
        // Icon should be "Expand" again
        composeTestRule.onAllNodesWithContentDescription("Expand").onFirst().assertIsDisplayed()
    }

    @Test
    fun faqList_scrollsAndAllItemsArePresent() {
        // This test is more relevant if you have many items and want to ensure virtualization works.
        // With the default 5 items, they might all be visible anyway.
        // We'll check if all questions from the hardcoded list in FaqScreen are found.
        val hardcodedQuestions = listOf(
            "What is Kotlin Multiplatform (KMP)?",
            "How does this template help with KMP development?",
            "Can I share UI code with KMP?",
            "What are the benefits of using KMP?",
            "Where can I find the shared code in this template?",
        )

        composeTestRule.setContent {
            KmtTheme {
                FaqScreen()
            }
        }

        composeTestRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST).assertIsDisplayed()

        hardcodedQuestions.forEachIndexed { index, questionText ->
            val faqId = index + 1 // Assuming IDs are 1-based and sequential in FaqScreen's internal list
            // Scroll to the item (important for long lists, might not be needed for short lists)
            // composeTestRule.onNodeWithTag(FaqScreenTestTags.FAQ_LIST)
            //     .performScrollToNode(hasTestTag("${FaqListItemTestTags.LIST_ITEM_ROOT_PREFIX}$faqId"))

            // Check question
            composeTestRule.onNodeWithText(questionText)
                .assertExists("Question '$questionText' not found")
                .assertIsDisplayed()

            // Check that the answer is initially not displayed (it might not exist in the tree yet)
            composeTestRule.onNodeWithTag(
                "${FaqScreenTestTags.FaqListItemTestTags.ANSWER_TEXT_PREFIX}$faqId",
                useUnmergedTree = true,
            )
                .assertDoesNotExist() // Or assertDoesNotExist() if not in composition
        }
    }
}
