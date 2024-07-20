/*
 *abiola 2022
 */

package com.mshdabiola.setting.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.ui.Modifier
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

@OptIn(KoinExperimentalAPI::class, ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.settingScreen(
    modifier: Modifier ,
    sharedTransitionScope: SharedTransitionScope,
    onShowSnack: suspend (String, String?) -> Boolean,
) {
    composable<Setting> {
        val viewModel: SettingViewModel = koinViewModel()

        SettingRoute(
            modifier = modifier,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this,
            onShowSnack = onShowSnack,
            viewModel = viewModel,
        )
    }
}
