package com.mshdabiola.ui

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.theme.HyaTheme

@DevicePreviews
@ThemePreviews
@Composable
fun ProfileCardPreview() {
    HyaTheme {
        Surface {
            ProfileCard()
        }
    }
}
