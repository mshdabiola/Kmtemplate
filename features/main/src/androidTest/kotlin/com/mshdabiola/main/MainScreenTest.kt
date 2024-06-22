/*
 *abiola 2022
 */

package com.mshdabiola.main

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.mshdabiola.data.model.Result
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for [MainScreen] composable.
 */
class MainScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun enterText_showsShowText() {
        composeTestRule.setContent {
            MainScreen(
                mainState = Result.Loading,
            )
        }

//        composeTestRule
//            .onNodeWithTag("main:list")
//            .assertExists()
    }
}
