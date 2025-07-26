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
import kotlinmultiplatformtemplate.features.setting.generated.resources.Res
import kotlinmultiplatformtemplate.features.setting.generated.resources.general
import kotlinmultiplatformtemplate.features.setting.generated.resources.screen_name
import kotlinmultiplatformtemplate.features.setting.generated.resources.segment
import kotlinmultiplatformtemplate.features.setting.generated.resources.support
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class SettingNav(val segment: Int, val index: Int) {
    // general
    Appearance(0, 0),

    // support
    Issue(1, 0),
    Faq(1, 1),

    About(1, 2),
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onDrawer: (() -> Unit)?,

) {
    val navigator = rememberListDetailPaneScaffoldNavigator<SettingNav>()
    val coroutineScope = rememberCoroutineScope()

    val seeMap = SettingNav
        .entries
        .groupBy { it.segment }

    ListDetailPaneScaffold(
        modifier = Modifier,
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                SettingListScreen(
                    modifier = modifier,
                    settingsMap = seeMap,
                    onDrawer = onDrawer,
                    onSettingClick = {
                        coroutineScope.launch {
                            navigator.navigateTo(
                                pane = ListDetailPaneScaffoldRole.Detail,
                                contentKey = it,
                            )
                        }
                    },
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
    settingsMap: Map<Int, List<SettingNav>>,
    onDrawer: (() -> Unit)?,
    onSettingClick: (SettingNav) -> Unit = {},
) {
    val segmentArrayString = stringArrayResource(Res.array.segment)

    val generalIcon = listOf(
        KmtIcons.Appearence,
    )
    val generalArrayString = stringArrayResource(Res.array.general)

    val supportIcon = listOf(
        KmtIcons.BugReport,
        KmtIcons.Faq,
        KmtIcons.About,
    )
    val supportArrayString = stringArrayResource(Res.array.support)

    val stringArray = listOf(
        generalArrayString,
        supportArrayString,
    )
    val iconArray = listOf(
        generalIcon,
        supportIcon,
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            KmtTopAppBar(
                title = { Text(stringResource(Res.string.screen_name)) },
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
            settingsMap.forEach { (index, settingList) ->
                item {
                    Text(segmentArrayString[index], style = MaterialTheme.typography.titleSmall)
                }
                items(settingList) { setting ->
                    ListItem(
                        modifier = Modifier.clickable { onSettingClick(setting) },
                        leadingContent = {
                            Icon(
                                iconArray[setting.segment][setting.index],
                                contentDescription = stringArray[setting.segment][setting.index],
                            )
                        },
                        headlineContent = {
                            Text(stringArray[setting.segment][setting.index])
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
    val generalArrayString = stringArrayResource(Res.array.general)

    val supportArrayString = stringArrayResource(Res.array.support)

    val stringArray = listOf(
        generalArrayString,
        supportArrayString,
    )
    Scaffold(
        modifier = modifier,
        topBar = {
            KmtTopAppBar(
                title = { Text(stringArray[settingNav.segment][settingNav.index]) },
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
