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
package com.mshdabiola.main

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag // Import testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.KmtIconButton
import com.mshdabiola.designsystem.component.KmtLoading
import com.mshdabiola.designsystem.component.KmtTopAppBar
import com.mshdabiola.designsystem.drawable.KmtIcons
import com.mshdabiola.designsystem.strings.KmtStrings
import com.mshdabiola.designsystem.theme.LocalTintTheme
import com.mshdabiola.ui.NoteCard
import kmtemplate.feature.main.generated.resources.Res
import kmtemplate.feature.main.generated.resources.features_main_empty_description
import kmtemplate.feature.main.generated.resources.features_main_empty_error
import kmtemplate.feature.main.generated.resources.features_main_img_empty_bookmarks
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

// Define a TestTags object for MainScreen
object MainScreenTestTags {
    const val SCREEN_ROOT = "MainScreenRoot"
    const val TOP_APP_BAR = "MainScreenTopAppBar"
    const val LOADING_INDICATOR = "MainScreenLoadingIndicator"
    const val EMPTY_STATE_COLUMN = "MainScreenEmptyStateColumn"
    const val EMPTY_STATE_IMAGE = "MainScreenEmptyStateImage"
    const val EMPTY_STATE_TITLE = "MainScreenEmptyStateTitle"
    const val EMPTY_STATE_DESCRIPTION = "MainScreenEmptyStateDescription"
    const val NOTE_LIST = "MainScreenNoteList"
    // You might want a way to tag individual NoteCard items if needed for specific tests,
    // e.g., by appending the note ID: fun noteCardTag(id: Long) = "MainScreenNoteCard_$id"
}

@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalMaterial3Api::class,
)
@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    mainState: MainState,
    navigateToDetail: (Long) -> Unit = {},
    onDrawer: (() -> Unit)? = null,
) {
    Scaffold(
        modifier = modifier.testTag(MainScreenTestTags.SCREEN_ROOT), // Apply testTag to the root
        topBar = {
            KmtTopAppBar(
                modifier = Modifier.testTag(MainScreenTestTags.TOP_APP_BAR),
                title = {
                    Text(if (onDrawer != null) KmtStrings.brand else "Main")
                }, // Consider adding a test tag if the title becomes dynamic
                titleHorizontalAlignment = Alignment.Start,
                navigationIcon = {
                    if (onDrawer != null) {
                        KmtIconButton(onClick = onDrawer) {
                            Icon(KmtIcons.Menu, "menu")
                        }
                    }
                },
            )
        },
        containerColor = Color.Transparent,
    ) { paddingValues ->
        when (mainState) {
            is MainState.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .testTag(MainScreenTestTags.LOADING_INDICATOR), // Tag for loading state
                    contentAlignment = Alignment.Center,
                ) {
                    KmtLoading() // If KmtLoading is a simple composable, this tag might be on the Box.
                    // If KmtLoading is complex, it might need its own internal tags.
                }
            }
            is MainState.Empty -> {
                EmptyState(
                    modifier = Modifier.padding(paddingValues),
                    // Pass padding if EmptyState should be inside Scaffold's content area
                )
            }
            is MainState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp)
                        .testTag(MainScreenTestTags.NOTE_LIST), // Tag for the list of notes
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(items = mainState.notes, key = { note -> note.id }) { note ->
                        // Assuming NoteUiState has an id
                        NoteCard(
                            noteUiState = note,
                            onClick = navigateToDetail,
                            // If you need to test individual cards, apply a dynamic tag here:
                            // modifier = Modifier.testTag(MainScreenTestTags.noteCardTag(note.id))
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .testTag(MainScreenTestTags.EMPTY_STATE_COLUMN), // Tag for the empty state container
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val iconTint = LocalTintTheme.current.iconTint
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(MainScreenTestTags.EMPTY_STATE_IMAGE),
            painter = painterResource(Res.drawable.features_main_img_empty_bookmarks),
            colorFilter = if (iconTint != Color.Unspecified) ColorFilter.tint(iconTint) else null,
            contentDescription = null, // Consider adding a content description for accessibility and testing
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = stringResource(Res.string.features_main_empty_error),
            modifier = Modifier
                .fillMaxWidth()
                .testTag(MainScreenTestTags.EMPTY_STATE_TITLE),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(Res.string.features_main_empty_description),
            modifier = Modifier
                .fillMaxWidth()
                .testTag(MainScreenTestTags.EMPTY_STATE_DESCRIPTION),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
