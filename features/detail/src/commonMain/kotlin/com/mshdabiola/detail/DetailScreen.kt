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
import com.mshdabiola.designsystem.component.KmtTextField
import com.mshdabiola.designsystem.component.KmtTopAppBar
import com.mshdabiola.designsystem.icon.KmtIcons
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
    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.state.collectAsStateWithLifecycle()
    DetailScreen(
        modifier = modifier,
        state = state.value,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        title = viewModel.title,
        content = viewModel.content,
        onDelete = {
            viewModel.onDelete()
            onBack()
        },
        onBack = {
            onBack
            coroutineScope.launch {
                onShowSnackbar("Note Deleted", null)
            }
        },
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
    state: DetailState.Success,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope,
    title: TextFieldState = TextFieldState(),
    content: TextFieldState = TextFieldState(),
    onBack: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    with(sharedTransitionScope) {
        Column(
            modifier.sharedBounds(
                sharedContentState = rememberSharedContentState("item ${state.id}"),
                animatedVisibilityScope = animatedContentScope,
            ),
        ) {
            KmtTopAppBar(
                titleRes = "Note",
                navigationIcon = KmtIcons.ArrowBack,
                navigationIconContentDescription = "",
                actionIcon = KmtIcons.Delete,
                actionIconContentDescription = "delete",
                onActionClick = { onDelete() },
                onNavigationClick = { onBack() },
            )
            KmtTextField(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .testTag("detail:title"),
                state = title,
                placeholder = "Title",
                maxNum = TextFieldLineLimits.SingleLine,
                imeAction = ImeAction.Next,
            )
            KmtTextField(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .testTag("detail:content")
                    .weight(1f),
                state = content,
                placeholder = "content",
                imeAction = ImeAction.Done,
//                keyboardAction = { coroutineScope.launch { onShowSnackbar("Note Update", null) } },
            )
        }
    }

    TrackScreenViewEvent(screenName = "Detail")
}
