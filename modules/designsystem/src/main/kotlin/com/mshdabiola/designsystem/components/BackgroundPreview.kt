/*
 *abiola 2024
 */

package com.mshdabiola.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.HyaBackground
import com.mshdabiola.designsystem.component.HyaGradientBackground
import com.mshdabiola.designsystem.theme.HyaTheme

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
annotation class ThemePreviews

@ThemePreviews
@Composable
fun BackgroundDefault() {
    HyaTheme(disableDynamicTheming = true) {
        HyaBackground(Modifier.size(100.dp), content = {})
    }
}

@ThemePreviews
@Composable
fun BackgroundDynamic() {
    HyaTheme(disableDynamicTheming = false) {
        HyaBackground(Modifier.size(100.dp), content = {})
    }
}

@ThemePreviews
@Composable
fun BackgroundAndroid() {
    HyaTheme {
        HyaBackground(Modifier.size(100.dp), content = {})
    }
}

@ThemePreviews
@Composable
fun GradientBackgroundDefault() {
    HyaTheme(disableDynamicTheming = true) {
        HyaGradientBackground(Modifier.size(100.dp), content = {})
    }
}

@ThemePreviews
@Composable
fun GradientBackgroundDynamic() {
    HyaTheme(disableDynamicTheming = false) {
        HyaGradientBackground(Modifier.size(100.dp), content = {})
    }
}

@ThemePreviews
@Composable
fun GradientBackgroundAndroid() {
    HyaTheme {
        HyaGradientBackground(Modifier.size(100.dp), content = {})
    }
}
