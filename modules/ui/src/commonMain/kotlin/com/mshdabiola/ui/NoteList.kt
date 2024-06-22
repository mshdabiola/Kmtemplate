/*
 *abiola 2022
 */

package com.mshdabiola.ui

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import com.mshdabiola.analytics.LocalAnalyticsHelper

fun LazyListScope.noteItems(
    items: List<NoteUiState>,
    onNoteClick: (Long) -> Unit,
    itemModifier: Modifier = Modifier,
) = items(
    items = items,
    key = { it.id },
    itemContent = { note ->
        val analyticsHelper = LocalAnalyticsHelper.current

        NoteCard(
            modifier = itemModifier,
            noteUiState = note,
            onClick = {
                analyticsHelper.logNoteOpened(note.id.toString())
                onNoteClick(note.id)
            },
        )
    },
)
