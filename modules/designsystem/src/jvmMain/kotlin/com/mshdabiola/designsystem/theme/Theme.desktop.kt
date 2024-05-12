package com.mshdabiola.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

actual fun supportsDynamicTheming(): Boolean {
    return false
}

@Composable
actual fun getDynamicColor(darkTheme: Boolean): ColorScheme {
    return darkColorScheme()
}
