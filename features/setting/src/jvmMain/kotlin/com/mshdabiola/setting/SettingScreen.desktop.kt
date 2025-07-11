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
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.model.DarkThemeConfig
import kotlinmultiplatformtemplate.features.setting.generated.resources.Res
import kotlinmultiplatformtemplate.features.setting.generated.resources.theme
import org.jetbrains.compose.resources.stringArrayResource

@Preview
@Composable
fun DialogPreview() {
    OptionsDialog(
        modifier = Modifier,
        options = stringArrayResource(Res.array.theme),
        current = 1,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun ScreenPreview() {
    SettingScreen(
        modifier = Modifier,
        settingState =
        SettingState.Success(
            contrast = 0,
            darkThemeConfig = DarkThemeConfig.DARK,
        ),
    )
}
