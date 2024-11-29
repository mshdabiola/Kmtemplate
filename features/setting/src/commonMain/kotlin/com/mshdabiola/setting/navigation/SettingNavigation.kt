/*
 *abiola 2022
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

fun NavController.navigateToSetting(navOptions: NavOptions = androidx.navigation.navOptions { }) = navigate(
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
