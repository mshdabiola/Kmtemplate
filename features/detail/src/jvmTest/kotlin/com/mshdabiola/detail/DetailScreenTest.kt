package com.mshdabiola.detail

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.ui.test.junit4.createComposeRule
import com.mshdabiola.ui.SharedContentPreview
import org.junit.Rule
import kotlin.test.Test

class DetailScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun main() {
        composeRule.setContent {
            SharedContentPreview { sharedTransitionScope, animatedContentScope ->
                DetailScreen(
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope,
                )
            }
        }
    }
}
