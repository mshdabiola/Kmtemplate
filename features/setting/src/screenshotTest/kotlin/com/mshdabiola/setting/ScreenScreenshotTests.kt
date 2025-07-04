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
