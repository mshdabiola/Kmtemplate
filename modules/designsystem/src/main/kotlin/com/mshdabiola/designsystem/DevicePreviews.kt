/*
 *abiola 2024
 */

package com.mshdabiola.designsystem

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

/**
 * Multipreview annotation that represents various device sizes. Add this annotation to a composable
 * to render various devices.
 */
@Preview(
    group = "screen",
    name = "phone",
    device = "spec:width=411dp,height=891dp",
)
@Preview(
    group = "screen",
    name = "landscape",
    device = "spec:width=411dp,height=891dp,orientation=landscape",

)
@Preview(
    group = "screen",
    name = "foldable",
    device = "spec:width=673dp,height=841dp",

)
@Preview(
    group = "screen",
    name = "tablet",
    device = "spec:width=1280dp,height=800dp,dpi=240",
)
annotation class DevicePreviews

@Preview(name = "Dark Mode", group = "dark", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Light Mode", group = "dark", uiMode = UI_MODE_NIGHT_NO)
annotation class ThemePreviews
