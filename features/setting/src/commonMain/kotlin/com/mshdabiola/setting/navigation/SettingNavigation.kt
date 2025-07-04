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

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.dialog
import com.mshdabiola.setting.SettingRoute
import com.mshdabiola.setting.SettingViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

fun NavController.navigateToSetting(navOptions: NavOptions = androidx.navigation.navOptions { }) =
    navigate(
        Setting,
        navOptions,
    )

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.settingScreen(
    modifier: Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
) {
    dialog<Setting> {
        val viewModel: SettingViewModel = koinViewModel()

        SettingRoute(
            modifier = modifier,
            onShowSnack = onShowSnack,
            viewModel = viewModel,
            onBack = onBack,
        )
    }
}
