/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.detail.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mshdabiola.detail.DetailScreen
import com.mshdabiola.detail.DetailViewModel
import com.mshdabiola.model.Notification
import com.mshdabiola.model.SnackbarDuration
import com.mshdabiola.model.Type
import com.mshdabiola.ui.LocalNavAnimatedContentScope
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
    onBack: () -> Unit,
    setNotification: (Notification)-> Unit
) {
    composable<Detail> { backStack ->

        val detail: Detail = backStack.toRoute()

//        val viewModel= koinViewModel<DetailViewModel>{ parametersOf(id)}
        val viewModel: DetailViewModel =
            koinViewModel(
                parameters = {
                    parameterSetOf(
                        detail.id,
                    )
                },
            )
        val detailState = viewModel.detailState.collectAsStateWithLifecycle()
        CompositionLocalProvider(
            LocalNavAnimatedContentScope provides this,
        ) {
            DetailScreen(
                modifier = modifier,
                state = detailState.value,
                detail = detail,
                onBack = onBack,
                onDelete = {
                    setNotification(
                        Notification.MessageWithAction(
                            type = Type.Warning,
                            duration = SnackbarDuration.Indefinite,
                            message = "Are you sure you want to delete this note?",
                            action = "Delete",
                            actionCallback = {
                                viewModel.onDelete()
                                onBack()
                            }
                        )
                    )
                },

            )
        }
    }
}
