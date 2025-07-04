/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.testing.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.theme.KmtTheme

/**
 * Takes six screenshots combining light/dark and default/Android themes and whether dynamic color
 * is enabled.
 */
@Composable
fun CaptureMultiTheme(
    shouldCompareDarkMode: Boolean = true,
    shouldCompareDynamicColor: Boolean = true,
    shouldCompareAndroidTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    val darkModeValues = if (shouldCompareDarkMode) listOf(true, false) else listOf(false)
    val dynamicThemingValues = if (shouldCompareDynamicColor) listOf(true, false) else listOf(false)
    val androidThemeValues = if (shouldCompareAndroidTheme) listOf(true, false) else listOf(false)

    var darkMode by mutableStateOf(true)
    var dynamicTheming by mutableStateOf(false)
    var androidTheme by mutableStateOf(false)
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        darkModeValues.forEach { isDarkMode ->
            darkMode = isDarkMode

            androidThemeValues.forEach { isAndroidTheme ->
                androidTheme = isAndroidTheme

                dynamicThemingValues.forEach dynamicTheme@{ isDynamicTheming ->
                    // Skip tests with both Android Theme and Dynamic color as they're incompatible.
                    if (isAndroidTheme && isDynamicTheming) return@dynamicTheme

                    dynamicTheming = isDynamicTheming

                    val description =
                        generateDescription(
                            shouldCompareDarkMode = shouldCompareDarkMode,
                            darkMode = darkMode,
                            shouldCompareAndroidTheme = shouldCompareAndroidTheme,
                            androidTheme = androidTheme,
                            shouldCompareDynamicColor = shouldCompareDynamicColor,
                            dynamicTheming = dynamicTheming,
                        )

                    Capture(
                        darkMode = darkMode,
                        androidTheme = androidTheme,
                        dynamicTheming = dynamicTheming,
                        description = description,
                        content = content,
                    )
                }
            }
        }
    }
}

@Composable
fun Capture(
    darkMode: Boolean = false,
    androidTheme: Boolean = false,
    dynamicTheming: Boolean = false,
    description: String = "",
    content: @Composable () -> Unit,
) {
    KmtTheme(
        androidTheme = androidTheme,
        darkTheme = darkMode,
        disableDynamicTheming = !dynamicTheming,
    ) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(description)
                Spacer(modifier = Modifier.height(8.dp))

                content()
            }
        }
    }
}

@Composable
private fun generateDescription(
    shouldCompareDarkMode: Boolean,
    darkMode: Boolean,
    shouldCompareAndroidTheme: Boolean,
    androidTheme: Boolean,
    shouldCompareDynamicColor: Boolean,
    dynamicTheming: Boolean,
): String {
    val description =
        "" +
            if (shouldCompareDarkMode) {
                if (darkMode) "Dark" else "Light"
            } else {
                ""
            } +
            if (shouldCompareAndroidTheme) {
                if (androidTheme) " Android" else " Default"
            } else {
                ""
            } +
            if (shouldCompareDynamicColor) {
                if (dynamicTheming) " Dynamic" else ""
            } else {
                ""
            }

    return description.trim()
}
//
// /**
// * Extracts some properties from the spec string. Note that this function is not exhaustive.
// */
// private fun extractSpecs(deviceSpec: String): TestDeviceSpecs {
//    val specs =
//        deviceSpec.substringAfter("spec:")
//            .split(",").map { it.split("=") }.associate { it[0] to it[1] }
//    val width = specs["width"]?.toInt() ?: 640
//    val height = specs["height"]?.toInt() ?: 480
//    val dpi = specs["dpi"]?.toInt() ?: 480
//    return TestDeviceSpecs(width, height, dpi)
// }

data class TestDeviceSpecs(val width: Int, val height: Int, val dpi: Int)
