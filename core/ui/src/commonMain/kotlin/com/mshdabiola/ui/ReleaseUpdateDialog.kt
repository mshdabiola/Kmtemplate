package com.mshdabiola.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.mshdabiola.designsystem.component.KmtButton
import com.mshdabiola.model.ReleaseInfo
import org.jetbrains.compose.ui.tooling.preview.Preview

const val RELEASE_UPDATE_DIALOG_TAG = "release_update_dialog"
const val RELEASE_UPDATE_DIALOG_TITLE_TAG = "release_update_dialog_title"
const val RELEASE_UPDATE_DIALOG_BODY_TAG = "release_update_dialog_body"
const val RELEASE_UPDATE_DIALOG_CONFIRM_BUTTON_TAG = "release_update_dialog_confirm_button"
const val RELEASE_UPDATE_DIALOG_DISMISS_BUTTON_TAG = "release_update_dialog_dismiss_button"

@Composable
fun ReleaseUpdateDialog(
    releaseInfo: ReleaseInfo.Success,
    onDismissRequest: () -> Unit,
    onDownloadClick: () -> Unit,
) {
    AlertDialog(
        modifier = Modifier.testTag(RELEASE_UPDATE_DIALOG_TAG),
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "New Update Available",
                modifier = Modifier.testTag(RELEASE_UPDATE_DIALOG_TITLE_TAG)
            )
        },
        text = {
            Text(
                text = releaseInfo.body,
                maxLines = 10,
                modifier = Modifier.testTag(RELEASE_UPDATE_DIALOG_BODY_TAG)
            )
        },
        confirmButton = {
            KmtButton(
                onClick = onDownloadClick,
                modifier = Modifier.testTag(RELEASE_UPDATE_DIALOG_CONFIRM_BUTTON_TAG)
            ) {
                Text("Download")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
                modifier = Modifier.testTag(RELEASE_UPDATE_DIALOG_DISMISS_BUTTON_TAG)
            ) {
                Text("Cancel")
            }
        }

    )
}

@Preview
@Composable
fun ReleaseUpdateDialogPreview() {
    val releaseInfo = ReleaseInfo.Success(
        tagName = "v1.0.0",
        releaseName = "Initial Release",
        body = "This is the first release of the application.",
        asset = "asset2.tar.gz"
    )
    ReleaseUpdateDialog(
        releaseInfo = releaseInfo,
        onDismissRequest = {},
        onDownloadClick = {}
    )
}
