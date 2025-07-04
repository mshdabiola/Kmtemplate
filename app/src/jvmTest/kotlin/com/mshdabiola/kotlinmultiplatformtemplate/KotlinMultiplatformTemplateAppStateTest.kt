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
package com.mshdabiola.kotlinmultiplatformtemplate

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.window.core.layout.WindowSizeClass
import com.mshdabiola.kotlinmultiplatformtemplate.ui.KotlinMultiplatformTemplateAppState
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class KotlinMultiplatformTemplateAppStateTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var state: KotlinMultiplatformTemplateAppState

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
                        KotlinMultiplatformTemplateAppState(
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
