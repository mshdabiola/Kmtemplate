package com.mshdabiola.detail

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import kotlin.test.Test

class DetailScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun main() {
        composeRule.setContent {
            DetailScreen()
        }
    }
}
