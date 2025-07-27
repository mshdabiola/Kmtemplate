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
package com.mshdabiola.detail

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.DevicePreviews
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.ui.SharedTransitionContainer // Import for consistency with UI tests

@OptIn(ExperimentalSharedTransitionApi::class) // For SharedTransitionContainer and DetailScreen params
class DetailScreenScreenshotTests {

    private val sampleOnBack: () -> Unit = {}
    private val sampleOnDelete: () -> Unit = {}

    @DevicePreviews
    @Composable
    fun DetailScreen_WithContent_LightMode() {
        val detailStateWithContent = DetailState(
            id = 1L,
            title = TextFieldState("Sample Note Title"),
            detail = TextFieldState("This is the detailed content of the sample note."),
        )
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SharedTransitionContainer {
                    // Match UI test setup
                    DetailScreen(
                        // id = 1L, // id is now part of DetailState
                        state = detailStateWithContent,
                        onBack = sampleOnBack,
                        onDelete = sampleOnDelete,
                    )
                }
            }
        }
    }

    @DevicePreviews
    @Composable
    fun DetailScreen_WithContent_DarkMode() {
        val detailStateWithContent = DetailState(
            id = 2L, // Different ID for shared element uniqueness if needed across previews
            title = TextFieldState("Another Note Title"),
            detail = TextFieldState("Dark mode content example."),
        )
        KmtTheme(darkTheme = true) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SharedTransitionContainer {
                    DetailScreen(
                        // id = 2L,
                        state = detailStateWithContent,
                        onBack = sampleOnBack,
                        onDelete = sampleOnDelete,
                    )
                }
            }
        }
    }

    @DevicePreviews
    @Composable
    fun DetailScreen_EmptyContent_LightMode() {
        val emptyDetailState = DetailState(
            id = 3L,
            title = TextFieldState(""), // Empty title
            detail = TextFieldState(""), // Empty detail
        )
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SharedTransitionContainer {
                    DetailScreen(
                        // id = 3L,
                        state = emptyDetailState,
                        onBack = sampleOnBack,
                        onDelete = sampleOnDelete,
                    )
                }
            }
        }
    }

    @DevicePreviews
    @Composable
    fun DetailScreen_EmptyContent_DarkMode() {
        val emptyDetailState = DetailState(
            id = 4L,
            title = TextFieldState(""), // Empty title
            detail = TextFieldState(""), // Empty detail
        )
        KmtTheme(darkTheme = true) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SharedTransitionContainer {
                    DetailScreen(
                        // id = 4L,
                        state = emptyDetailState,
                        onBack = sampleOnBack,
                        onDelete = sampleOnDelete,
                    )
                }
            }
        }
    }

    // Test with very long content to see how it handles scrolling/wrapping (if applicable)
    // Though screenshot will only capture the visible part.
    @DevicePreviews
    @Composable
    fun DetailScreen_LongContent_LightMode() {
        val longContent = "This is a very long note content to see how the text field behaves. " +
            "It should ideally wrap to multiple lines and allow scrolling if the content " +
            "exceeds the available space. Screenshot tests will capture the initial visible portion. " +
            "Adding more text to make it even longer. ".repeat(10)
        val detailStateLongContent = DetailState(
            id = 5L,
            title = TextFieldState("Note With Very Long Content"),
            detail = TextFieldState(longContent),
        )
        KmtTheme(darkTheme = false) {
            Surface(modifier = Modifier.fillMaxSize()) {
                SharedTransitionContainer {
                    DetailScreen(
                        // id = 5L,
                        state = detailStateLongContent,
                        onBack = sampleOnBack,
                        onDelete = sampleOnDelete,
                    )
                }
            }
        }
    }
}
