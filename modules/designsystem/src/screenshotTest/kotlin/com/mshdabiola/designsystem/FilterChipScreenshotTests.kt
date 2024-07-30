/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.SkFilterChip
import com.mshdabiola.testing.util.CaptureMultiTheme

class FilterChipScreenshotTests {

    @Preview
    @Composable
    fun UnSelectedFilterChip() {
        CaptureMultiTheme {
            SkFilterChip(selected = false, onSelectedChange = {}) {
                Text("Unselected chip")
            }
        }
    }

    @Preview
    @Composable
    fun SelectedFilterChip() {
        CaptureMultiTheme {
            SkFilterChip(selected = true, onSelectedChange = {}) {
                Text("Selected Chip")
            }
        }
    }

    @Preview
    @Preview(fontScale = 2.0f)
    @Composable
    fun HugeFontFilterChip() {
        CaptureMultiTheme {
            SkFilterChip(selected = true, onSelectedChange = {}) {
                Text("Chip")
            }
        }
    }
}
