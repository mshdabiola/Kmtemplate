/*
 * Copyright (C) 2024-2025 MshdAbiola
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
package com.mshdabiola.designsystem

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.HyaButton
import com.mshdabiola.designsystem.icon.HyaIcons
import com.mshdabiola.testing.util.CaptureMultiTheme

class ButtonScreenshotTests {

    @Preview
    @Composable
    fun Button() {
        CaptureMultiTheme {
            HyaButton(onClick = {}, text = { Text(" Button") })
        }
    }

    @Preview
    @Composable
    fun ButtonWithLeadIcon() {
        CaptureMultiTheme {
            HyaButton(
                onClick = {},
                text = { Text("Icon Button") },
                leadingIcon = { Icon(imageVector = HyaIcons.Add, contentDescription = null) },
            )
        }
    }
}
