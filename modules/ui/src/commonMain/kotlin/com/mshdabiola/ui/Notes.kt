/*
 *abiola 2022
 */

package com.mshdabiola.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NoteCard(
    modifier: Modifier,
    noteUiState: NoteUiState,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = modifier.clickable { onClick() },
        headlineContent = { Text(text = noteUiState.title) },
        supportingContent = { Text(text = noteUiState.content) },
    )
}

data class NoteUiState(
    val id: Long,
    val title: String,
    val content: String,
)
//
// @Preview
// @Composable
// private fun NoteUiPreview() {
//    SkTheme {
//        LazyColumn {
//            noteItem(
//                feedMainState = MainState.Loading,
//            )
//        }
//    }
// }
