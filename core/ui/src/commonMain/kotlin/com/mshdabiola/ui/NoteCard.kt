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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag // Import testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.Note
import com.mshdabiola.model.testtag.NoteCardTestTags
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    noteUiState: Note,
    onClick: (Long) -> Unit,
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalNavAnimatedContentScope.current
    with(sharedTransitionScope) {
        Card(
            modifier = modifier
                .testTag(NoteCardTestTags.ROOT) // Add test tag to the root element
                .sharedBounds(
                    sharedContentState = rememberSharedContentState("note_${noteUiState.id}"),
                    animatedVisibilityScope = animatedContentScope,
                )
                .fillMaxWidth()
                .clickable { onClick(noteUiState.id) },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
            ) {
                Text(
                    text = noteUiState.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .testTag(NoteCardTestTags.TITLE),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = noteUiState.content,
                    modifier = Modifier.testTag(NoteCardTestTags.CONTENT),
                    maxLines = 2,
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun NoteCardPreview() {
    val noteUiState =
        Note(id = 1L, title = "Sample Note", content = "This is a sample note content.")

    SharedTransitionContainer {
        NoteCard(
            noteUiState = noteUiState,
            onClick = {},
        )
    }
}
