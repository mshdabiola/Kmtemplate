/*
 *abiola 2024
 */

package com.mshdabiola.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class ProfileCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testText() {
        composeTestRule.setContent {
            ProfileCard(name = "abiola")
        }

        composeTestRule.onNodeWithText("abiola").assertExists()
    }
}
