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

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.HyaIconToggleButton
import com.mshdabiola.designsystem.icon.HyaIcons
import com.mshdabiola.testing.util.CaptureMultiTheme

class IconButtonScreenshotTests {

    @Preview
    @Composable
    fun ToggleButton() {
        CaptureMultiTheme {
            HyaIconToggleButton(
                checked = true,
                onCheckedChange = { },
                icon = {
                    Icon(
                        imageVector = HyaIcons.BookmarkBorder,
                        contentDescription = null,
                    )
                },
                checkedIcon = {
                    Icon(
                        imageVector = HyaIcons.Bookmark,
                        contentDescription = null,
                    )
                },
            )
        }
    }

    @Preview
    @Composable
    fun UnToggleButton() {
        CaptureMultiTheme {
            HyaIconToggleButton(
                checked = false,
                onCheckedChange = { },
                icon = {
                    Icon(
                        imageVector = HyaIcons.BookmarkBorder,
                        contentDescription = null,
                    )
                },
                checkedIcon = {
                    Icon(
                        imageVector = HyaIcons.Bookmark,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}
