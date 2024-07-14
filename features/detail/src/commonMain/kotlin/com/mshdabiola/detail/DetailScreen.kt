/*
 *abiola 2022
 */

package com.mshdabiola.detail

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import com.mshdabiola.designsystem.component.SkTextField
import com.mshdabiola.designsystem.component.SkTopAppBar
import com.mshdabiola.designsystem.icon.SkIcons
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.TrackScreenViewEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun DetailRoute(
    screenSize: ScreenSize,

    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel,
) {
    DetailScreen(
        onShowSnackbar = onShowSnackbar,
        modifier = modifier,
        screenSize = screenSize,
        title = viewModel.title,
        content = viewModel.content,
        onDelete = {
            viewModel.onDelete()
            onBack()
        },
        onBack = onBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@Composable
internal fun DetailScreen(
    modifier: Modifier = Modifier,
    title: TextFieldState = TextFieldState(),
    content: TextFieldState = TextFieldState(),
    screenSize: ScreenSize = ScreenSize.COMPACT,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    onBack: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        // contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SkTopAppBar(
                titleRes = "Note",
                navigationIcon = SkIcons.ArrowBack,
                navigationIconContentDescription = "back",
                actionIcon = Icons.Default.Delete,
                actionIconContentDescription = "delete",
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                ),
                onActionClick = onDelete,
                onNavigationClick = onBack,
            )
        },
    ) { padding ->
        Column(Modifier.padding(padding)) {
            SkTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("detail:title"),
                state = title,
                placeholder = "Title",
                maxNum = TextFieldLineLimits.SingleLine,
                imeAction = ImeAction.Next,
            )
            SkTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("detail:content")
                    .weight(1f),
                state = content,
                placeholder = "content",
                imeAction = ImeAction.Done,
                keyboardAction = { coroutineScope.launch { onShowSnackbar("Note Update", null) } },
            )
        }
    }

    TrackScreenViewEvent(screenName = "Detail")
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DetailScreenPreview() {
    DetailScreen()
}
