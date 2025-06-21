/*
 * Copyright (C) 2024-2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package com.mshdabiola.ui

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
