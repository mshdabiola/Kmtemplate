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

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SmallExtendedFloatingActionButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.WideNavigationRail
import androidx.compose.material3.WideNavigationRailItem
import androidx.compose.material3.WideNavigationRailValue
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberWideNavigationRailState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.mshdabiola.designsystem.icon.KmtIcons
import com.mshdabiola.detail.navigation.Detail
import com.mshdabiola.detail.navigation.navigateToDetail
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.setting.navigation.Setting
import com.mshdabiola.ui.LocalSharedTransitionScope
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun KmtScaffold(
    modifier: Modifier = Modifier,
    appState: KmtAppState,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit,
) {
    val sharedScope = LocalSharedTransitionScope.current

    with(sharedScope) {
        if (appState is Compact) {
            ModalNavigationDrawer(
                modifier = modifier,
                drawerContent = {
                    ModalDrawerSheet(
                        modifier = Modifier.width(300.dp),
                        drawerState = appState.drawerState,
                    ) {
                        DrawerContent(
                            modifier = Modifier.padding(16.dp),
                            appState = appState,
                        )
                    }
                },
                drawerState = appState.drawerState,
                gesturesEnabled = appState.isTopRoute,
            ) {
                Scaffold(
                    containerColor = containerColor,
                    contentWindowInsets = contentWindowInsets,
                    contentColor = contentColor,
                    topBar = topBar,
                    bottomBar = bottomBar,
                    snackbarHost = snackbarHost,

                    floatingActionButton = {
                        AnimatedVisibility(appState.isMain) {
                            Fab(
                                appState = appState,
                                modifier = Modifier
                                    .sharedBounds(
                                        sharedContentState = rememberSharedContentState("note_-1"),
                                        animatedVisibilityScope = this,
                                    ),
                            )
                        }
                    },
                ) { paddingValues ->
                    content(paddingValues)
                }
            }
        } else {
            PermanentNavigationDrawer(
                modifier = modifier,
                drawerContent = {
                    if (appState.isTopRoute) {
                        if (appState is Medium) {
                            WideNavigationRail(
                                modifier = Modifier.fillMaxHeight(),
                                state = appState.wideNavigationRailState,
                                header = {
                                    IconButton(
                                        modifier =
                                        Modifier.padding(start = 24.dp).semantics {
                                            // The button must announce the expanded or collapsed state of the rail
                                            // for accessibility.
                                            stateDescription =
                                                if (appState.wideNavigationRailState.currentValue ==
                                                    WideNavigationRailValue.Expanded
                                                ) {
                                                    "Expanded"
                                                } else {
                                                    "Collapsed"
                                                }
                                        },
                                        onClick = {
                                            if (appState.wideNavigationRailState.targetValue ==
                                                WideNavigationRailValue.Expanded
                                            ) {
                                                appState.collapse()
                                            } else {
                                                appState.expand()
                                            }
                                        },
                                    ) {
                                        if (appState.wideNavigationRailState.targetValue ==
                                            WideNavigationRailValue.Expanded
                                        ) {
                                            Icon(Icons.AutoMirrored.Filled.MenuOpen, "Collapse rail")
                                        } else {
                                            Icon(Icons.Filled.Menu, "Expand rail")
                                        }
                                    }
                                },
                            ) {
                                DrawerContent(
                                    appState = appState,
                                )
                            }
                        }
                        if (appState is Expand) {
                            PermanentDrawerSheet(
                                drawerContainerColor = Color.Transparent,
                                modifier = Modifier.width(300.dp),
                            ) {
                                DrawerContent(
                                    modifier = Modifier.padding(16.dp),
                                    appState = appState,
                                )
                            }
                        }
                    }
                },
            ) {
                Scaffold(
                    containerColor = containerColor,
                    contentWindowInsets = contentWindowInsets,
                    contentColor = contentColor,
                    topBar = topBar,
                    bottomBar = bottomBar,
                    snackbarHost = snackbarHost,
                ) { paddingValues ->
                    content(paddingValues)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
fun KmtScaffoldPreview() {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val windowSizeClass = windowAdaptiveInfo.windowSizeClass
    val navController = rememberNavController().apply {
        graph =
            createGraph(startDestination = Main) {
                composable<Main> { }
                composable<Detail> { }
                composable<Setting> { }
            }
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val appState = rememberKmtAppState(
        windowSizeClass = windowSizeClass,
        navController = navController,
        drawerState = drawerState,
        wideNavigationRailState = rememberWideNavigationRailState(initialValue = WideNavigationRailValue.Collapsed),
    )
//    appState.navController.navigateToMain(Main)

    KmtScaffold(appState = appState) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text =
                "Note: This demo is best shown in portrait mode, as landscape mode" +
                    " may result in a compact height in certain devices. For any" +
                    " compact screen dimensions, use a Navigation Bar instead.",
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    appState: KmtAppState,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (appState !is Medium) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(imageVector = Icons.Default.Home, contentDescription = null)
                Text("KMTemplate")
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
        if (appState !is Compact && appState.isMain) {
            val modifier = if (appState is Medium) {
                Modifier.padding(start = 24.dp)
            } else {
                Modifier
            }
            Fab(
                modifier = modifier,
                appState = appState,
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
        TOP_LEVEL_ROUTES.forEach { item ->

            if (appState is Medium) {
                WideNavigationRailItem(
                    modifier = Modifier,
                    railExpanded = appState.wideNavigationRailState.targetValue == WideNavigationRailValue.Expanded,
                    icon = {
                        val imageVector =
                            if (appState.isInCurrentRoute(item.route)) {
                                item.selectedIcon
                            } else {
                                item.unSelectedIcon
                            }
                        Icon(imageVector = imageVector, contentDescription = null)
                    },
                    label = { Text(item.label) },
                    selected = appState.isInCurrentRoute(item.route),
                    onClick = {
                        appState.navigateTopRoute(item.route)
                    },
                )
            } else {
                NavigationDrawerItem(
                    modifier = Modifier,
                    icon = {
                        val imageVector =
                            if (appState.isInCurrentRoute(item.route)) {
                                item.selectedIcon
                            } else {
                                item.unSelectedIcon
                            }
                        Icon(imageVector = imageVector, contentDescription = null)
                    },
                    label = { Text(item.label) },
                    selected = appState.isInCurrentRoute(item.route),
                    onClick = {
                        appState.navigateTopRoute(item.route)
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Fab(
    modifier: Modifier = Modifier,
    appState: KmtAppState,
) {
    AnimatedContent(
        appState is Medium &&
            appState.wideNavigationRailState.targetValue == WideNavigationRailValue.Collapsed,
    ) {
        if (it) {
            SmallFloatingActionButton(
                modifier = modifier,
                onClick = { appState.navController.navigateToDetail(Detail(-1)) },
            ) {
                Icon(imageVector = KmtIcons.Add, "add")
            }
        } else {
            SmallExtendedFloatingActionButton(
                modifier = modifier,
                onClick = { appState.navController.navigateToDetail(Detail(-1)) },
            ) {
                Icon(imageVector = KmtIcons.Add, "add")
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text("Add Note")
            }
        }
    }
}
