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
package com.mshdabiola.ui

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.mshdabiola.designsystem.component.KmtButton
import com.mshdabiola.designsystem.component.KmtTextButton
import com.mshdabiola.model.ReleaseInfo
import com.mshdabiola.model.testtag.ReleaseUpdateTags
import kmtemplate.core.ui.generated.resources.Res
import kmtemplate.core.ui.generated.resources.release_update_dialog_body
import kmtemplate.core.ui.generated.resources.release_update_dialog_cancel_button
import kmtemplate.core.ui.generated.resources.release_update_dialog_download_button
import kmtemplate.core.ui.generated.resources.release_update_dialog_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ReleaseUpdateDialog(
    releaseInfo: ReleaseInfo.NewUpdate,
    onDismissRequest: () -> Unit,
    onDownloadClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    AlertDialog(
        modifier = Modifier.testTag(ReleaseUpdateTags.RELEASE_UPDATE_DIALOG_TAG),
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(Res.string.release_update_dialog_title),
                modifier = Modifier.testTag(ReleaseUpdateTags.RELEASE_UPDATE_DIALOG_TITLE_TAG),
            )
        },
        text = {
            Text(
                text = stringResource(Res.string.release_update_dialog_body, releaseInfo.tagName),
                modifier = Modifier
                    .testTag(ReleaseUpdateTags.RELEASE_UPDATE_DIALOG_BODY_TAG)
                    .verticalScroll(scrollState),
            )
        },
        confirmButton = {
            KmtButton(
                onClick = onDownloadClick,
                modifier = Modifier.testTag(ReleaseUpdateTags.RELEASE_UPDATE_DIALOG_CONFIRM_BUTTON_TAG),
            ) {
                Text(stringResource(Res.string.release_update_dialog_download_button))
            }
        },
        dismissButton = {
            KmtTextButton(
                onClick = onDismissRequest,
                modifier = Modifier.testTag(ReleaseUpdateTags.RELEASE_UPDATE_DIALOG_DISMISS_BUTTON_TAG),
            ) {
                Text(stringResource(Res.string.release_update_dialog_cancel_button))
            }
        },

    )
}

@Preview
@Composable
fun ReleaseUpdateDialogPreview() {
    val releaseInfo = ReleaseInfo.NewUpdate(
        tagName = "v1.0.0",
        releaseName = "Initial Release",
        body = "This is the first release of the application.",
        asset = "asset2.tar.gz",
    )
    ReleaseUpdateDialog(
        releaseInfo = releaseInfo,
        onDismissRequest = {},
        onDownloadClick = {},
    )
}
