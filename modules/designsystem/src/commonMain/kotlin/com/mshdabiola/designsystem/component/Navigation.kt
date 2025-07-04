/*
 * Copyright (C) 2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package com.mshdabiola.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.icon.KmtIcons
import com.mshdabiola.designsystem.theme.KmtTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Now in Android navigation bar item with icon and label content slots. Wraps Material 3
 * [NavigationBarItem].
 *
 * @param selected Whether this item is selected.
 * @param onClick The callback to be invoked when this item is selected.
 * @param icon The item icon content.
 * @param modifier Modifier to be applied to this item.
 * @param selectedIcon The item icon content when selected.
 * @param enabled controls the enabled state of this item. When `false`, this item will not be
 * clickable and will appear disabled to accessibility services.
 * @param label The item text label content.
 * @param alwaysShowLabel Whether to always show the label for this item. If false, the label will
 * only be shown when this item is selected.
 */
@Composable
fun RowScope.KmtNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors =
        NavigationBarItemDefaults.colors(
            selectedIconColor = KmtNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = KmtNavigationDefaults.navigationContentColor(),
            selectedTextColor = KmtNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = KmtNavigationDefaults.navigationContentColor(),
            indicatorColor = KmtNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

/**
 * Now in Android navigation bar with content slot. Wraps Material 3 [NavigationBar].
 *
 * @param modifier Modifier to be applied to the navigation bar.
 * @param content Destinations inside the navigation bar. This should contain multiple
 * [NavigationBarItem]s.
 */
@Composable
fun KmtNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        contentColor = KmtNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content,
    )
}

/**
 * Now in Android navigation rail item with icon and label content slots. Wraps Material 3
 * [NavigationRailItem].
 *
 * @param selected Whether this item is selected.
 * @param onClick The callback to be invoked when this item is selected.
 * @param icon The item icon content.
 * @param modifier Modifier to be applied to this item.
 * @param selectedIcon The item icon content when selected.
 * @param enabled controls the enabled state of this item. When `false`, this item will not be
 * clickable and will appear disabled to accessibility services.
 * @param label The item text label content.
 * @param alwaysShowLabel Whether to always show the label for this item. If false, the label will
 * only be shown when this item is selected.
 */
@Composable
fun KmtNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null,
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors =
        NavigationRailItemDefaults.colors(
            selectedIconColor = KmtNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = KmtNavigationDefaults.navigationContentColor(),
            selectedTextColor = KmtNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = KmtNavigationDefaults.navigationContentColor(),
            indicatorColor = KmtNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

/**
 * Now in Android navigation rail with header and content slots. Wraps Material 3 [NavigationRail].
 *
 * @param modifier Modifier to be applied to the navigation rail.
 * @param header Optional header that may hold a floating action button or a logo.
 * @param content Destinations inside the navigation rail. This should contain multiple
 * [NavigationRailItem]s.
 */
@Composable
fun KmtNavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = KmtNavigationDefaults.navigationContentColor(),
        header = header,
        content = content,
    )
}

/**
 * Now in Android navigation suite scaffold with item and content slots.
 * Wraps Material 3 [NavigationSuiteScaffold].
 *
 * @param modifier Modifier to be applied to the navigation suite scaffold.
 * @param navigationSuiteItems A slot to display multiple items via [KmtNavigationSuiteScope].
 * @param windowAdaptiveInfo The window adaptive info.
 * @param content The app content inside the scaffold.
 */
@Composable
fun KmtNavigationSuiteScaffold(
    navigationSuiteItems: KmtNavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    layoutType: NavigationSuiteType,
    content: @Composable () -> Unit,
) {
    val navigationSuiteItemColors =
        NavigationSuiteItemColors(
            navigationBarItemColors =
            NavigationBarItemDefaults.colors(
                selectedIconColor = KmtNavigationDefaults.navigationSelectedItemColor(),
                unselectedIconColor = KmtNavigationDefaults.navigationContentColor(),
                selectedTextColor = KmtNavigationDefaults.navigationSelectedItemColor(),
                unselectedTextColor = KmtNavigationDefaults.navigationContentColor(),
                indicatorColor = KmtNavigationDefaults.navigationIndicatorColor(),
            ),
            navigationRailItemColors =
            NavigationRailItemDefaults.colors(
                selectedIconColor = KmtNavigationDefaults.navigationSelectedItemColor(),
                unselectedIconColor = KmtNavigationDefaults.navigationContentColor(),
                selectedTextColor = KmtNavigationDefaults.navigationSelectedItemColor(),
                unselectedTextColor = KmtNavigationDefaults.navigationContentColor(),
                indicatorColor = KmtNavigationDefaults.navigationIndicatorColor(),
            ),
            navigationDrawerItemColors =
            NavigationDrawerItemDefaults.colors(
                selectedIconColor = KmtNavigationDefaults.navigationSelectedItemColor(),
                unselectedIconColor = KmtNavigationDefaults.navigationContentColor(),
                selectedTextColor = KmtNavigationDefaults.navigationSelectedItemColor(),
                unselectedTextColor = KmtNavigationDefaults.navigationContentColor(),
            ),
        )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            KmtNavigationSuiteScope(
                navigationSuiteScope = this,
                navigationSuiteItemColors = navigationSuiteItemColors,
            ).run(navigationSuiteItems)
        },
        layoutType = layoutType,
        containerColor = Color.Transparent,
        navigationSuiteColors =
        NavigationSuiteDefaults.colors(
            navigationBarContentColor = KmtNavigationDefaults.navigationContentColor(),
            navigationRailContainerColor = Color.Transparent,
        ),
        modifier = modifier,
    ) {
        content()
    }
}

/**
 * A wrapper around [NavigationSuiteScope] to declare navigation items.
 */
class KmtNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteItemColors: NavigationSuiteItemColors,
) {
    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit = icon,
        label: @Composable (() -> Unit)? = null,
    ) = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        icon = {
            if (selected) {
                selectedIcon()
            } else {
                icon()
            }
        },
        label = label,
        colors = navigationSuiteItemColors,
        modifier = modifier,
    )
}

@Preview
@Composable
fun KmtNavigationBarPreview() {
    val items = listOf("For you", "Saved", "Interests")
    val icons =
        listOf(
            KmtIcons.UpcomingBorder,
            KmtIcons.BookmarksBorder,
            KmtIcons.Grid3x3,
        )
    val selectedIcons =
        listOf(
            KmtIcons.Upcoming,
            KmtIcons.Bookmarks,
            KmtIcons.Grid3x3,
        )

    KmtTheme {
        KmtNavigationBar {
            items.forEachIndexed { index, item ->
                KmtNavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = selectedIcons[index],
                            contentDescription = item,
                        )
                    },
                    label = { Text(item) },
                    selected = index == 0,
                    onClick = { },
                )
            }
        }
    }
}

@Preview
@Composable
fun KmtNavigationRailPreview() {
    val items = listOf("For you", "Saved", "Interests")
    val icons =
        listOf(
            KmtIcons.UpcomingBorder,
            KmtIcons.BookmarksBorder,
            KmtIcons.Grid3x3,
        )
    val selectedIcons =
        listOf(
            KmtIcons.Upcoming,
            KmtIcons.Bookmarks,
            KmtIcons.Grid3x3,
        )

    KmtTheme {
        KmtNavigationRail {
            items.forEachIndexed { index, item ->
                KmtNavigationRailItem(
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = selectedIcons[index],
                            contentDescription = item,
                        )
                    },
                    label = { Text(item) },
                    selected = index == 0,
                    onClick = { },
                )
            }
        }
    }
}

/**
 * Now in Android navigation default values.
 */
object KmtNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}
