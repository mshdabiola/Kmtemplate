/*
 *abiola 2022
 */

package com.mshdabiola.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.designsystem.icon.mainRoute
import com.mshdabiola.main.MainRoute
import com.mshdabiola.ui.ScreenSize

val MAIN_ROUTE = mainRoute[0]

fun NavController.navigateToMain(navOptions: NavOptions) = navigate(MAIN_ROUTE, navOptions)

fun NavGraphBuilder.mainScreen(
    onShowSnack: suspend (String, String?) -> Boolean,
    onClicked: (Long) -> Unit,
    navigateToSetting: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    screenSize: ScreenSize,
) {
    composable(route = MAIN_ROUTE) {
        MainRoute(
            screenSize = screenSize,
            onClicked = onClicked,
            onShowSnackbar = onShowSnack,
            navigateToSetting = navigateToSetting,
            navigateToDetail = navigateToDetail,
        )
    }
}
