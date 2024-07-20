/*
 *abiola 2022
 */

package com.mshdabiola.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.model.naviagation.Setting
import com.mshdabiola.setting.SettingRoute
import com.mshdabiola.setting.SettingViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

fun NavController.navigateToSetting(navOptions: NavOptions = androidx.navigation.navOptions { }) = navigate(Setting, navOptions)

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.settingScreen(
    onShowSnack: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
) {
    composable<Setting> {
        val viewModel: SettingViewModel = koinViewModel()

        SettingRoute(
            onBack = onBack,
            onShowSnack = onShowSnack,
            viewModel = viewModel,
        )
    }
}
