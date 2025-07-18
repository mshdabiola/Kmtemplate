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
import com.mshdabiola.main.navigation.Main
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun rememberKotlinMultiplatformTemplateAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    wideNavigationRailState: WideNavigationRailState= rememberWideNavigationRailState(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed )
): KotlinMultiplatformTemplateAppState {
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
    ) {
        KotlinMultiplatformTemplateAppState(
            navController,
            coroutineScope,
            windowSizeClass,
            wideNavigationRailState,
            drawerState=drawerState
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Stable
class KotlinMultiplatformTemplateAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
    val wideNavigationRailState: WideNavigationRailState,
    val drawerState: DrawerState
) {
    val currentDestination: NavDestination?
        @Composable get() =
            navController
                .currentBackStackEntryAsState().value?.destination

    val isMain: Boolean
        @Composable get() =
            currentDestination?.hasRoute(Main::class) == true
    val isTopRoute
        @Composable get() = TOP_LEVEL_ROUTES.any { navController.currentDestination?.hasRoute(it.route::class)?:false }

    val showDrawer: Boolean
    @Composable get() = windowSizeClass.isWidthCompact && isTopRoute

    val showFab: Boolean
    @Composable get() = windowSizeClass.isWidthCompact && isMain

    val showRail: Boolean
    @Composable get() = (windowSizeClass.isWidthMedium || windowSizeClass.isWidthExpanded) && isTopRoute

    fun expand(){
        coroutineScope.launch {
            if (windowSizeClass.isWidthCompact){
                drawerState.open()
            }else{
                wideNavigationRailState.expand()
            }

        }
    }

    fun collapse(){
        coroutineScope.launch {
            if (windowSizeClass.isWidthCompact){
                drawerState.close()
            }else{
                wideNavigationRailState.collapse()
            }

        }
    }

    @Composable
    fun currentRoute(any: Any): Boolean{
       return currentDestination?.hasRoute(any::class)==true
    }




}

@Stable
val WindowSizeClass.isWidthCompact: Boolean
     get() = minWidthDp<WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND

@Stable
inline val WindowSizeClass.isWidthMedium: Boolean
    get() = minWidthDp>=WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND && minWidthDp<WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND

@Stable
inline val WindowSizeClass.isWidthExpanded: Boolean
    get() = minWidthDp>=WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND
