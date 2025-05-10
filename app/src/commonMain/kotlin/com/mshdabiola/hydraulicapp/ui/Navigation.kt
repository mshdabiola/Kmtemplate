package com.mshdabiola.hydraulicapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.mshdabiola.designsystem.component.HyaNavigationBar
import com.mshdabiola.designsystem.component.HyaNavigationBarItem
import com.mshdabiola.designsystem.component.HyaNavigationRail
import com.mshdabiola.designsystem.component.HyaNavigationRailItem
import com.mshdabiola.designsystem.icon.HyaIcons
import com.mshdabiola.designsystem.string.appName
import com.mshdabiola.designsystem.string.cbtNavigator
import com.mshdabiola.designsystem.string.settingNavigator
import com.mshdabiola.ui.ProfileCard

@Composable
fun CommonNavigation(
    modifier: Modifier = Modifier,
    navController: NavController,
    showLong: Boolean = true,
) {
    val color = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent)

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(0.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier =
                modifier
                    .padding(8.dp)
                    .verticalScroll(state = rememberScrollState()),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(HyaIcons.LocalLibrary, "Logo")
                Text(
                    appName,
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
            Spacer(Modifier.height(32.dp))
            if (showLong) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("Main")
                    TOP_LEVEL_ROUTES
                        .forEachIndexed { index, navigator ->
                            NavigationDrawerItem(
                                selected =
                                    navController
                                        .currentBackStackEntryAsState().value?.destination?.hasRoute(
                                            route = navigator.route::class,
                                        )
                                        == true,
                                label = { Text("Testing") },
                                onClick = {
                                    navController.navigate(
                                        navigator.route,
                                        navOptions =
                                            navOptions {
                                                launchSingleTop
                                                restoreState
                                            },
                                    )
                                },
                                colors = color,
                                icon = { Icon(navigator.icon, "Testing") },
                            )
                        }
                }
            }

            Spacer(Modifier.height(64.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SETTING_LEVEL_ROUTES
                    .forEachIndexed { index, navigator ->
                        NavigationDrawerItem(
                            selected =
                                navController
                                    .currentBackStackEntryAsState()
                                    .value?.destination
                                    ?.hasRoute(route = navigator.route::class)
                                    == true,
                            label = { Text("Setting") },
                            onClick = {
                                navController.navigate(navigator.route)
                            },
                            colors = color,
                            icon = {
                                Icon(
                                    navigator.icon,
                                    "Setting",
                                )
                            },
                        )
                    }
            }
            Spacer(Modifier.height(8.dp))

            HorizontalDivider()
            Spacer(Modifier.height(8.dp))

            ProfileCard()
        }
    }
}

@Composable
fun CommonRail(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    HyaNavigationRail(modifier) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                Modifier
                    .padding(8.dp)
                    .verticalScroll(state = rememberScrollState()),
        ) {
            Icon(HyaIcons.LocalLibrary, "Logo")

            Spacer(Modifier.height(32.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Main")
                TOP_LEVEL_ROUTES
                    .forEachIndexed { index, navigator ->
                        HyaNavigationRailItem(
                            selected =
                                navController
                                    .currentBackStackEntryAsState()
                                    .value?.destination
                                    ?.hasRoute(route = navigator.route::class)
                                    == true,
                            label = { Text(settingNavigator[index]) },
                            onClick = {
                                navController.navigate(navigator.route)
                            },
                            icon = {
                                Icon(
                                    navigator.icon,
                                    settingNavigator[index],
                                )
                            },
                        )
                    }
            }

            Spacer(Modifier.height(64.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SETTING_LEVEL_ROUTES
                    .forEachIndexed { index, navigator ->
                        HyaNavigationRailItem(
                            selected =
                                navController
                                    .currentBackStackEntryAsState()
                                    .value
                                    ?.destination?.hasRoute(route = navigator.route::class)
                                    == true,
                            label = { Text(settingNavigator[index]) },
                            onClick = {
                                navController.navigate(
                                    navigator.route,
                                    navOptions =
                                        navOptions {
                                            launchSingleTop
                                            restoreState
                                        },
                                )
                            },
                            icon = {
                                Icon(
                                    navigator.icon,
                                    settingNavigator[index],
                                )
                            },
                        )
                    }
            }

            HorizontalDivider()

            // ProfileCard()
        }
    }
}

@Composable
fun CommonBar(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    HyaNavigationBar(modifier) {
        TOP_LEVEL_ROUTES
            .forEachIndexed { index, navigator ->
                HyaNavigationBarItem(
                    selected =
                        navController
                            .currentBackStackEntryAsState()
                            .value?.destination
                            ?.hasRoute(route = navigator.route::class)
                            == true,
                    label = { Text(cbtNavigator[index]) },
                    onClick = {
                        navController.navigate(
                            navigator.route,
                            navOptions =
                                navOptions {
                                    launchSingleTop
                                    restoreState
                                },
                        )
                    },
                    alwaysShowLabel = false,
                    icon = {
                        Icon(
                            navigator.icon,
                            cbtNavigator[index],
                        )
                    },
                )
            }
    }
}
