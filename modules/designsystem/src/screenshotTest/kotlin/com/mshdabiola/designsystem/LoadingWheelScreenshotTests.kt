/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.SkLoadingWheel
import com.mshdabiola.designsystem.component.SkOverlayLoadingWheel
import com.mshdabiola.testing.util.CaptureMultiTheme

class LoadingWheelScreenshotTests {

    @Preview
    @Composable
    fun LoadingWheel() {
        CaptureMultiTheme {
            SkLoadingWheel(contentDesc = "test")
        }
    }

    @Preview
    @Composable
    fun OverlayLoadingWheel() {
        CaptureMultiTheme {
            SkOverlayLoadingWheel(contentDesc = "test")
        }
    }
}
