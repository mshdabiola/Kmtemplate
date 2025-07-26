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
package com.mshdabiola.kotlinmultiplatformtemplate.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.WideNavigationRailState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberWideNavigationRailState
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
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.main.navigation.navigateToMain
import com.mshdabiola.setting.navigation.Setting
import com.mshdabiola.setting.navigation.navigateToSetting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun rememberKmtAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    wideNavigationRailState: WideNavigationRailState = rememberWideNavigationRailState(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
): KmtAppState {
    return remember(
        navController,
        windowSizeClass,
    ) {
        when {
            windowSizeClass.isWidthCompact -> Compact(navController, coroutineScope, drawerState)
            windowSizeClass.isWidthMedium -> Medium(navController, coroutineScope, wideNavigationRailState)
            else -> Expand(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Stable
sealed class KmtAppState(
    open val navController: NavHostController,
) {

    abstract val onDrawer: (() -> Unit)?

    val currentDestination: NavDestination?
        @Composable get() =
            navController
                .currentBackStackEntryAsState().value?.destination

    val isMain: Boolean
        @Composable get() =
            currentDestination?.hasRoute(Main::class) == true

    val isTopRoute
        @Composable get() = remember(currentDestination) {
            TOP_LEVEL_ROUTES.any {
                TOP_LEVEL_ROUTES.any {
                    navController.currentDestination?.hasRoute(it.route::class)
                        ?: false
                }
            }
        }

    fun navigateTopRoute(any: Any) {
        when (any) {
            is Main -> navController.navigateToMain()
            is Setting -> navController.navigateToSetting()
            else -> {}
        }
    }

    @Composable
    fun isInCurrentRoute(any: Any): Boolean {
        return currentDestination?.hasRoute(any::class) == true
    }
}

data class Compact(
    override val navController: NavHostController,
    val coroutineScope: CoroutineScope,

    val drawerState: DrawerState,
) : KmtAppState(navController) {

    override val onDrawer: (() -> Unit)?
        get() = {
            if (drawerState.isOpen) {
                coroutineScope.launch {
                    drawerState.close()
                }
            } else {
                coroutineScope.launch {
                    drawerState.open()
                }
            }
        }
}

data class Medium
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
constructor(
    override val navController: NavHostController,
    val coroutineScope: CoroutineScope,

    val wideNavigationRailState: WideNavigationRailState,
) : KmtAppState(navController) {

    override val onDrawer: (() -> Unit)?
        get() = null

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    fun expand() {
        coroutineScope.launch {
            wideNavigationRailState.expand()
        }
    }

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    fun collapse() {
        coroutineScope.launch {
            wideNavigationRailState.collapse()
        }
    }
}

data class Expand(
    override val navController: NavHostController,
) : KmtAppState(navController) {

    override val onDrawer: (() -> Unit)?
        get() = null
}

@Stable
val WindowSizeClass.isWidthCompact: Boolean
    get() = windowWidthSizeClass == WindowWidthSizeClass.COMPACT

@Stable
inline val WindowSizeClass.isWidthMedium: Boolean
    get() = windowWidthSizeClass == WindowWidthSizeClass.MEDIUM

@Stable
inline val WindowSizeClass.isWidthExpanded: Boolean
    get() = windowWidthSizeClass == WindowWidthSizeClass.EXPANDED
