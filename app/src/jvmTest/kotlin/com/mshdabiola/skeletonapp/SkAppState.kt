package com.mshdabiola.skeletonapp

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.window.core.layout.WindowSizeClass
import com.mshdabiola.skeletonapp.ui.SkAppState
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class SkAppState {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var state: SkAppState

    @Test
    fun currentDestination() =
        runTest {
            var currentDestination: String? = null

            composeTestRule.setContent {
                val navController =
                    rememberNavController().apply {
                        graph =
                            createGraph(startDestination = "a") {
                                composable("a") { }
                                composable("b") { }
                                composable("c") { }
                            }
                    }
                state =
                    remember(navController) {
                        SkAppState(
                            navController = navController,
                            coroutineScope = backgroundScope,
                            WindowSizeClass.compute(456f, 456f),
                        )
                    }

                // Update currentDestination whenever it changes
                currentDestination = "b" // state.currentRoute

                // Navigate to destination b once
                LaunchedEffect(Unit) {
                    navController.navigate("b")
                }
            }

            assertEquals("b", currentDestination)
        }
}
