/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import com.mshdabiola.detail.navigation.Detail
import com.mshdabiola.model.testtag.DetailScreenTestTags
import com.mshdabiola.ui.LocalNavAnimatedContentScope
import com.mshdabiola.ui.LocalSharedTransitionScope
import kmtemplate.feature.detail.generated.resources.Res
import kmtemplate.feature.detail.generated.resources.detail_back_icon_content_description
import kmtemplate.feature.detail.generated.resources.detail_content_placeholder
import kmtemplate.feature.detail.generated.resources.detail_delete_icon_content_description
import kmtemplate.feature.detail.generated.resources.detail_screen_title
import kmtemplate.feature.detail.generated.resources.detail_title_placeholder
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun DetailScreen(
    modifier: Modifier = Modifier,
    state: DetailState,
    detail: Detail = Detail(id = -1L),
    onBack: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalNavAnimatedContentScope.current
    with(sharedTransitionScope) {
        Scaffold(
            modifier = modifier
                .sharedBounds(
                    sharedContentState = rememberSharedContentState("note_${detail.id}"),
                    animatedVisibilityScope = animatedContentScope,
                )
                .testTag(DetailScreenTestTags.SCREEN_ROOT), // Apply testTag to the root
            topBar = {
                KmtTopAppBar(
                    modifier = Modifier.testTag(DetailScreenTestTags.TOP_APP_BAR),
                    title = { Text(stringResource(Res.string.detail_screen_title)) },
                    actions = {
                        if (state.id > -1L) {
                            KmtIconButton(
                                onClick = onDelete,
                                modifier = Modifier.testTag(DetailScreenTestTags.DELETE_BUTTON),
                            ) {
                                Icon(
                                    imageVector = KmtIcons.Delete,
                                    contentDescription = stringResource(
                                        Res.string.detail_delete_icon_content_description,
                                    ),
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        KmtIconButton(
                            onClick = onBack,
                            modifier = Modifier.testTag(DetailScreenTestTags.BACK_BUTTON),
                        ) {
                            Icon(
                                imageVector = KmtIcons.ArrowBack,
                                contentDescription = stringResource(
                                    Res.string.detail_back_icon_content_description,
                                ),
                            )
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
                    label = stringResource(Res.string.detail_title_placeholder),
                    maxNum = TextFieldLineLimits.SingleLine,
                    imeAction = ImeAction.Next,
                )
                KmtTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .testTag(DetailScreenTestTags.CONTENT_TEXT_FIELD),
                    state = state.detail,
                    imeAction = ImeAction.Default,
                    label = stringResource(Res.string.detail_content_placeholder),
                    maxNum = TextFieldLineLimits.MultiLine(),
                )
            }
        }
    }
}
