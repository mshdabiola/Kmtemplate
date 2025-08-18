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
package com.mshdabiola.kmtemplate.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.WideNavigationRailState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberWideNavigationRailState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.main.navigation.navigateToMain
import com.mshdabiola.model.Notification
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
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
): KmtAppState {
    return remember(
        navController,
        windowSizeClass,
    ) {
        when {
            windowSizeClass.isWidthExpanded -> Expand(navController,snackbarHostState, coroutineScope)
            windowSizeClass.isWidthMedium -> Medium(navController,
                snackbarHostState,
                coroutineScope,
                wideNavigationRailState)
            else -> Compact(navController, snackbarHostState,coroutineScope, drawerState)
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Stable
sealed class KmtAppState(
    open val navController: NavHostController,
    open val snackbarHostState: SnackbarHostState,
    open val coroutineScope: CoroutineScope,
) {

    abstract val onDrawer: (() -> Unit)?

    open fun navigateTopRoute(any: Any) {
        when (any) {
            is Main -> navController.navigateToMain()
            is Setting -> navController.navigateToSetting()
            else -> {}
        }
    }

    fun isInCurrentRoute(any: Any): Boolean {
        return navController.currentDestination?.hasRoute(any::class) == true
    }

    fun onNotification(notification: Notification) {
        coroutineScope.launch {
            when (notification) {
                is Notification.Message ->
                    snackbarHostState.showSnackbar(notification.message)
                is Notification.MessageWithAction ->{
                    val result = snackbarHostState.showSnackbar(
                        message = notification.message,
                        actionLabel = notification.action,
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        notification.actionCallback()
                    }
                }

            }
        }

    }
}

data class Compact(
    override val navController: NavHostController,
    override val snackbarHostState: SnackbarHostState,
    override val coroutineScope: CoroutineScope,

    val drawerState: DrawerState,
) : KmtAppState(navController, snackbarHostState,coroutineScope) {

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

    override fun navigateTopRoute(any: Any) {
        super.navigateTopRoute(any)
        onDrawer?.invoke()
    }
}

data class Medium
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
constructor(
    override val navController: NavHostController,
    override val snackbarHostState: SnackbarHostState,
    override val coroutineScope: CoroutineScope,

    val wideNavigationRailState: WideNavigationRailState,
) : KmtAppState(navController, snackbarHostState,coroutineScope) {

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
    override val snackbarHostState: SnackbarHostState,
    override val coroutineScope: CoroutineScope,

    ) : KmtAppState(navController,snackbarHostState, coroutineScope) {

    override val onDrawer: (() -> Unit)?
        get() = null
}

@Stable
val WindowSizeClass.isWidthCompact: Boolean
    get() = minWidthDp < WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND

@Stable
inline val WindowSizeClass.isWidthMedium: Boolean
    get() = minWidthDp >= WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND &&
        minWidthDp < WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND

@Stable
inline val WindowSizeClass.isWidthExpanded: Boolean
    get() = minWidthDp >= WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND
