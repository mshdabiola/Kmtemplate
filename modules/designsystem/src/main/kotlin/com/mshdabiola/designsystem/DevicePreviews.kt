/*
 *abiola 2024
 */

package com.mshdabiola.designsystem

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.theme.SkTheme

/**
 * Multipreview annotation that represents various device sizes. Add this annotation to a composable
 * to render various devices.
 */
@Preview(
    group = "screen",
    name = "phone",
    device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480",
    showBackground = true,
)
@Preview(
    group = "screen",
    name = "landscape",
    device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480",
)
@Preview(
    group = "screen",
    name = "foldable",
    device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480",
)
@Preview(
    group = "screen",
    name = "tablet",
    device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480",
)
annotation class DevicePreviews

@Preview(name = "Dark Mode", group = "dark", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Light Mode", group = "dark", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
annotation class ThemePreviews

@Composable
fun MultiLine(dynamicTheme: Boolean = true, content: @Composable () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
    ) {
        SkTheme(androidTheme = true, disableDynamicTheming = true) {
            content()
        }

        SkTheme(androidTheme = true, disableDynamicTheming = true) {
            content()
        }
    }
}
