/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.SkBackground
import com.mshdabiola.designsystem.component.SkFilterChip
import com.mshdabiola.designsystem.theme.SkTheme

class FilterChipScreenshotTests {

    @ThemePreviews
    @Composable
    fun UnSelectedFilterChip() {
        Surface {
            SkFilterChip(selected = false, onSelectedChange = {}) {
                Text("Unselected chip")
            }

        }
    }

    @ThemePreviews
    @Composable
    fun SelectedFilterChip() {
        Surface {
            SkFilterChip(selected = true, onSelectedChange = {}) {
                Text("Selected Chip")
            }
        }

    }

    @ThemePreviews
    @Preview(fontScale = 2.0f)
    @Composable
    fun HugeFontFilterChip() {

        SkTheme {
            SkBackground {
                SkFilterChip(selected = true, onSelectedChange = {}) {
                    Text("Chip")
                }
            }
        }

    }

}
