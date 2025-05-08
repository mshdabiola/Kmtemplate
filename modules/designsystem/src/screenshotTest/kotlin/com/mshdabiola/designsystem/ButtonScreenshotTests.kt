/*
 *abiola 2024
 */

package com.mshdabiola.designsystem

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.HyaButton
import com.mshdabiola.designsystem.icon.HyaIcons
import com.mshdabiola.testing.util.CaptureMultiTheme

class ButtonScreenshotTests {

    @Preview
    @Composable
    fun Button() {
        CaptureMultiTheme {
            HyaButton(onClick = {}, text = { Text(" Button") })
        }
    }

    @Preview
    @Composable
    fun ButtonWithLeadIcon() {
        CaptureMultiTheme {
            HyaButton(
                onClick = {},
                text = { Text("Icon Button") },
                leadingIcon = { Icon(imageVector = HyaIcons.Add, contentDescription = null) },
            )
        }
    }
}
