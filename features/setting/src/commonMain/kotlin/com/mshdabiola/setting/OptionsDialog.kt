/*
 * Copyright (C) 2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
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
