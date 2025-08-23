/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.DevicePreviews
import com.mshdabiola.model.Note

class NoteScreenshotTests {

    @OptIn(ExperimentalSharedTransitionApi::class)
    @DevicePreviews
    @Composable
    fun Light() {
        val noteUiState =
            Note(id = 1L, title = "Sample Note", content = "This is a sample note content.")

        SharedTransitionContainer {
            NoteCard(noteUiState = noteUiState, onClick = {})
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @DevicePreviews
    @Composable
    fun Dark() {
        Note(id = 1L, title = "Sample Note", content = "This is a sample note content.")

        SharedTransitionContainer(true) {
            NoteCard(noteUiState = noteUiState, onClick = {})
        }
    }
}
