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
