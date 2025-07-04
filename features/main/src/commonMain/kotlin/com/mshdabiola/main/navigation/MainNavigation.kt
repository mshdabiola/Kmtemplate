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
package com.mshdabiola.main.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.main.MainRoute

fun NavController.navigateToMain(
    main: Main,
    navOptions: NavOptions,
) = navigate(main, navOptions)

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.mainScreen(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    onShowSnack: suspend (String, String?) -> Boolean,
    navigateToDetail: (Long) -> Unit,
) {
    composable<Main> {
        MainRoute(
            modifier = modifier,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this,
            showSnackbar = onShowSnack,
            navigateToDetail = navigateToDetail,
        )
    }
}
