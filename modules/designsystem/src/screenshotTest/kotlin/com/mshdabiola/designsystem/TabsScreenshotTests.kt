/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.HyaTab
import com.mshdabiola.designsystem.component.HyaTabRow
import com.mshdabiola.testing.util.CaptureMultiTheme

class TabsScreenshotTests {

    @Preview
    @Composable
    fun Tabs() {
        CaptureMultiTheme {
            HyaTabsExample()
        }
    }

    @Preview
    @Preview(fontScale = 2.0f)
    @Composable
    fun TabsHumFontScale2() {
        CaptureMultiTheme {
            HyaTabsExample("Looooong item")
        }
    }

    @Composable
    private fun HyaTabsExample(label: String = "Topics") {
        Surface {
            val titles = listOf(label, "People")
            HyaTabRow(selectedTabIndex = 0) {
                titles.forEachIndexed { index, title ->
                    HyaTab(
                        selected = index == 0,
                        onClick = { },
                        text = { Text(text = title) },
                    )
                }
            }
        }
    }
}
