/*
 *abiola 2022
 */

package com.mshdabiola.designsystem.component

import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mshdabiola.designsystem.icon.HyaIcons
import com.mshdabiola.designsystem.theme.HyaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Now in Android toggle button with icon and checked icon content slots. Wraps Material 3
 * [IconButton].
 *
 * @param checked Whether the toggle button is currently checked.
 * @param onCheckedChange Called when the user clicks the toggle button and toggles checked.
 * @param modifier Modifier to be applied to the toggle button.
 * @param enabled Controls the enabled state of the toggle button. When `false`, this toggle button
 * will not be clickable and will appear disabled to accessibility services.
 * @param icon The icon content to show when unchecked.
 * @param checkedIcon The icon content to show when checked.
 */
@Composable
fun HyaIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable () -> Unit,
    checkedIcon: @Composable () -> Unit = icon,
) {
    //  File bug
    // Can't use regular IconToggleButton as it doesn't include a shape (appears square)
    FilledIconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors =
            IconButtonDefaults.iconToggleButtonColors(
                checkedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                checkedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                disabledContainerColor =
                    if (checked) {
                        MaterialTheme.colorScheme.onBackground.copy(
                            alpha = HyaIconButtonDefaults.DISABLED_ICON_BUTTON_CONTAINER_ALPHA,
                        )
                    } else {
                        Color.Transparent
                    },
            ),
    ) {
        if (checked) checkedIcon() else icon()
    }
}

@Preview
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

@Preview
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

/**
 * Now in Android icon button default values.
 */
object HyaIconButtonDefaults {
    //  File bug
    // IconToggleButton disabled container alpha not exposed by IconButtonDefaults
    const val DISABLED_ICON_BUTTON_CONTAINER_ALPHA = 0.12f
}
