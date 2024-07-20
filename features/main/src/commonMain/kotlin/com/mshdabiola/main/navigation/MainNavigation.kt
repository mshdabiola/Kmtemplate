/*
 *abiola 2022
 */

package com.mshdabiola.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.main.MainRoute
import com.mshdabiola.model.naviagation.Detail
import com.mshdabiola.model.naviagation.Main

fun NavController.navigateToMain(main: Main, navOptions: NavOptions) = navigate(main, navOptions)

fun NavGraphBuilder.mainScreen(
    onShowSnack: suspend (String, String?) -> Boolean,
    navigateToSetting: () -> Unit,
    navigateToDetail: (Detail) -> Unit,
) {
    composable<Main> {
        MainRoute(
            onShowSnackbar = onShowSnack,
            navigateToSetting = navigateToSetting,
            navigateToDetail = navigateToDetail,
        )
    }
}
