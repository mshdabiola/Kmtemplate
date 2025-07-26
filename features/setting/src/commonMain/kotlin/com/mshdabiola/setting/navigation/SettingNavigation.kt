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
package com.mshdabiola.setting.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.mshdabiola.setting.SettingScreen
import com.mshdabiola.setting.SettingViewModel
import com.mshdabiola.ui.LocalNavAnimatedContentScope
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

fun NavController.navigateToSetting(
    navOptions: NavOptions = navOptions { launchSingleTop = true },
) =
    navigate(
        Setting,
        navOptions,
    )

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.settingScreen(
    modifier: Modifier,
    onDrawer: (() -> Unit)?,
) {
    composable<Setting> {
        val viewModel: SettingViewModel = koinViewModel()
        val settingState = viewModel.settingState.collectAsStateWithLifecycle()

        CompositionLocalProvider(
            LocalNavAnimatedContentScope provides this,
        ) {
            SettingScreen(
//                settingState = settingState.value,
                modifier = modifier,
//                setContrast = { viewModel.setContrast(it) },
//                onDarkClick = { showDarkThemeConfig = true },
                onDrawer = onDrawer,
                settingState = settingState.value,
                onContrastChange = { viewModel.setContrast(it) },
                onDarkModeChange = { viewModel.setDarkThemeConfig(it) },
            )
        }
    }
}
