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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.WideNavigationRail
import androidx.compose.material3.WideNavigationRailDefaults
import androidx.compose.material3.WideNavigationRailValue
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.mshdabiola.designsystem.component.CustomWideNavigationRailItem
import com.mshdabiola.designsystem.drawable.KmtIcons
import com.mshdabiola.designsystem.strings.KmtStrings
import com.mshdabiola.detail.navigation.Detail
import com.mshdabiola.detail.navigation.navigateToDetail
import com.mshdabiola.kmtemplate.app.generated.resources.Res
import com.mshdabiola.kmtemplate.app.generated.resources.add_content_description
import com.mshdabiola.kmtemplate.app.generated.resources.brand_content_description
import com.mshdabiola.kmtemplate.app.generated.resources.fab_add_note_text
import com.mshdabiola.kmtemplate.app.generated.resources.rail_action_collapse
import com.mshdabiola.kmtemplate.app.generated.resources.rail_action_expand
import com.mshdabiola.kmtemplate.app.generated.resources.rail_state_collapsed
import com.mshdabiola.kmtemplate.app.generated.resources.rail_state_expanded
import com.mshdabiola.kmtemplate.app.generated.resources.route
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.model.testtag.KmtScaffoldTestTags
import com.mshdabiola.setting.navigation.Setting
import com.mshdabiola.ui.LocalSharedTransitionScope
import com.mshdabiola.ui.SharedTransitionContainer
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
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

    val topDestination = remember {
        setOf(
            TopLevelRoute(
                route = Main,
                selectedIcon = KmtIcons.Home,
                unSelectedIcon = KmtIcons.HomeOutlined,
                label = 0,
            ),
            TopLevelRoute(
                route = Setting,
                selectedIcon = KmtIcons.Settings,
                unSelectedIcon = KmtIcons.SettingsOutlined,
                label = 1,
            ),

        )
    }

    val currentDestination = appState.navController
        .currentBackStackEntryAsState().value?.destination
    val isMain = remember(currentDestination) {
        currentDestination?.hasRoute(Main::class) == true
    }
    val isTopDestination = remember(currentDestination) {
        topDestination.any {
            currentDestination
                ?.hasRoute(it.route::class)
                ?: false
        }
    }

    with(sharedScope) {
        if (appState is Compact) {
            ModalNavigationDrawer(
                modifier = modifier.testTag(KmtScaffoldTestTags.MODAL_NAVIGATION_DRAWER),
                drawerContent = {
                    ModalDrawerSheet(
                        modifier = Modifier
                            .width(300.dp)
                            .testTag(KmtScaffoldTestTags.MODAL_DRAWER_SHEET),
                        drawerState = appState.drawerState,
                    ) {
                        DrawerContent(
                            modifier = Modifier.padding(16.dp),
                            appState = appState,
                            isMain = isMain,
                            topDestination = topDestination,
                        )
                    }
                },
                drawerState = appState.drawerState,
                gesturesEnabled = isTopDestination,
            ) {
                Scaffold(
                    modifier = Modifier.testTag(KmtScaffoldTestTags.SCAFFOLD_CONTENT_AREA + "_compact"),
                    containerColor = containerColor,
                    contentWindowInsets = contentWindowInsets,
                    contentColor = contentColor,
                    topBar = topBar,
                    bottomBar = bottomBar,
                    snackbarHost = snackbarHost,
                    floatingActionButton = {
                        AnimatedVisibility(isMain) {
                            Fab(
                                appState = appState,
                                modifier = Modifier
                                    .navigationBarsPadding()
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
                modifier = modifier.testTag(KmtScaffoldTestTags.PERMANENT_NAVIGATION_DRAWER),
                drawerContent = {
                    if (isTopDestination) {
                        if (appState is Medium) {
                            WideNavigationRail(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .testTag(KmtScaffoldTestTags.WIDE_NAVIGATION_RAIL),
                                state = appState.wideNavigationRailState,
                                colors = WideNavigationRailDefaults.colors(containerColor = containerColor),
                                header = {
                                    val expand = stringResource(Res.string.rail_state_expanded)
                                    val collapse = stringResource(Res.string.rail_state_collapsed)
                                    IconButton(
                                        modifier = Modifier
                                            .padding(start = 24.dp)
                                            .semantics {
                                                stateDescription =
                                                    if (appState.wideNavigationRailState.currentValue ==
                                                        WideNavigationRailValue.Expanded
                                                    ) {
                                                        expand
                                                    } else {
                                                        collapse
                                                    }
                                            }
                                            .testTag(KmtScaffoldTestTags.RAIL_TOGGLE_BUTTON),
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
                                            Icon(KmtIcons.MenuOpen, stringResource(Res.string.rail_action_collapse))
                                        } else {
                                            Icon(KmtIcons.Menu, stringResource(Res.string.rail_action_expand))
                                        }
                                    }
                                },
                            ) {
                                DrawerContent(
                                    appState = appState,
                                    isMain = isMain,
                                    topDestination = topDestination,
                                )
                            }
                        }
                        if (appState is Expand) {
                            PermanentDrawerSheet(
                                drawerContainerColor = containerColor,
                                modifier = Modifier
                                    .width(300.dp)
                                    .testTag(KmtScaffoldTestTags.PERMANENT_DRAWER_SHEET),
                            ) {
                                DrawerContent(
                                    modifier = Modifier.padding(16.dp),
                                    appState = appState,
                                    isMain = isMain,
                                    topDestination = topDestination,
                                )
                            }
                        }
                    }
                },
            ) {
                Scaffold(
                    modifier = Modifier.testTag(KmtScaffoldTestTags.SCAFFOLD_CONTENT_AREA + "_permanent"),
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
    val navController = rememberNavController().apply {
        graph =
            createGraph(startDestination = Main) {
                composable<Main> { }
                composable<Detail> { }
                composable<Setting> { }
            }
    }
    val appState = Expand(
        navController = navController,
        snackbarHostState = SnackbarHostState(),
        coroutineScope = rememberCoroutineScope(),
    )

    SharedTransitionContainer {
        KmtScaffold(appState = appState) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
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
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    appState: KmtAppState,
    isMain: Boolean,
    topDestination: Set<TopLevelRoute<out Any>>,
) {
    val scrollState = rememberScrollState()
    val routeArray = stringArrayResource(Res.array.route)

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .testTag(KmtScaffoldTestTags.DrawerContentTestTags.DRAWER_CONTENT_COLUMN),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AnimatedVisibility(appState !is Medium) {
            Row(
                modifier = Modifier.testTag(KmtScaffoldTestTags.DrawerContentTestTags.BRAND_ROW),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .testTag(KmtScaffoldTestTags.DrawerContentTestTags.BRAND_ICON),
                    imageVector = KmtIcons.AppIcon,
                    contentDescription = stringResource(Res.string.brand_content_description),
                    tint = MaterialTheme.colorScheme.primary,
                )
                Text(
                    KmtStrings.brand, // Assuming KmtStrings.brand is already a resource or intended to be so.
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.testTag(KmtScaffoldTestTags.DrawerContentTestTags.BRAND_TEXT),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Spacer(modifier = Modifier.height(64.dp))
        }
        AnimatedVisibility(appState !is Compact && isMain) {
            val fabModifier = if (appState is Medium) {
                Modifier.padding(start = 24.dp)
            } else {
                Modifier
            }
            Fab(
                modifier = fabModifier, // Modifier for FAB is passed, its internal tags handle specifics
                appState = appState,
            )

            Spacer(modifier = Modifier.height(64.dp))
        }
        topDestination.forEach { item ->

            if (appState is Medium) {
                CustomWideNavigationRailItem(
                    modifier = Modifier.testTag(
                        KmtScaffoldTestTags.DrawerContentTestTags.wideNavigationRailItemTag(item.route),
                    ),
                    railExpanded = appState.wideNavigationRailState.targetValue == WideNavigationRailValue.Expanded,
                    icon = {
                        val imageVector =
                            if (appState.isInCurrentRoute(item.route)) {
                                item.selectedIcon
                            } else {
                                item.unSelectedIcon
                            }
                        Icon(
                            imageVector = imageVector,
                            contentDescription = routeArray.getOrElse(item.label, { "" }),
                        )
                    },
                    label = { Text(routeArray.getOrElse(item.label, { "" })) },
                    selected = appState.isInCurrentRoute(item.route),
                    onClick = {
                        appState.navigateTopRoute(item.route)
                    },
                )
            } else {
                NavigationDrawerItem(
                    modifier = Modifier.testTag(
                        KmtScaffoldTestTags
                            .DrawerContentTestTags.navigationItemTag(item.route),
                    ),
                    icon = {
                        val imageVector =
                            if (appState.isInCurrentRoute(item.route)) {
                                item.selectedIcon
                            } else {
                                item.unSelectedIcon
                            }
                        Icon(
                            imageVector = imageVector,
                            contentDescription = routeArray.getOrElse(item.label, { "" }),
                        )
                    },
                    label = { Text(routeArray.getOrElse(item.label, { "" })) },
                    selected = appState.isInCurrentRoute(item.route),
                    onClick = {
                        appState.navigateTopRoute(item.route)
                        if (appState is Compact) {
                            appState.coroutineScope.launch {
                                appState.onDrawerToggle()
                            }
                        }
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Fab(
    modifier: Modifier = Modifier, // The passed modifier might already include sharedBounds
    appState: KmtAppState,
) {
    AnimatedContent(
        targetState = appState is Medium &&
            appState.wideNavigationRailState.targetValue == WideNavigationRailValue.Collapsed,

        modifier = modifier.testTag(KmtScaffoldTestTags.FabTestTags.FAB_ANIMATED_CONTENT),
        // Tag the AnimatedContent wrapper
    ) { isCollapsedMediumFab ->
        if (isCollapsedMediumFab) {
            SmallFloatingActionButton(
                modifier = Modifier.testTag(KmtScaffoldTestTags.FabTestTags.SMALL_FAB), // Tag the specific FAB type
                onClick = { appState.navController.navigateToDetail(Detail(-1)) },
            ) {
                Icon(
                    imageVector = KmtIcons.Add,
                    contentDescription = stringResource(Res.string.add_content_description),
                    modifier = Modifier.testTag(KmtScaffoldTestTags.FabTestTags.FAB_ADD_ICON),
                )
            }
        } else {
            SmallExtendedFloatingActionButton(
                modifier = Modifier.testTag(KmtScaffoldTestTags.FabTestTags.EXTENDED_FAB),
                // Tag the specific FAB type
                onClick = { appState.navController.navigateToDetail(Detail(-1)) },
            ) {
                Icon(
                    imageVector = KmtIcons.Add,
                    contentDescription = stringResource(Res.string.add_content_description),
                    modifier = Modifier.testTag(KmtScaffoldTestTags.FabTestTags.FAB_ADD_ICON),
                )
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text(
                    stringResource(Res.string.fab_add_note_text),
                    modifier = Modifier.testTag(KmtScaffoldTestTags.FabTestTags.FAB_ADD_TEXT),
                )
            }
        }
    }
}
