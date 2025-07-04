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

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.KmtTab
import com.mshdabiola.designsystem.component.KmtTabRow
import com.mshdabiola.testing.util.CaptureMultiTheme

class TabsScreenshotTests {

    @Preview
    @Composable
    fun Tabs() {
        CaptureMultiTheme {
            KmtTabsExample()
        }
    }

    @Preview
    @Preview(fontScale = 2.0f)
    @Composable
    fun TabsHumFontScale2() {
        CaptureMultiTheme {
            KmtTabsExample("Looooong item")
        }
    }

    @Composable
    private fun KmtTabsExample(label: String = "Topics") {
        Surface {
            val titles = listOf(label, "People")
            KmtTabRow(selectedTabIndex = 0) {
                titles.forEachIndexed { index, title ->
                    KmtTab(
                        selected = index == 0,
                        onClick = { },
                        text = { Text(text = title) },
                    )
                }
            }
        }
    }
}
