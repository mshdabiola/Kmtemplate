/*
 *abiola 2022
 */

package com.mshdabiola.skeletonapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.mshdabiola.detail.navigation.Detail
import com.mshdabiola.main.navigation.Main
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

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
    val currentRoute: String
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route ?: ""

    val isMain: Boolean
        @Composable get() = currentRoute.contains(Main::class.name)

    val shouldShowTopBar: Boolean
        @Composable get() = currentRoute.contains(Detail::class.name).not()
    val shouldShowBottomBar: Boolean
        @Composable get() = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT && isMain

    val shouldShowNavRail: Boolean
        @Composable get() = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.MEDIUM && isMain

    val shouldShowDrawer: Boolean
        @Composable get() = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED && isMain
}

val <T : Any> KClass<T>.name: String
    get() {
        return this.qualifiedName.toString()
    }
