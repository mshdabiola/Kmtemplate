/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
