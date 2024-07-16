/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.component.SkIconToggleButton
import com.mshdabiola.designsystem.icon.SkIcons

class IconButtonScreenshotTests {

    @ThemePreviews
    @Composable
    fun ToggleButton() {
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

    @ThemePreviews
    @Composable
    fun UnToggleButton() {
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
