/*
 * Copyright (C) 2023-2025 MshdAbiola
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

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.HyaFilterChip
import com.mshdabiola.testing.util.CaptureMultiTheme

class FilterChipScreenshotTests {

    @Preview
    @Composable
    fun UnSelectedFilterChip() {
        CaptureMultiTheme {
            HyaFilterChip(selected = false, onSelectedChange = {}) {
                Text("Unselected chip")
            }
        }
    }

    @Preview
    @Composable
    fun SelectedFilterChip() {
        CaptureMultiTheme {
            HyaFilterChip(selected = true, onSelectedChange = {}) {
                Text("Selected Chip")
            }
        }
    }

    @Preview
    @Preview(fontScale = 2.0f)
    @Composable
    fun HugeFontFilterChip() {
        CaptureMultiTheme {
            HyaFilterChip(selected = true, onSelectedChange = {}) {
                Text("Chip")
            }
        }
    }
}
