/*
 *abiola 2022
 */

package com.mshdabiola.detail.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mshdabiola.detail.DetailRoute
import com.mshdabiola.detail.DetailViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parameterSetOf

fun NavController.navigateToDetail(detail: Detail) {
    // val encodedId = URLEncoder.encode(topicId, URL_CHARACTER_ENCODING)
    navigate(detail) {
        launchSingleTop = true
    }
}

@OptIn(KoinExperimentalAPI::class, ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.detailScreen(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    onShowSnack: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
) {
    composable<Detail> { backStack ->

        val detail: Detail = backStack.toRoute()

        val viewModel: DetailViewModel =
            koinViewModel(
                parameters = {
                    parameterSetOf(
                        detail.id,
                    )
                },
            )

        DetailRoute(
            modifier = modifier,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this,
            onShowSnackbar = onShowSnack,
            onBack = onBack,
            viewModel = viewModel,
        )
    }
}
