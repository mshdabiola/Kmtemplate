/*
 * Copyright (C) 2022-2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
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
