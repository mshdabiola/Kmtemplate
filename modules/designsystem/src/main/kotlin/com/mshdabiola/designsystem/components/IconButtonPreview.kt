/*
 *abiola 2022
 */

package com.mshdabiola.designsystem.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.component.HyaIconToggleButton
import com.mshdabiola.designsystem.icon.HyaIcons
import com.mshdabiola.designsystem.theme.HyaTheme

@ThemePreviews
@Composable
fun IconButtonPreview() {
    HyaTheme {
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

@ThemePreviews
@Composable
fun IconButtonPreviewUnchecked() {
    HyaTheme {
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
