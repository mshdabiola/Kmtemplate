/*
 *abiola 2022
 */

package com.mshdabiola.skeletonapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mshdabiola.detail.navigation.detailScreen
import com.mshdabiola.detail.navigation.navigateToDetail
import com.mshdabiola.main.navigation.mainScreen
import com.mshdabiola.model.naviagation.Main
import com.mshdabiola.setting.navigation.navigateToSetting
import com.mshdabiola.setting.navigation.settingScreen
import com.mshdabiola.skeletonapp.ui.SkAppState

@Composable
fun SkNavHost(
    appState: SkAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = Main,
        modifier = modifier,
    ) {
        mainScreen(
            onShowSnack = onShowSnackbar,
            navigateToSetting = navController::navigateToSetting,
            navigateToDetail = navController::navigateToDetail,
        )
        detailScreen(
            onShowSnack = onShowSnackbar,
            onBack = navController::popBackStack,
        )
        settingScreen(
            onShowSnack = onShowSnackbar,
            onBack = navController::popBackStack,
        )
    }
}
