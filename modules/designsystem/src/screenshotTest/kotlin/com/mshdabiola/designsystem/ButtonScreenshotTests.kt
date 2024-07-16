/*
 *abiola 2024
 */

package com.mshdabiola.designsystem

import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.component.SkButton
import com.mshdabiola.designsystem.icon.SkIcons

class ButtonScreenshotTests {

    @ThemePreviews
    @Composable
    fun Button() {
        Surface {
            SkButton(onClick = {}, text = { Text(" Button") })
        }
    }

    @ThemePreviews
    @Composable
    fun ButtonWithLeadIcon() {
        Surface {
            SkButton(
                onClick = {},
                text = { Text("Icon Button") },
                leadingIcon = { Icon(imageVector = SkIcons.Add, contentDescription = null) },
            )
        }
    }
}
