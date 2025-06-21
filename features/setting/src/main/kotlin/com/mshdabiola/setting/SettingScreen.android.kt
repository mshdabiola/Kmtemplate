/*
 * Copyright (C) 2025 MshdAbiola
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
package com.mshdabiola.setting

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun ScreenPreview() {
    SettingScreen(
        settingState =
        SettingState.Success(
            themeBrand = ThemeBrand.DEFAULT,
            darkThemeConfig = DarkThemeConfig.DARK,
        ),
    )
}
