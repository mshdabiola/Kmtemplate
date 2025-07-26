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
package com.mshdabiola.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.KmtIconButton
import com.mshdabiola.designsystem.component.KmtTopAppBar
import com.mshdabiola.designsystem.icon.KmtIcons
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.ui.SharedTransitionContainer
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class SettingNav {
    Appearance,
    About,
    Faq,
    Issue,
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onDrawer: (() -> Unit)?,

) {
    val navigator = rememberListDetailPaneScaffoldNavigator<SettingNav>()
    val coroutineScope = rememberCoroutineScope()
    val settingsMap = mapOf(
        "General" to
            listOf(
                SettingList(
                    icon = Icons.Default.Contrast,
                    label = "Appearance",
                    onClick = {
                        coroutineScope.launch {
                            navigator.navigateTo(
                                pane = ListDetailPaneScaffoldRole.Detail,
                                contentKey = SettingNav.Appearance,
                            )
                        }
                    },
                ),
            ),
        "Support" to
            listOf(
                SettingList(
                    icon = Icons.Default.Contrast,
                    label = "Report an isssue",
                    onClick = {
                        coroutineScope.launch {
                            navigator.navigateTo(
                                pane = ListDetailPaneScaffoldRole.Detail,
                                contentKey = SettingNav.Issue,
                            )
                        }
                    },
                ),
                SettingList(
                    icon = Icons.Default.Contrast,
                    label = "FAQ",
                    onClick = {
                        coroutineScope.launch {
                            navigator.navigateTo(
                                pane = ListDetailPaneScaffoldRole.Detail,
                                contentKey = SettingNav.Faq,
                            )
                        }
                    },
                ),
                SettingList(
                    icon = Icons.Default.Contrast,
                    label = "About",
                    onClick = {
                        coroutineScope.launch {
                            navigator.navigateTo(
                                pane = ListDetailPaneScaffoldRole.Detail,
                                contentKey = SettingNav.About,
                            )
                        }
                    },
                ),
            ),
    )
    ListDetailPaneScaffold(
        modifier = Modifier,
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                SettingListScreen(
                    modifier = modifier,
                    settingsMap = settingsMap,
                    onDrawer = onDrawer,
                )
            }
        },
        detailPane = {
            AnimatedPane {
                SettingContentScreen(
                    modifier = modifier,
                    onBack = if (navigator.canNavigateBack()) {
                        {
                            coroutineScope.launch {
                                navigator.navigateBack()
                            }
                        }
                    } else {
                        null
                    },
                    settingNav = navigator.currentDestination?.contentKey ?: SettingNav.Appearance,
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingListScreen(
    modifier: Modifier = Modifier,
    settingsMap: Map<String, List<SettingList>>,
    onDrawer: (() -> Unit)?,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            KmtTopAppBar(
                title = { Text("Setting") },
                navigationIcon = {
                    if (onDrawer != null) {
                        KmtIconButton(onClick = onDrawer) {
                            Icon(
                                imageVector = KmtIcons.Menu,
                                contentDescription = "menu",
                            )
                        }
                    }
                },
            )
        },
        containerColor = Color.Transparent,
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            settingsMap.forEach { (title, settingList) ->
                item {
                    Text(title, style = MaterialTheme.typography.titleSmall)
                }
                items(settingList) { setting ->
                    ListItem(
                        modifier = Modifier.clickable { setting.onClick() },
                        leadingContent = { Icon(setting.icon, contentDescription = setting.label) },
                        headlineContent = {
                            Text(setting.label)
                        },
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingContentScreen(
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)?,
    settingNav: SettingNav,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            KmtTopAppBar(
                title = { Text(settingNav.name) },
                navigationIcon = {
                    if (onBack != null) {
                        KmtIconButton(onClick = onBack) {
                            Icon(
                                imageVector = KmtIcons.ArrowBack,
                                contentDescription = "back",
                            )
                        }
                    }
                },
            )
        },
        containerColor = Color.Transparent,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
        }
    }
}

@Preview()
@Composable
internal fun SettingScreenPreview() {
    val settingState = SettingState(
        contrast = 0,
        darkThemeConfig = DarkThemeConfig.DARK,
    )
    SharedTransitionContainer {
        SettingScreen(
            modifier = Modifier,
            onDrawer = {},
        )
    }
}

data class ContrastOption(
    val id: Int, // Unique identifier for the option
    val icon: ImageVector,
    val contentDescription: String,
    val label: String, // Optional label below the icon
)

@Composable
fun ContrastTimeline(
    modifier: Modifier = Modifier,
    options: List<ContrastOption>,
    selectedOptionId: Int,
    onOptionSelected: (Int) -> Unit,
    iconSize: Dp = 24.dp,
    lineThickness: Dp = 2.dp,
    selectedIndicatorSize: Dp = 32.dp,
    unselectedIndicatorSize: Dp = 28.dp,
    lineColor: Color = MaterialTheme.colorScheme.outlineVariant,
    selectedIconColor: Color = MaterialTheme.colorScheme.primary,
    unselectedIconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    selectedBackgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    unselectedBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = option.id == selectedOptionId
            val currentIndicatorSize = if (isSelected) selectedIndicatorSize else unselectedIndicatorSize

            // Clickable area for the icon and its surrounding
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f) // Distribute space
                    .clickable(
                        onClick = { onOptionSelected(option.id) },
                        role = Role.RadioButton, // Good for accessibility
                        onClickLabel = "Select ${option.label}",
                    )
                    .padding(horizontal = 4.dp), // Padding around each clickable item
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Line before the first item (conditionally)
                    if (index > 0) {
                        HorizontalDivider(
                            modifier = Modifier
                                .weight(1f) // Flexible line
                                .height(lineThickness),
                            color = lineColor,
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f)) // Placeholder for even distribution
                    }

                    Box(
                        modifier = Modifier
                            .size(currentIndicatorSize)
                            .clip(CircleShape)
                            .background(if (isSelected) selectedBackgroundColor else unselectedBackgroundColor)
                            .border(
                                width = if (isSelected) 1.5.dp else 0.dp,
                                color = if (isSelected) selectedIconColor else Color.Transparent,
                                shape = CircleShape,
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = option.icon,
                            contentDescription = option.contentDescription,
                            modifier = Modifier.size(iconSize),
                            tint = if (isSelected) selectedIconColor else unselectedIconColor,
                        )
                    }

                    // Line after the last item (conditionally)
                    if (index < options.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier
                                .weight(1f) // Flexible line
                                .height(lineThickness),
                            color = lineColor,
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f)) // Placeholder for even distribution
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ContrastTimelinePreview() {
    val contrastOptions = listOf(
        ContrastOption(
            id = 0,
            icon = Icons.Filled.LightMode, // Representing "Low Contrast"
            contentDescription = "Low Contrast",
            label = "Low",
        ),
        ContrastOption(
            id = 1,
            icon = Icons.Filled.Contrast, // Representing "Standard Contrast"
            contentDescription = "Standard Contrast",
            label = "Standard",
        ),
        ContrastOption(
            id = 2,
            icon = Icons.Filled.DarkMode, // Representing "High Contrast"
            contentDescription = "High Contrast",
            label = "High",
        ),
    )

    MaterialTheme {
        // Use your app's theme
        ContrastTimeline(
            options = contrastOptions,
            selectedOptionId = 0,
            onOptionSelected = { },
        )
    }
}
