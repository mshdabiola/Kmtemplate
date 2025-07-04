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

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.KmtTopAppBar
import com.mshdabiola.designsystem.icon.KmtIcons
import com.mshdabiola.testing.util.CaptureMultiTheme

class TopAppBarScreenshotTests() {

    @Preview
    @Composable
    fun TopAppBar() {
        CaptureMultiTheme {
            KmtTopAppBarExample()
        }
    }

    @Preview(fontScale = 2.0f)
    @Composable
    fun TopAppBarHumFontScale2() {
        CaptureMultiTheme {
            KmtTopAppBarExample()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun KmtTopAppBarExample() {
        KmtTopAppBar(
            titleRes = "untitled",
            navigationIcon = KmtIcons.Search,
            navigationIconContentDescription = "Navigation icon",
            actionIcon = KmtIcons.MoreVert,
            actionIconContentDescription = "Action icon",
        )
    }
}
