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

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.setting.detailscreen.openUrl
import com.mshdabiola.ui.SharedTransitionContainer
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onDrawer: (() -> Unit)?,
    settingState: SettingState,
    onContrastChange: (Int) -> Unit = {},
    onDarkModeChange: (DarkThemeConfig) -> Unit = {},

) {
    val navigator = rememberListDetailPaneScaffoldNavigator<SettingNav>()
    val coroutineScope = rememberCoroutineScope()

    val seeMap = SettingNav
        .entries
        .groupBy { it.segment }

    val openUri = openUrl("https://github.com/mshdabiola/KotlinMultiplatformTemplate/issues")

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
                        if (it == SettingNav.Issue) {
                            openUri()
                        } else {
                            coroutineScope.launch {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail,
                                    contentKey = it,
                                )
                            }
                        }
                    },
                )
            }
        },
        detailPane = {
            AnimatedPane {
                SettingDetailScreen(
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
                    settingState = settingState,
                    onContrastChange = onContrastChange,
                    onDarkModeChange = onDarkModeChange,
                )
            }
        },
    )
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
            settingState = settingState,
        )
    }
}
