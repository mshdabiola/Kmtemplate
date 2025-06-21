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
package com.mshdabiola.main

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.ui.test.junit4.createComposeRule
import com.mshdabiola.data.model.Result
import com.mshdabiola.testing.fake.notes
import com.mshdabiola.ui.SharedContentPreview
import org.junit.Rule
import kotlin.test.Test

class MainScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun main() {
        composeRule.setContent {
            SharedContentPreview { sharedTransitionScope, animatedContentScope ->
                MainScreen(
                    mainState =
                    Result.Success(
                        notes,
                    ),
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope,
                )
            }
        }

//        composeRule.onNodeWithTag("main:screen").assertExists()
//        composeRule.onNodeWithTag("main:list").assertExists()
    }
}
