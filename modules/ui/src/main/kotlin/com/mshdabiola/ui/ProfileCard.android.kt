package com.mshdabiola.ui

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.theme.SkTheme

@DevicePreviews
@ThemePreviews
@Composable
actual fun ProfileCardPreview() {
    SkTheme {
        Surface {
            ProfileCard()
        }
    }
}
