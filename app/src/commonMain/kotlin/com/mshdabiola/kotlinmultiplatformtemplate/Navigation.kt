package com.mshdabiola.kotlinmultiplatformtemplate

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallExtendedFloatingActionButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.WideNavigationRail
import androidx.compose.material3.WideNavigationRailItem
import androidx.compose.material3.WideNavigationRailValue
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberWideNavigationRailState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.window.core.layout.WindowSizeClass
import com.mshdabiola.designsystem.icon.KmtIcons
import com.mshdabiola.detail.navigation.Detail
import com.mshdabiola.detail.navigation.navigateToDetail
import com.mshdabiola.kotlinmultiplatformtemplate.ui.KotlinMultiplatformTemplateAppState
import com.mshdabiola.kotlinmultiplatformtemplate.ui.TOP_LEVEL_ROUTES
import com.mshdabiola.kotlinmultiplatformtemplate.ui.isWidthCompact
import com.mshdabiola.kotlinmultiplatformtemplate.ui.isWidthMedium
import com.mshdabiola.kotlinmultiplatformtemplate.ui.rememberKotlinMultiplatformTemplateAppState
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.main.navigation.navigateToMain
import com.mshdabiola.setting.navigation.Setting
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ResponsiveScaffold(
    modifier: Modifier = Modifier,
    appState: KotlinMultiplatformTemplateAppState,
    content: @Composable (PaddingValues) -> Unit,
) {


    Row(modifier = modifier) {
        if (appState.showRail) {
            WideNavigationRail(
                modifier = Modifier.fillMaxHeight(),
                state = appState.wideNavigationRailState,
                header = {
                    Header(appState)
                },
            ) {

                if (!appState.windowSizeClass.isWidthCompact && appState.isMain) {

                    Fab(
                        modifier = Modifier.padding(start = 24.dp),
                        appState = appState,
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                TOP_LEVEL_ROUTES.forEachIndexed { index, item ->
                    WideNavigationRailItem(
                        modifier = Modifier,
                        railExpanded = appState.wideNavigationRailState.targetValue == WideNavigationRailValue.Expanded,
                        icon = {
                            val imageVector =
                                if (appState.currentRoute(item.route)) {
                                    item.selectedIcon
                                } else {
                                    item.unSelectedIcon
                                }
                            Icon(imageVector = imageVector, contentDescription = null)
                        },
                        label = { Text(item.label) },
                        selected = appState.currentRoute(item.route),
                        onClick = {

                        },
                    )
                }
            }
        }
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier.width(300.dp),
                    drawerState = appState.drawerState) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        TOP_LEVEL_ROUTES.forEach{ item ->

                            NavigationDrawerItem(
                                modifier = Modifier,
                                icon = {
                                    val imageVector =
                                        if (appState.currentRoute(item.route) ) {
                                            item.selectedIcon
                                        } else {
                                            item.unSelectedIcon
                                        }
                                    Icon(imageVector = imageVector, contentDescription = null)
                                },
                                label = { Text(item.label) },
                                selected =appState.currentRoute(item.route),
                                onClick = {

                                },
                            )
                        }
                    }

                }

            },
            drawerState = appState.drawerState,
            modifier = Modifier,
            gesturesEnabled = appState.showDrawer,
        ) {

            Scaffold(
                floatingActionButton = {
                    if (appState.showFab) {
                        Fab(
                            appState = appState,
                        )
                    }
                },
            ) { paddingValues ->
              content(paddingValues)
            }

        }

    }

}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
fun ResiposiveNavPreview() {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val windowSizeClass = WindowSizeClass(400f, 800f)
   val navController= rememberNavController().apply {
        graph =
            createGraph(startDestination = Main) {
                composable<Main> { }
                composable<Detail> { }
                composable <Setting>{  }
            }
    }
    val drawerState=rememberDrawerState(initialValue = DrawerValue.Open)
    val appState = rememberKotlinMultiplatformTemplateAppState(
        windowSizeClass = windowSizeClass,
        navController = navController,
        drawerState = drawerState,
        wideNavigationRailState = rememberWideNavigationRailState(initialValue = WideNavigationRailValue.Expanded),
    )
//    appState.navController.navigateToMain(Main)

    ResponsiveScaffold(appState = appState){
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
fun Header(appState: KotlinMultiplatformTemplateAppState) {
    if (appState.windowSizeClass.isWidthMedium) {
        IconButton(
            modifier =
                Modifier.padding(start = 24.dp).semantics {
                    // The button must announce the expanded or collapsed state of the rail
                    // for accessibility.
                    stateDescription =
                        if (appState.wideNavigationRailState.currentValue == WideNavigationRailValue.Expanded) {
                            "Expanded"
                        } else {
                            "Collapsed"
                        }
                },
            onClick = {

                if (appState.wideNavigationRailState.targetValue == WideNavigationRailValue.Expanded)
                    appState.collapse()
                else appState.expand()

            },
        ) {
            if (appState.wideNavigationRailState.targetValue == WideNavigationRailValue.Expanded) {
                Icon(Icons.AutoMirrored.Filled.MenuOpen, "Collapse rail")
            } else {
                Icon(Icons.Filled.Menu, "Expand rail")
            }
        }
    } else {
        Row(
            modifier = Modifier.padding(start = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Icon(imageVector = Icons.Default.Home, contentDescription = null)
            Text("KMTemplate")
        }
    }


}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Fab(
    modifier: Modifier = Modifier,
    appState: KotlinMultiplatformTemplateAppState,
) {
    AnimatedContent(appState.windowSizeClass.isWidthCompact || appState.wideNavigationRailState.targetValue == WideNavigationRailValue.Expanded) {
        if (it) {

            SmallExtendedFloatingActionButton(
                modifier = modifier,
                onClick = { appState.navController.navigateToDetail(Detail(-1)) },
            ) {
                Icon(imageVector = KmtIcons.Add, "add")
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text("Add Note")
            }
        } else {

            SmallFloatingActionButton(
                modifier = modifier,
                onClick = { appState.navController.navigateToDetail(Detail(-1)) },
            ) {
                Icon(imageVector = KmtIcons.Add, "add")
            }

        }


    }

}

