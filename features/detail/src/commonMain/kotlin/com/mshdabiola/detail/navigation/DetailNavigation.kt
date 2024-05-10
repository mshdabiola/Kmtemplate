/*
 *abiola 2022
 */

package com.mshdabiola.detail.navigation

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.designsystem.icon.mainRoute
import com.mshdabiola.detail.DetailRoute
import com.mshdabiola.detail.DetailViewModel
import com.mshdabiola.mvvn.KoinCommonViewModel
import com.mshdabiola.ui.ScreenSize
import org.koin.core.parameter.parameterSetOf

val DETAIL_ROUTE = mainRoute[1]

private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

@VisibleForTesting
internal const val DETAIL_ID_ARG = "topicId"

internal class DetailArgs(val id: Long) {
    constructor(savedStateHandle: SavedStateHandle) : this(checkNotNull<Long>(savedStateHandle[DETAIL_ID_ARG]))
    // this(URLDecoder.decode(checkNotNull(savedStateHandle[DETAIL_ID_ARG]), URL_CHARACTER_ENCODING))
}

fun NavController.navigateToDetail(topicId: Long) {
    // val encodedId = URLEncoder.encode(topicId, URL_CHARACTER_ENCODING)
    navigate("$DETAIL_ROUTE/$topicId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.detailScreen(
    screenSize: ScreenSize,
    onShowSnack: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
) {
    composable(
        route = "$DETAIL_ROUTE/{$DETAIL_ID_ARG}",
        arguments = listOf(
            navArgument(DETAIL_ID_ARG) {
                type = NavType.LongType
            },
        ),
        enterTransition = {
            slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left)
        },
        exitTransition = {
            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right)
        },
    ) { backStack ->
        val id = backStack.arguments?.getLong(DETAIL_ID_ARG)

        val viewModel: DetailViewModel = KoinCommonViewModel(
            parameters = {
                parameterSetOf(
                    id,
                )
            },
        )

        DetailRoute(
            screenSize = screenSize,
            onShowSnackbar = onShowSnack,
            onBack = onBack,
            viewModel = viewModel,
        )
    }
}
