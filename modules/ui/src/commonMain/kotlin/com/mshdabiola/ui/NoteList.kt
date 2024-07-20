/*
 *abiola 2022
 */

package com.mshdabiola.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import com.mshdabiola.analytics.LocalAnalyticsHelper

@OptIn(ExperimentalSharedTransitionApi::class)
fun LazyListScope.noteItems(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    items: List<NoteUiState>,
    onNoteClick: (Long) -> Unit,
) = items(
    items = items,
    key = { it.id },
    itemContent = { note ->
        val analyticsHelper = LocalAnalyticsHelper.current

        with(sharedTransitionScope) {
            NoteCard(
                modifier = modifier
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState("item"),
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
