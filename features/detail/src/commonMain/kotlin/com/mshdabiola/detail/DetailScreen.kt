/*
 *abiola 2022
 */

package com.mshdabiola.detail

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mshdabiola.designsystem.component.HyaTextField
import com.mshdabiola.designsystem.component.HyaTopAppBar
import com.mshdabiola.designsystem.icon.HyaIcons
import com.mshdabiola.ui.TrackScreenViewEvent
import com.mshdabiola.ui.Waiting
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
    val state = viewModel.state.collectAsStateWithLifecycle()
    DetailScreen(
        modifier = modifier,
        state = state.value,
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
    ExperimentalSharedTransitionApi::class,
    ExperimentalMaterial3Api::class,
)
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@Composable
internal fun DetailScreen(
    modifier: Modifier = Modifier,
    state: DetailState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope,
    title: TextFieldState = TextFieldState(),
    content: TextFieldState = TextFieldState(),
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    onBack: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    AnimatedContent(state) {
        when (it) {
            is DetailState.Loading -> Waiting(modifier)
            is DetailState.Success ->
                MainContent(
                    modifier = modifier,
                    state = it,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope,
                    title = title,
                    content = content,
                    onShowSnackbar = onShowSnackbar,
                    onBack = onBack,
                    onDelete = onDelete,
                )
            else -> {}
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun MainContent(
    modifier: Modifier = Modifier,
    state: DetailState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope,
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
            HyaTopAppBar(
                titleRes = "Note",
                navigationIcon = HyaIcons.ArrowBack,
                navigationIconContentDescription = "",
                actionIcon = HyaIcons.Delete,
                actionIconContentDescription = "delete",
                onActionClick = { onDelete() },
                onNavigationClick = { onBack() },
            )
            HyaTextField(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .testTag("detail:title"),
                state = title,
                placeholder = "Title",
                maxNum = TextFieldLineLimits.SingleLine,
                imeAction = ImeAction.Next,
            )
            HyaTextField(
                modifier =
                    Modifier
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
