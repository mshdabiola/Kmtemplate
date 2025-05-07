/*
 *abiola 2022
 */

package com.mshdabiola.skeletonapp.ui

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
fun rememberSkAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): SkAppState {
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
    ) {
        SkAppState(
            navController,
            coroutineScope,
            windowSizeClass,
        )
    }
}

@Stable
class SkAppState(
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
