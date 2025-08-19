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
package com.mshdabiola.setting.detailscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.KmtTextButton
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.UserSettings
import kmtemplate.feature.setting.generated.resources.Res
import kmtemplate.feature.setting.generated.resources.update_screen_check_for_update_button
import kmtemplate.feature.setting.generated.resources.update_screen_join_beta_release_text
import kmtemplate.feature.setting.generated.resources.update_screen_show_update_dialog_text
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UpdateScreen(
    modifier: Modifier = Modifier,
    userSettings: UserSettings,
    onSetUpdateDialog: (Boolean) -> Unit = {},
    onSetUpdateFromPreRelease: (Boolean) -> Unit = {},
    onCheckForUpdate: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        // Changed from SpacedBy to Top for more control with Spacers
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSetUpdateDialog(!userSettings.showUpdateDialog) }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.update_screen_show_update_dialog_text),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Switch(
                checked = userSettings.showUpdateDialog,
                onCheckedChange = { onSetUpdateDialog(it) },
                modifier = Modifier,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSetUpdateFromPreRelease(!userSettings.updateFromPreRelease) }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.update_screen_join_beta_release_text),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Switch(
                checked = userSettings.updateFromPreRelease,
                onCheckedChange = { onSetUpdateFromPreRelease(it) },
                modifier = Modifier,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        KmtTextButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = onCheckForUpdate,
        ) {
            Text(text = stringResource(Res.string.update_screen_check_for_update_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UpdateScreenPreview() {
    KmtTheme {
        UpdateScreen(userSettings = UserSettings())
    }
}
