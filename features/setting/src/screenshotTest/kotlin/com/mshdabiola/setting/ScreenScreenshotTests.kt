/*
 * Copyright (C) 2023-2025 MshdAbiola
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.DevicePreviews
import com.mshdabiola.designsystem.theme.darkDefaultScheme
import com.mshdabiola.designsystem.theme.lightDefaultScheme

class ScreenScreenshotTests {

    @DevicePreviews
    @Composable
    fun LoadingLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Text("Hello")
//            SettingScreen(
//                modifier = Modifier.fillMaxSize(),
//
//                settingState = SettingState.Loading(),
//            )
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @DevicePreviews
    @Composable
    fun LoadingDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {
                Text("Hello")

//                SettingScreen(
//                    modifier = Modifier.fillMaxSize(),
//                    settingState = SettingState.Loading(),
//                )
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @DevicePreviews
    @Composable
    fun MainLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {
                Text("Hello")

//                SettingScreen(
//                    modifier = Modifier.fillMaxSize(),
//
//                    settingState = SettingState.Success(
//                        themeBrand = ThemeBrand.DEFAULT,
//                        darkThemeConfig = DarkThemeConfig.DARK,
//                    ),
//                )
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @DevicePreviews
    @Composable
    fun MainDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {
                Text("Hello")

//                SettingScreen(
//                    modifier = Modifier.fillMaxSize(),
//                    settingState = SettingState.Success(
//                        themeBrand = ThemeBrand.DEFAULT,
//                        darkThemeConfig = DarkThemeConfig.DARK,
//                    ),
//                )
            }
        }
    }
}
