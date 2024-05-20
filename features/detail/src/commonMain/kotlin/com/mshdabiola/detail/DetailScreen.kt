/*
 *abiola 2022
 */

package com.mshdabiola.detail

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import com.mshdabiola.designsystem.component.DetailTopAppBar
import com.mshdabiola.designsystem.component.SkTextField
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
    Column(modifier) {
        DetailTopAppBar(
            onNavigationClick = onBack,
            onDeleteClick = onDelete,
        )
        SkTextField(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("detail:title"),
            state = title,
            placeholder = "Title",
            maxNum = androidx.compose.foundation.text2.input.TextFieldLineLimits.SingleLine,
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

    TrackScreenViewEvent(screenName = "Detail")
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DetailScreenPreview() {
    DetailScreen()
}
