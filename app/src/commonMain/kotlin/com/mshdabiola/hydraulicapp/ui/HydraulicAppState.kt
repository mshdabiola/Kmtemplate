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
package com.mshdabiola.hydraulicapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.mshdabiola.detail.navigation.Detail
import com.mshdabiola.main.navigation.Main
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberHydraulicAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): HydraulicAppState {
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
    ) {
        HydraulicAppState(
            navController,
            coroutineScope,
            windowSizeClass,
        )
    }
}

@Stable
class HydraulicAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
) {
    val currentDestination: NavDestination?
        @Composable get() =
            navController
                .currentBackStackEntryAsState().value?.destination
//    val currentRoute: String
//        @Composable get() = navController
//            .currentBackStackEntryAsState().value?.destination?.route ?: ""

    val isMain: Boolean
        @Composable get() =
            currentDestination?.hasRoute(Main::class) == true

    val shouldShowTopBar: Boolean
        @Composable get() = currentDestination?.hasRoute(Detail::class) != true
    val shouldShowBottomBar: Boolean
        @Composable get() = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT && isMain

    val shouldShowNavRail: Boolean
        @Composable get() = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.MEDIUM && isMain

    val shouldShowDrawer: Boolean
        @Composable get() = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED && isMain
}
