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
package com.mshdabiola.kotlinmultiplatformtemplate.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mshdabiola.detail.navigation.Detail
import com.mshdabiola.detail.navigation.detailScreen
import com.mshdabiola.detail.navigation.navigateToDetail
import com.mshdabiola.kotlinmultiplatformtemplate.ui.KotlinMultiplatformTemplateAppState
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.main.navigation.mainScreen
import com.mshdabiola.setting.navigation.settingScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun KotlinMultiplatformTemplateAppNavHost(
    appState: KotlinMultiplatformTemplateAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    SharedTransitionLayout(modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = Main,
        ) {
            mainScreen(
                modifier = Modifier,
                navigateToDetail = { navController.navigateToDetail(Detail(it)) },
            )
            detailScreen(
                modifier = Modifier,
                onBack = navController::popBackStack,
            )
            settingScreen(
                modifier = Modifier,
                onBack = navController::popBackStack,
            )
        }
    }
}
