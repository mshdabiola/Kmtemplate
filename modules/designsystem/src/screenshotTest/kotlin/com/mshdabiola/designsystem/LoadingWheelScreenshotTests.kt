/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.HyaLoadingWheel
import com.mshdabiola.designsystem.component.HyaOverlayLoadingWheel
import com.mshdabiola.testing.util.CaptureMultiTheme

class LoadingWheelScreenshotTests {

    @Preview
    @Composable
    fun LoadingWheel() {
        CaptureMultiTheme {
            HyaLoadingWheel(contentDesc = "test")
        }
    }

    @Preview
    @Composable
    fun OverlayLoadingWheel() {
        CaptureMultiTheme {
            HyaOverlayLoadingWheel(contentDesc = "test")
        }
    }
}
