/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.component.SkLoadingWheel
import com.mshdabiola.designsystem.component.SkOverlayLoadingWheel

class LoadingWheelScreenshotTests {


    @ThemePreviews
    @Composable
    fun LoadingWheel() {

        Surface {
            SkLoadingWheel(contentDesc = "test")
        }

    }


    @ThemePreviews
    @Composable
    fun OverlayLoadingWheel() {
        Surface {
            SkOverlayLoadingWheel(contentDesc = "test")
        }

    }

}
