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
import com.mshdabiola.designsystem.component.KmtNavigationBar
import com.mshdabiola.designsystem.component.KmtNavigationBarItem
import com.mshdabiola.designsystem.component.KmtNavigationRail
import com.mshdabiola.designsystem.component.KmtNavigationRailItem
import com.mshdabiola.designsystem.icon.KmtIcons
import com.mshdabiola.kotlinmultiplatformtemplate.app.generated.resources.Res
import com.mshdabiola.kotlinmultiplatformtemplate.app.generated.resources.app_name
import com.mshdabiola.kotlinmultiplatformtemplate.app.generated.resources.main_navigator
import com.mshdabiola.kotlinmultiplatformtemplate.app.generated.resources.setting_navigator
import com.mshdabiola.ui.ProfileCard
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CommonNavigation(
    modifier: Modifier = Modifier,
    navController: NavController,
    showLong: Boolean = true,
) {
    val color = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent)
    val mainArray =
        stringArrayResource(Res.array.main_navigator).ifEmpty {
            listOf(
                "Contributions",
                "NearBy",
                "Explore",
                "Bookmarks",
                "Review",
            )
        }
    val settingArray =
        stringArrayResource(Res.array.setting_navigator).ifEmpty {
            listOf(
                "Setting",
                "Feedback",
                "About",
            )
        }
    print("main string ${mainArray.joinToString()}")
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
                Icon(KmtIcons.LocalLibrary, "Logo")
                Text(
                    stringResource(Res.string.app_name),
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
                                label = { Text(mainArray[index]) },
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
                                icon = { Icon(navigator.icon, mainArray[index]) },
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
                            label = { Text(settingArray[index]) },
                            onClick = {
                                navController.navigate(navigator.route)
                            },
                            colors = color,
                            icon = {
                                Icon(
                                    navigator.icon,
                                    settingArray[index],
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
    val mainArray =
        stringArrayResource(Res.array.main_navigator).ifEmpty {
            listOf(
                "Contributions",
                "NearBy",
                "Explore",
                "Bookmarks",
                "Review",
            )
        }
    val settingArray =
        stringArrayResource(Res.array.setting_navigator).ifEmpty {
            listOf(
                "Setting",
                "Feedback",
                "About",
            )
        }

    KmtNavigationRail(modifier) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
            Modifier
                .padding(8.dp)
                .verticalScroll(state = rememberScrollState()),
        ) {
            Icon(KmtIcons.LocalLibrary, "Logo")

            Spacer(Modifier.height(32.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Main")
                TOP_LEVEL_ROUTES
                    .forEachIndexed { index, navigator ->
                        KmtNavigationRailItem(
                            selected =
                            navController
                                .currentBackStackEntryAsState()
                                .value?.destination
                                ?.hasRoute(route = navigator.route::class)
                                == true,
                            label = { Text(settingArray[index]) },
                            onClick = {
                                navController.navigate(navigator.route)
                            },
                            icon = {
                                Icon(
                                    navigator.icon,
                                    settingArray[index],
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
                        KmtNavigationRailItem(
                            selected =
                            navController
                                .currentBackStackEntryAsState()
                                .value
                                ?.destination?.hasRoute(route = navigator.route::class)
                                == true,
                            label = { Text(settingArray[index]) },
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
                                    settingArray[index],
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
    val mainArray =
        stringArrayResource(Res.array.main_navigator).ifEmpty {
            listOf(
                "Contributions",
                "NearBy",
                "Explore",
                "Bookmarks",
                "Review",
            )
        }
    val settingArray =
        stringArrayResource(Res.array.setting_navigator).ifEmpty {
            listOf(
                "Setting",
                "Feedback",
                "About",
            )
        }

    KmtNavigationBar(modifier) {
        TOP_LEVEL_ROUTES
            .forEachIndexed { index, navigator ->
                KmtNavigationBarItem(
                    selected =
                    navController
                        .currentBackStackEntryAsState()
                        .value?.destination
                        ?.hasRoute(route = navigator.route::class)
                        == true,
                    label = { Text(mainArray[index]) },
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
                            mainArray[index],
                        )
                    },
                )
            }
    }
}
