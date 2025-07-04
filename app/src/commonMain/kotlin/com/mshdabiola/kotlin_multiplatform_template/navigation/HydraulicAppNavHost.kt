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
package com.mshdabiola.kotlin_multiplatform_template.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mshdabiola.detail.navigation.Detail
import com.mshdabiola.detail.navigation.detailScreen
import com.mshdabiola.detail.navigation.navigateToDetail
import com.mshdabiola.kotlin_multiplatform_template.ui.HydraulicAppState
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.main.navigation.mainScreen
import com.mshdabiola.setting.navigation.settingScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HydraulicAppNavHost(
    appState: HydraulicAppState,
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
                sharedTransitionScope = this@SharedTransitionLayout,
                onShowSnack = onShowSnackbar,
                navigateToDetail = { navController.navigateToDetail(Detail(it)) },
            )
            detailScreen(
                modifier = Modifier,
                sharedTransitionScope = this@SharedTransitionLayout,
                onShowSnack = onShowSnackbar,
                onBack = navController::popBackStack,
            )
            settingScreen(
                modifier = Modifier,
                onShowSnack = onShowSnackbar,
                onBack = navController::popBackStack,
            )
        }
    }
}
