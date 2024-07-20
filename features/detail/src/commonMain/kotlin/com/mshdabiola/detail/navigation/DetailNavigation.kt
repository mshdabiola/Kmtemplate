/*
 *abiola 2022
 */

package com.mshdabiola.detail.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mshdabiola.detail.DetailRoute
import com.mshdabiola.detail.DetailViewModel
import com.mshdabiola.model.naviagation.Detail
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parameterSetOf

fun NavController.navigateToDetail(detail: Detail) {
    // val encodedId = URLEncoder.encode(topicId, URL_CHARACTER_ENCODING)
    navigate(detail) {
        launchSingleTop = true
    }
}

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.detailScreen(
    onShowSnack: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
) {
    composable<Detail>(
        enterTransition = {
            slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left)
        },
        exitTransition = {
            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right)
        },
    ) { backStack ->
        val detail: Detail = backStack.toRoute()

        val viewModel: DetailViewModel = koinViewModel(
            parameters = {
                parameterSetOf(
                    detail.id,
                )
            },
        )

        DetailRoute(
            onShowSnackbar = onShowSnack,
            onBack = onBack,
            viewModel = viewModel,
        )
    }
}
