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
package com.mshdabiola.detail

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
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
                    state = DetailState.Success(1),
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope,
                    title = rememberTextFieldState("title"),
                    content = rememberTextFieldState("content"),
                )
            }
        }

        composeRule.onNodeWithTag("detail:title").assertExists()
        composeRule.onNodeWithTag("detail:content").assertExists()
    }
}
