/*
 *abiola 2022
 */

package com.mshdabiola.detail

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import com.mshdabiola.designsystem.component.SkTextField
import com.mshdabiola.designsystem.component.SkTopAppBar
import com.mshdabiola.designsystem.icon.SkIcons
import com.mshdabiola.ui.TrackScreenViewEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun DetailRoute(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
    viewModel: DetailViewModel,
) {
    DetailScreen(
        modifier = modifier,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        onShowSnackbar = onShowSnackbar,
        title = viewModel.title,
        content = viewModel.content,
        onDelete = {
            viewModel.onDelete()
            onBack()
        },
        onBack = onBack,
    )
}

@OptIn(
    ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class,
)
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@Composable
internal fun DetailScreen(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    title: TextFieldState = TextFieldState(),
    content: TextFieldState = TextFieldState(),
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    onBack: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    with(sharedTransitionScope) {
        Column(
            modifier.sharedBounds(
                sharedContentState = rememberSharedContentState("item"),
                animatedVisibilityScope = animatedContentScope,
            ),
        ) {
            SkTopAppBar(
                titleRes = "Note",
                navigationIcon = SkIcons.ArrowBack,
                navigationIconContentDescription = "",
                actionIcon = SkIcons.Delete,
                actionIconContentDescription = "delete",
                onActionClick = { onDelete() },
                onNavigationClick = {onBack()}
            )
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
