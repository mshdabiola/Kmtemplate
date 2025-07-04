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
package com.mshdabiola.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.analytics.LocalAnalyticsHelper
import com.mshdabiola.model.Note

@OptIn(ExperimentalSharedTransitionApi::class)
fun LazyListScope.noteItems(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope,
    items: List<Note>,
    onNoteClick: (Long) -> Unit,
) = items(
    items = items,
    key = { it.id },
    itemContent = { note ->
        val analyticsHelper = LocalAnalyticsHelper.current

        with(sharedTransitionScope) {
            NoteCard(
                modifier =
                modifier
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState("item ${note.id}"),
                        animatedVisibilityScope = animatedContentScope,
                    ),
                noteUiState = note,
                onClick = {
                    analyticsHelper.logNoteOpened(note.id.toString())
                    onNoteClick(note.id)
                },
            )
        }
    },
)

@Composable
fun NoteCard(
    modifier: Modifier,
    noteUiState: Note,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = modifier.clickable { onClick() },
        headlineContent = { Text(text = noteUiState.title) },
        supportingContent = { Text(text = noteUiState.content) },
    )
}
