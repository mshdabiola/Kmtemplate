/*
 *abiola 2022
 */

package com.mshdabiola.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mshdabiola.data.model.Result
import com.mshdabiola.designsystem.component.SkLoadingWheel
import com.mshdabiola.designsystem.component.SkTopAppBar
import com.mshdabiola.designsystem.component.scrollbar.DraggableScrollbar
import com.mshdabiola.designsystem.component.scrollbar.rememberDraggableScroller
import com.mshdabiola.designsystem.component.scrollbar.scrollbarState
import com.mshdabiola.designsystem.icon.SkIcons
import com.mshdabiola.designsystem.theme.LocalTintTheme
import com.mshdabiola.model.Image
import com.mshdabiola.ui.NoteUiState
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.noteItems
import hydraulic.features.main.generated.resources.Res
import hydraulic.features.main.generated.resources.features_main_empty_description
import hydraulic.features.main.generated.resources.features_main_empty_error
import hydraulic.features.main.generated.resources.features_main_img_empty_bookmarks
import hydraulic.features.main.generated.resources.features_main_loading
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

// import org.koin.androidx.compose.koinViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun MainRoute(
    screenSize: ScreenSize,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onClicked: (Long) -> Unit,
    navigateToSetting: () -> Unit,
    navigateToDetail: (Long) -> Unit,
//    viewModel: MainViewModel,
) {
    val viewModel: MainViewModel = koinViewModel()

    val feedNote = viewModel.feedUiMainState.collectAsStateWithLifecycleCommon()

    MainScreen(
        mainState = feedNote.value,
        screenSize = screenSize,
        navigateToDetail = navigateToDetail,
        navigateToSetting = navigateToSetting,
        //   items = timeline,

    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    mainState: Result<List<NoteUiState>>,
    screenSize: ScreenSize = ScreenSize.COMPACT,
    navigateToDetail: (Long) -> Unit = {},
    navigateToSetting: () -> Unit = {},
) {
    val state = rememberLazyListState()

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        // contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SkTopAppBar(
                titleRes = "Note",
                navigationIcon = SkIcons.Search,
                navigationIconContentDescription = "search",
                actionIcon = SkIcons.Settings,
                actionIconContentDescription = "setting",
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                ),
                onActionClick = navigateToSetting,
                onNavigationClick = { },
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.safeDrawing)
                    .testTag("main:add"),
                onClick = {
                    navigateToDetail(-1)
                },
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "add note",
                )
//                            Spacer(modifier = )
                Text("Add Note")
            }
        },
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding),
        ) {
            LazyColumn(
                state = state,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .testTag("main:list"),
            ) {
                item {
                    // Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
                }
                when (mainState) {
                    is Result.Loading -> item {
                        LoadingState()
                    }

                    is Result.Error -> TODO()
                    is Result.Success -> {
                        if (mainState.data.isEmpty()) {
                            item {
                                EmptyState()
                            }
                        } else {
                            noteItems(
                                items = mainState.data,
                                onNoteClick = navigateToDetail,
                                itemModifier = Modifier,
                            )
                        }
                    }
                }
                item {
                    Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
                }
            }
            val itemsAvailable = noteUiStateItemsSize(mainState)
            val scrollbarState = state.scrollbarState(
                itemsAvailable = itemsAvailable,
            )
            state.DraggableScrollbar(
                modifier = Modifier
                    .fillMaxHeight()
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .padding(horizontal = 2.dp)
                    .align(Alignment.CenterEnd),
                state = scrollbarState,
                orientation = Orientation.Vertical,
                onThumbMoved = state.rememberDraggableScroller(
                    itemsAvailable = itemsAvailable,
                ),
            )
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    SkLoadingWheel(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
            .testTag("main:loading"),
        contentDesc = stringResource(Res.string.features_main_loading),
    )
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .testTag("main:empty"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val iconTint = LocalTintTheme.current.iconTint
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(Res.drawable.features_main_img_empty_bookmarks),
            colorFilter = if (iconTint != Color.Unspecified) ColorFilter.tint(iconTint) else null,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = stringResource(Res.string.features_main_empty_error),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(Res.string.features_main_empty_description),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
private fun noteUiStateItemsSize(
    topicUiState: Result<List<NoteUiState>>,
) = when (topicUiState) {
    is Result.Error -> 0 // Nothing
    is Result.Loading -> 1 // Loading bar
    is Result.Success -> topicUiState.data.size + 2
}

@Composable
fun ItemImage(imageModel: Image) {
    ListItem(
        headlineContent = { Text(imageModel.user ?: "name") },
        leadingContent = {
            AsyncImage(
                modifier = Modifier.size(150.dp),
                model = imageModel.url,
                contentDescription = null,
            )
        },
    )
}
