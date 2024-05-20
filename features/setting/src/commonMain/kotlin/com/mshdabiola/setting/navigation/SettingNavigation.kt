/*
 *abiola 2022
 */

package com.mshdabiola.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mshdabiola.designsystem.icon.settingRoute
import com.mshdabiola.setting.SettingRoute
import com.mshdabiola.setting.SettingViewModel
import com.mshdabiola.ui.ScreenSize
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

val SETTING_ROUTE = settingRoute[0]

fun NavController.navigateToSetting() = navigate(SETTING_ROUTE)

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.settingScreen(
    screenSize: ScreenSize,
    onShowSnack: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
) {
    composable(route = SETTING_ROUTE) {
        val viewModel: SettingViewModel = koinViewModel()

        SettingRoute(
            screenSize = screenSize,
            onBack = onBack,
            onShowSnack = onShowSnack,
            viewModel = viewModel,
        )
    }
}
