/*
 *abiola 2022
 */

package com.mshdabiola.detail

import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loading_showsLoadingSpinner() {
        composeTestRule.setContent {
            Text("abiola")
        }
//
//        composeTestRule
//            .onNodeWithContentDescription(
//                composeTestRule.activity.resources.getString(R.string.feature_bookmarks_loading),
//            )
//            .assertExists()
    }
}
