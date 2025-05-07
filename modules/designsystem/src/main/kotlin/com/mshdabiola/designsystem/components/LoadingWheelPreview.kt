/*
 *abiola 2022
 */

package com.mshdabiola.designsystem.components

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.component.HyaLoadingWheel
import com.mshdabiola.designsystem.component.HyaOverlayLoadingWheel
import com.mshdabiola.designsystem.theme.HyaTheme

@ThemePreviews
@Composable
fun NiaLoadingWheelPreview() {
    HyaTheme {
        Surface {
            HyaLoadingWheel(contentDesc = "LoadingWheel")
        }
    }
}

@ThemePreviews
@Composable
fun NiaOverlayLoadingWheelPreview() {
    HyaTheme {
        Surface {
            HyaOverlayLoadingWheel(contentDesc = "LoadingWheel")
        }
    }
}

private const val ROTATION_TIME = 12000
private const val NUM_OF_LINES = 12
