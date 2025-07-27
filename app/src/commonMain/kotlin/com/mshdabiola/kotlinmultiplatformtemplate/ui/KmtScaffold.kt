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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.WideNavigationRailDefaults
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
import androidx.compose.ui.platform.testTag // Ensure this import is present
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.mshdabiola.designsystem.drawable.KmtDrawable
import com.mshdabiola.designsystem.drawable.KmtIcons
import com.mshdabiola.designsystem.strings.KmtStrings
import com.mshdabiola.detail.navigation.Detail
import com.mshdabiola.detail.navigation.navigateToDetail
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.setting.navigation.Setting
import com.mshdabiola.ui.LocalSharedTransitionScope
import org.jetbrains.compose.ui.tooling.preview.Preview

// Test Tags for KmtScaffold and its inner components
object KmtScaffoldTestTags {
    const val MODAL_NAVIGATION_DRAWER = "scaffold:modal_nav_drawer"
    const val PERMANENT_NAVIGATION_DRAWER = "scaffold:permanent_nav_drawer"
    const val MODAL_DRAWER_SHEET = "scaffold:modal_drawer_sheet"
    const val WIDE_NAVIGATION_RAIL = "scaffold:wide_nav_rail"
    const val PERMANENT_DRAWER_SHEET = "scaffold:permanent_drawer_sheet"
    const val RAIL_TOGGLE_BUTTON = "scaffold:rail_toggle_button" // For expanding/collapsing rail
    const val SCAFFOLD_CONTENT_AREA = "scaffold:content_area" // For the main Scaffold used inside drawers
}

object DrawerContentTestTags {
    const val DRAWER_CONTENT_COLUMN = "drawer:content_column"
    const val BRAND_ROW = "drawer:brand_row"
    const val BRAND_ICON = "drawer:brand_icon"
    const val BRAND_TEXT = "drawer:brand_text"
    fun navigationItemTag(route: Any) = "drawer:nav_item_$route"
    fun wideNavigationRailItemTag(route: Any) = "drawer:wide_nav_rail_item_$route"
}

object FabTestTags {
    const val FAB_ANIMATED_CONTENT = "fab:animated_content"
    const val SMALL_FAB = "fab:small_fab"
    const val EXTENDED_FAB = "fab:extended_fab"
    const val FAB_ADD_ICON = "fab:add_icon" // Common for both FAB types
    const val FAB_ADD_TEXT = "fab:add_text" // Specific to Extended FAB
}

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
                        )
                    }
                },
                drawerState = appState.drawerState,
                gesturesEnabled = appState.isTopRoute,
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
                modifier = modifier.testTag(KmtScaffoldTestTags.PERMANENT_NAVIGATION_DRAWER),
                drawerContent = {
                    if (appState.isTopRoute) {
                        if (appState is Medium) {
                            WideNavigationRail(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .testTag(KmtScaffoldTestTags.WIDE_NAVIGATION_RAIL),
                                state = appState.wideNavigationRailState,
                                colors = WideNavigationRailDefaults.colors(containerColor = containerColor),
                                header = {
                                    IconButton(
                                        modifier = Modifier
                                            .padding(start = 24.dp)
                                            .semantics {
                                                stateDescription =
                                                    if (appState.wideNavigationRailState.currentValue ==
                                                        WideNavigationRailValue.Expanded
                                                    ) {
                                                        "Expanded"
                                                    } else {
                                                        "Collapsed"
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
                                            Icon(KmtIcons.MenuOpen, "Collapse rail")
                                        } else {
                                            Icon(KmtIcons.Menu, "Expand rail")
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
                                drawerContainerColor = containerColor,
                                modifier = Modifier
                                    .width(300.dp)
                                    .testTag(KmtScaffoldTestTags.PERMANENT_DRAWER_SHEET),
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

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    appState: KmtAppState,
) {
    Column(
        modifier = modifier.testTag(DrawerContentTestTags.DRAWER_CONTENT_COLUMN),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AnimatedVisibility(appState !is Medium) {
            Row(
                modifier = Modifier.testTag(DrawerContentTestTags.BRAND_ROW),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .testTag(DrawerContentTestTags.BRAND_ICON),
                    imageVector = KmtDrawable.brand,
                    contentDescription = "brand",
                )
                Text(
                    KmtStrings.brand,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.testTag(DrawerContentTestTags.BRAND_TEXT),
                )
            }
            Spacer(modifier = Modifier.height(64.dp))
        }
        AnimatedVisibility(appState !is Compact && appState.isMain) {
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
        TOP_LEVEL_ROUTES.forEach { item ->

            if (appState is Medium) {
                WideNavigationRailItem(
                    modifier = Modifier.testTag(DrawerContentTestTags.wideNavigationRailItemTag(item.route)),
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
                    modifier = Modifier.testTag(DrawerContentTestTags.navigationItemTag(item.route)),
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
    modifier: Modifier = Modifier, // The passed modifier might already include sharedBounds
    appState: KmtAppState,
) {
    AnimatedContent(
        targetState = appState is Medium &&
            appState.wideNavigationRailState.targetValue == WideNavigationRailValue.Collapsed,
        modifier = modifier.testTag(FabTestTags.FAB_ANIMATED_CONTENT), // Tag the AnimatedContent wrapper
    ) { isCollapsedMediumFab ->
        if (isCollapsedMediumFab) {
            SmallFloatingActionButton(
                modifier = Modifier.testTag(FabTestTags.SMALL_FAB), // Tag the specific FAB type
                onClick = { appState.navController.navigateToDetail(Detail(-1)) },
            ) {
                Icon(
                    imageVector = KmtIcons.Add,
                    contentDescription = "add",
                    modifier = Modifier.testTag(FabTestTags.FAB_ADD_ICON),
                )
            }
        } else {
            SmallExtendedFloatingActionButton(
                modifier = Modifier.testTag(FabTestTags.EXTENDED_FAB), // Tag the specific FAB type
                onClick = { appState.navController.navigateToDetail(Detail(-1)) },
            ) {
                Icon(
                    imageVector = KmtIcons.Add,
                    contentDescription = "add",
                    modifier = Modifier.testTag(FabTestTags.FAB_ADD_ICON),
                )
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text(
                    "Add Note",
                    modifier = Modifier.testTag(FabTestTags.FAB_ADD_TEXT),
                )
            }
        }
    }
}
