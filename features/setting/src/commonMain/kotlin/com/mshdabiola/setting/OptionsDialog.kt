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
package com.mshdabiola.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.mshdabiola.designsystem.component.KmtTextButton

@Composable
fun OptionsDialog(
    modifier: Modifier = Modifier,
    current: Int,
    options: List<String>,
    onSelect: (Int) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            KmtTextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                options.fastForEachIndexed { i, s ->
                    Row(
                        modifier =
                        Modifier.clickable {
                            onSelect(i)
                        },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        RadioButton(selected = i == current, onClick = { onSelect(i) })
                        Text(modifier = Modifier.weight(1f), text = s)
                    }
                }
            }
        },
    )
}
