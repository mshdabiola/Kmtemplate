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

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag // Import testTag
import androidx.compose.ui.text.input.ImeAction
import com.mshdabiola.designsystem.component.KmtIconButton
import com.mshdabiola.designsystem.component.KmtTextField
import com.mshdabiola.designsystem.component.KmtTopAppBar
import com.mshdabiola.designsystem.drawable.KmtIcons
import com.mshdabiola.ui.LocalNavAnimatedContentScope
import com.mshdabiola.ui.LocalSharedTransitionScope

// Define a TestTags object
internal object DetailScreenTestTags {
    const val SCREEN_ROOT = "DetailScreenRoot"
    const val TITLE_TEXT_FIELD = "DetailScreenTitleTextField"
    const val CONTENT_TEXT_FIELD = "DetailScreenContentTextField"
    const val DELETE_BUTTON = "DetailScreenDeleteButton"
    const val BACK_BUTTON = "DetailScreenBackButton"
    const val TOP_APP_BAR = "DetailScreenTopAppBar"
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun DetailScreen(
    modifier: Modifier = Modifier,
    state: DetailState,
    onBack: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalNavAnimatedContentScope.current
    with(sharedTransitionScope) {
        Scaffold(
            modifier = modifier
                .sharedBounds(
                    sharedContentState = rememberSharedContentState("note_${state.note.id}"),
                    animatedVisibilityScope = animatedContentScope,
                )
                .testTag(DetailScreenTestTags.SCREEN_ROOT), // Apply testTag to the root
            topBar = {
                KmtTopAppBar(
                    modifier = Modifier.testTag(DetailScreenTestTags.TOP_APP_BAR),
                    title = { Text("Note") },
                    actions = {
                        KmtIconButton(
                            onClick = onDelete,
                            modifier = Modifier.testTag(DetailScreenTestTags.DELETE_BUTTON),
                        ) {
                            Icon(imageVector = KmtIcons.Delete, contentDescription = "delete")
                        }
                    },
                    navigationIcon = {
                        KmtIconButton(
                            onClick = onBack,
                            modifier = Modifier.testTag(DetailScreenTestTags.BACK_BUTTON),
                        ) {
                            Icon(imageVector = KmtIcons.ArrowBack, contentDescription = "back")
                        }
                    },
                )
            },
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            ) {
                KmtTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(DetailScreenTestTags.TITLE_TEXT_FIELD),
                    state = state.title,
                    placeholder = "Title",
                    maxNum = TextFieldLineLimits.SingleLine,
                    imeAction = ImeAction.Next,
                )
                KmtTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .testTag(DetailScreenTestTags.CONTENT_TEXT_FIELD),
                    state = state.detail,
                    placeholder = "content",
                )
            }
        }
    }
}
