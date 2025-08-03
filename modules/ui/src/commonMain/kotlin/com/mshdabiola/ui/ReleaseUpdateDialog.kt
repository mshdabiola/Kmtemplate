package com.mshdabiola.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.component.KmtButton
import com.mshdabiola.model.ReleaseInfo
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ReleaseUpdateDialog(
    releaseInfo: ReleaseInfo.Success,
    onDismissRequest: () -> Unit,
    onDownloadClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text("New Update Available")
        },
        text = {
                Text(
                    text = releaseInfo.body,
                    maxLines = 10
                )
        },
        confirmButton = {
            KmtButton(onClick = onDownloadClick) {
                Text("Download")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
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
        asset =  "asset2.tar.gz"
    )
    ReleaseUpdateDialog(
        releaseInfo = releaseInfo,
        onDismissRequest = {},
        onDownloadClick = {}
    )
}
