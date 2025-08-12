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

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.KmtButton
import com.mshdabiola.designsystem.component.KmtTextButton
import com.mshdabiola.model.ReleaseInfo
import com.mshdabiola.model.testtag.ReleaseUpdateTags
import kmtemplate.core.ui.generated.resources.Res
import kmtemplate.core.ui.generated.resources.release_update_dialog_cancel_button
import kmtemplate.core.ui.generated.resources.release_update_dialog_download_button
import kmtemplate.core.ui.generated.resources.release_update_dialog_title
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource

@Composable
fun ReleaseUpdateDialog(
    releaseInfo: ReleaseInfo.Success,
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
                text = releaseInfo.body,
                modifier = Modifier
                    .testTag(ReleaseUpdateTags.RELEASE_UPDATE_DIALOG_BODY_TAG)
                    .height(200.dp)
                    .verticalScroll(scrollState)
                ,
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
    val releaseInfo = ReleaseInfo.Success(
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
