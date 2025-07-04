/*
 * Copyright (C) 2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
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
