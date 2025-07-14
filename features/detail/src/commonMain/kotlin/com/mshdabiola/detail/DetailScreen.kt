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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import com.mshdabiola.designsystem.component.KmtIconButton
import com.mshdabiola.designsystem.component.KmtTextField
import com.mshdabiola.designsystem.component.KmtTopAppBar
import com.mshdabiola.designsystem.icon.KmtIcons
import com.mshdabiola.ui.LocalNavAnimatedContentScope
import com.mshdabiola.ui.LocalSharedTransitionScope

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun DetailScreen(
    modifier: Modifier = Modifier,
    id: Long = -1,
    state: DetailState,
    onBack: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalNavAnimatedContentScope.current
    with(sharedTransitionScope) {
        Scaffold(
            modifier = modifier.sharedBounds(
                sharedContentState = rememberSharedContentState("note_$id"),
                animatedVisibilityScope = animatedContentScope,
            ),
            topBar = {
                KmtTopAppBar(
                    title = { Text("Note") },
                    actions = {
                        KmtIconButton(onClick = onDelete) {
                            Icon(imageVector = KmtIcons.Delete, contentDescription = "delete")
                        }
                    },
                    navigationIcon = {
                        KmtIconButton(onClick = onBack) {
                            Icon(imageVector = KmtIcons.ArrowBack, contentDescription = "back")
                        }
                    },
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            ) {
                KmtTextField(
                    modifier =
                    Modifier
                        .fillMaxWidth(),
                    state = state.title,
                    placeholder = "Title",
                    maxNum = TextFieldLineLimits.SingleLine,
                    imeAction = ImeAction.Next,
                )
                KmtTextField(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    state = state.detail,
                    placeholder = "content",
                )
            }
        }
    }
}
