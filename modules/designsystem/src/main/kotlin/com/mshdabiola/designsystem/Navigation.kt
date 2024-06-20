/*
 *abiola 2022
 */

package com.mshdabiola.designsystem

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
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.ThemePreviews
import com.mshdabiola.designsystem.icon.SkIcons
import com.mshdabiola.designsystem.theme.SkTheme

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
fun RowScope.SkNavigationBarItem(
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
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = SkNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = SkNavigationDefaults.navigationContentColor(),
            selectedTextColor = SkNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = SkNavigationDefaults.navigationContentColor(),
            indicatorColor = SkNavigationDefaults.navigationIndicatorColor(),
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
fun SkNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        contentColor = SkNavigationDefaults.navigationContentColor(),
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
fun SkNavigationRailItem(
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
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = SkNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = SkNavigationDefaults.navigationContentColor(),
            selectedTextColor = SkNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = SkNavigationDefaults.navigationContentColor(),
            indicatorColor = SkNavigationDefaults.navigationIndicatorColor(),
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
fun SkNavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = SkNavigationDefaults.navigationContentColor(),
        header = header,
        content = content,
    )
}

/**
 * Now in Android navigation suite scaffold with item and content slots.
 * Wraps Material 3 [NavigationSuiteScaffold].
 *
 * @param modifier Modifier to be applied to the navigation suite scaffold.
 * @param navigationSuiteItems A slot to display multiple items via [SkNavigationSuiteScope].
 * @param windowAdaptiveInfo The window adaptive info.
 * @param content The app content inside the scaffold.
 */
@Composable
fun SkNavigationSuiteScaffold(
    navigationSuiteItems: SkNavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    isTopDestination: Boolean = false,
    content: @Composable () -> Unit,
) {
    val layoutType =
        if (!isTopDestination) {
            NavigationSuiteType.None
        } else {
            NavigationSuiteScaffoldDefaults
                .calculateFromAdaptiveInfo(windowAdaptiveInfo)
        }

    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = SkNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = SkNavigationDefaults.navigationContentColor(),
            selectedTextColor = SkNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = SkNavigationDefaults.navigationContentColor(),
            indicatorColor = SkNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = SkNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = SkNavigationDefaults.navigationContentColor(),
            selectedTextColor = SkNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = SkNavigationDefaults.navigationContentColor(),
            indicatorColor = SkNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = SkNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = SkNavigationDefaults.navigationContentColor(),
            selectedTextColor = SkNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = SkNavigationDefaults.navigationContentColor(),
        ),
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            SkNavigationSuiteScope(
                navigationSuiteScope = this,
                navigationSuiteItemColors = navigationSuiteItemColors,
            ).run(navigationSuiteItems)
        },
        layoutType = layoutType,
        containerColor = Color.Transparent,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContentColor = SkNavigationDefaults.navigationContentColor(),
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
class SkNavigationSuiteScope internal constructor(
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

@ThemePreviews
@Composable
fun SkNavigationBarPreview() {
    val items = listOf("For you", "Saved", "Interests")
    val icons = listOf(
        SkIcons.UpcomingBorder,
        SkIcons.BookmarksBorder,
        SkIcons.Grid3x3,
    )
    val selectedIcons = listOf(
        SkIcons.Upcoming,
        SkIcons.Bookmarks,
        SkIcons.Grid3x3,
    )

    SkTheme {
        SkNavigationBar {
            items.forEachIndexed { index, item ->
                SkNavigationBarItem(
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

@ThemePreviews
@Composable
fun SkNavigationRailPreview() {
    val items = listOf("For you", "Saved", "Interests")
    val icons = listOf(
        SkIcons.UpcomingBorder,
        SkIcons.BookmarksBorder,
        SkIcons.Grid3x3,
    )
    val selectedIcons = listOf(
        SkIcons.Upcoming,
        SkIcons.Bookmarks,
        SkIcons.Grid3x3,
    )

    SkTheme {
        SkNavigationRail {
            items.forEachIndexed { index, item ->
                SkNavigationRailItem(
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
object SkNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}
