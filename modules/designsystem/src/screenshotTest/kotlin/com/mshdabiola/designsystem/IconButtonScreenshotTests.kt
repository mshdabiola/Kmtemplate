/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.SkIconToggleButton
import com.mshdabiola.designsystem.icon.SkIcons
import com.mshdabiola.testing.util.CaptureMultiTheme

class IconButtonScreenshotTests {

    @Preview
    @Composable
    fun ToggleButton() {
        CaptureMultiTheme {
            SkIconToggleButton(
                checked = true,
                onCheckedChange = { },
                icon = {
                    Icon(
                        imageVector = SkIcons.BookmarkBorder,
                        contentDescription = null,
                    )
                },
                checkedIcon = {
                    Icon(
                        imageVector = SkIcons.Bookmark,
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
            SkIconToggleButton(
                checked = false,
                onCheckedChange = { },
                icon = {
                    Icon(
                        imageVector = SkIcons.BookmarkBorder,
                        contentDescription = null,
                    )
                },
                checkedIcon = {
                    Icon(
                        imageVector = SkIcons.Bookmark,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}
