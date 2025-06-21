/*
 * Copyright (C) 2022-2025 MshdAbiola
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
