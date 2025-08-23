/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import androidx.compose.ui.platform.testTag
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.testtag.SettingScreenTestTags
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
    onGradientBackgroundChange: (Boolean) -> Unit = {},
    onLanguageChange: (String) -> Unit = {},
    openUrl: (String) -> Unit = {},
    openEmail: (String, String, String) -> Unit = { _, _, _ -> },
    onSetUpdateDialog: (Boolean) -> Unit = {},
    onSetUpdateFromPreRelease: (Boolean) -> Unit = {},
    onCheckForUpdate: () -> Unit = {},

) {
    val navigator = rememberListDetailPaneScaffoldNavigator<SettingNav>()
    val coroutineScope = rememberCoroutineScope()

    val settingsBySegment = SettingNav
        .entries
        .groupBy { it.segment }

    ListDetailPaneScaffold(
        modifier = modifier.testTag(SettingScreenTestTags.SCREEN_ROOT), // Apply the tag here
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                SettingListScreen(
                    modifier = Modifier, // Pass modifier if needed from here
                    settingsMap = settingsBySegment,
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
                SettingDetailScreen(
                    modifier = Modifier, // Pass modifier if needed from here
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
                    onGradientBackgroundChange = onGradientBackgroundChange,
                    onLanguageChange = onLanguageChange,
                    openUrl = openUrl,
                    openEmail = openEmail,
                    onSetUpdateDialog = onSetUpdateDialog,
                    onSetUpdateFromPreRelease = onSetUpdateFromPreRelease,
                    onCheckForUpdate = onCheckForUpdate,
                )
            }
        },
    )
}

@Preview()
@Composable
internal fun SettingScreenPreview() {
    val settingState = SettingState()
    SharedTransitionContainer {
        SettingScreen(
            modifier = Modifier,
            onDrawer = {},
            settingState = settingState,
        )
    }
}
