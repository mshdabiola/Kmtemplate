/*
 *abiola 2022
 */

package com.mshdabiola.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.noteItem(
    items: ImmutableList<NoteUiState>,
    onClick: (Long) -> Unit = {},
) {
    items(items) { noteUiState ->
        NoteUi(noteUiState = noteUiState, onClick = { onClick(noteUiState.id ?: 0) })
    }

//            items(items.itemCount) { index ->
//                val item = items[index]
//                if (item != null) {
//                    //  Item(item)
//                    NoteUi(
//                    noteUiState = item,
//                    onClick = {
// //                        analyticsHelper.logNoteOpened(
// //                            newsResourceId = note.id.toString(),
// //                        )
//                        onClick(item.id?:0)
//                        // launchCustomChromeTab(context, Uri.parse(""), backgroundColor)
//                    })
//
//                }
//            }
//            items.loadState.apply {
//                when {
//                    refresh is LoadStateNotLoading && items.itemCount < 1 -> {
//                        item {
//                            Box(
//                                modifier = Modifier.fillParentMaxSize(),
//                                contentAlignment = Alignment.Center,
//                            ) {
//                                Text(
//                                    text = "No Items",
//                                    modifier = Modifier.align(Alignment.Center),
//                                    textAlign = TextAlign.Center,
//                                )
//                            }
//                        }
//                    }
//
//                    refresh is LoadStateLoading -> {
//                        item {
//                            Box(
//                                modifier = Modifier.fillParentMaxSize(),
//                                contentAlignment = Alignment.Center,
//                            ) {
//                                CircularProgressIndicator(
//                                    Modifier.align(Alignment.Center),
//                                    color = MaterialTheme.colorScheme.primary,
//                                )
//                            }
//                        }
//                    }
//
//                    append is LoadStateLoading -> {
//                        item {
//                            CircularProgressIndicator(
//                                color = MaterialTheme.colorScheme.primary,
//                                modifier = Modifier.fillMaxWidth()
//                                    .padding(16.dp)
//                                    .wrapContentWidth(Alignment.CenterHorizontally),
//                            )
//                        }
//                    }
//
//                    refresh is LoadStateError -> {
//                        item {
// //                                ErrorView(
// //                                    message = "No Internet Connection",
// //                                    onClickRetry = { data.retry() },
// //                                    modifier = Modifier.fillParentMaxSize()
// //                                )
//                        }
//                    }
//
//                    append is LoadStateError -> {
//                        item {
// //                                ErrorItem(
// //                                    message = "No Internet Connection",
// //                                    onClickRetry = { data.retry() },
// //                                )
//                        }
//                    }
//                }
//            }
//            items(
//                items = feedMainState.noteUiStates,
//                key = { it.id },
//                contentType = { "newsFeedItem" },
//            ) { note ->
//                val analyticsHelper = LocalAnalyticsHelper.current
//
//                NoteUi(
//                    noteUiState = note,
//                    onClick = {
//                        analyticsHelper.logNoteOpened(
//                            newsResourceId = note.id.toString(),
//                        )
//                        onClick(note.id)
//                        // launchCustomChromeTab(context, Uri.parse(""), backgroundColor)
//                    },
//                    modifier = Modifier
//                        .padding(horizontal = 8.dp)
//                        .animateItemPlacement(),
//                )
//            }
}

@Composable
fun NoteUi(
    modifier: Modifier = Modifier,
    noteUiState: NoteUiState,
    onClick: (Long) -> Unit,
) {
    ListItem(
        modifier = modifier.clickable { onClick(noteUiState.id ?: 0) },
        headlineContent = { Text(text = noteUiState.title) },
        supportingContent = { Text(text = noteUiState.content) },
    )
}

data class NoteUiState(
    val id: Long?,
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
