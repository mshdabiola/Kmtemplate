/*
 *abiola 2022
 */

package com.mshdabiola.skeletonapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mshdabiola.detail.navigation.detailScreen
import com.mshdabiola.detail.navigation.navigateToDetail
import com.mshdabiola.main.navigation.MAIN_ROUTE
import com.mshdabiola.main.navigation.mainScreen
import com.mshdabiola.setting.navigation.navigateToSetting
import com.mshdabiola.setting.navigation.settingScreen
import com.mshdabiola.skeletonapp.ui.SkAppState

@Composable
fun SkNavHost(
    appState: SkAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    modifier: Modifier = Modifier,
    startDestination: String = MAIN_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        mainScreen(
            screenSize = appState.screenSize,
            onShowSnack = onShowSnackbar,
            onClicked = navController::navigateToDetail,
            navigateToSetting = navController::navigateToSetting,
            navigateToDetail = navController::navigateToDetail,
        )
        detailScreen(
            screenSize = appState.screenSize,
            onShowSnack = onShowSnackbar,
            onBack = navController::popBackStack,
        )
        settingScreen(
            screenSize = appState.screenSize,
            onShowSnack = onShowSnackbar,
            onBack = navController::popBackStack,
        )
    }
}
