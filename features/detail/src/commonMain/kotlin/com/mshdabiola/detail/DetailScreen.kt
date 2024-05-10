/*
 *abiola 2022
 */

package com.mshdabiola.detail

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
        title = viewModel.noteState.value.title,
        content = viewModel.noteState.value.content,
        onTitleChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange,
        onDelete = {
            viewModel.onDelete()
            onBack()
        },
        onBack = onBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@Composable
internal fun DetailScreen(
    modifier: Modifier = Modifier,
    title: String = "",
    content: String = "",
    screenSize: ScreenSize = ScreenSize.COMPACT,

    onTitleChange: (String) -> Unit = {},
    onContentChange: (String) -> Unit = {},
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
            value = title,
            onValueChange = onTitleChange,
            placeholder = "Title",
            maxNum = 1,
            imeAction = ImeAction.Next,
        )
        SkTextField(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("detail:content")
                .weight(1f),
            value = content,
            onValueChange = onContentChange,
            placeholder = "content",
            imeAction = ImeAction.Done,
            keyboardAction = { coroutineScope.launch { onShowSnackbar("Note Update", null) } },
        )
    }

    TrackScreenViewEvent(screenName = "Detail")
}

@Composable
private fun DetailScreenPreview() {
    DetailScreen()
}
