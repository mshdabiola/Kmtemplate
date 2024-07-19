package com.mshdabiola.main

import androidx.compose.ui.test.junit4.createComposeRule
import com.mshdabiola.data.model.Result
import org.junit.Rule
import org.junit.Test

class MainScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun main() {
        composeRule.setContent {
            MainScreen(mainState = Result.Loading)
        }
    }
}
