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
package com.mshdabiola.hydraulicapp.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mshdabiola.analytics.AnalyticsHelper
import com.mshdabiola.analytics.LocalAnalyticsHelper
import com.mshdabiola.designsystem.component.HyaBackground
import com.mshdabiola.designsystem.component.HyaGradientBackground
import com.mshdabiola.designsystem.component.HyaTopAppBar
import com.mshdabiola.designsystem.icon.HyaIcons
import com.mshdabiola.designsystem.theme.GradientColors
import com.mshdabiola.designsystem.theme.HyaTheme
import com.mshdabiola.designsystem.theme.LocalGradientColors
import com.mshdabiola.detail.navigation.Detail
import com.mshdabiola.detail.navigation.navigateToDetail
import com.mshdabiola.hydraulicapp.MainActivityUiState
import com.mshdabiola.hydraulicapp.MainAppViewModel
import com.mshdabiola.hydraulicapp.navigation.HydraulicAppNavHost
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.setting.navigation.navigateToSetting
import com.mshdabiola.ui.semanticsCommon
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(
    KoinExperimentalAPI::class,
    ExperimentalMaterial3Api::class,
)
@Composable
fun HydraulicApp() {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()

    val appState =
        rememberHydraulicAppState(
            windowSizeClass = windowAdaptiveInfo.windowSizeClass,
        )
    val shouldShowGradientBackground = false

    val viewModel: MainAppViewModel = koinViewModel()
    val analyticsHelper = koinInject<AnalyticsHelper>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val darkTheme = shouldUseDarkTheme(uiState)

    CompositionLocalProvider(LocalAnalyticsHelper provides analyticsHelper) {
        HyaTheme(
            androidTheme = shouldUseAndroidTheme(uiState),
            darkTheme = darkTheme,
            disableDynamicTheming = shouldDisableDynamicTheming(uiState),
        ) {
            HyaBackground {
                HyaGradientBackground(
                    gradientColors =
                    if (shouldShowGradientBackground) {
                        LocalGradientColors.current
                    } else {
                        GradientColors()
                    },
                ) {
                    val snackbarHostState = remember { SnackbarHostState() }
                    PermanentNavigationDrawer(
                        drawerContent = {
                            if (appState.shouldShowDrawer) {
                                CommonNavigation(
                                    modifier = Modifier.width(300.dp).fillMaxHeight(),
                                    navController = appState.navController,
                                )
                            }
                        },
                    ) {
                        Row {
                            if (appState.shouldShowNavRail) {
                                CommonRail(
                                    modifier = Modifier.width(120.dp),
                                    navController = appState.navController,
                                )
                            }
                            Scaffold(
                                modifier = Modifier.semanticsCommon {},
                                containerColor = Color.Transparent,
                                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                                snackbarHost = { SnackbarHost(snackbarHostState) },
                                topBar = {
                                    if (appState.shouldShowTopBar) {
                                        if (appState.isMain) {
                                            HyaTopAppBar(
                                                titleRes = "Note",
                                                navigationIcon = HyaIcons.Person,
                                                navigationIconContentDescription = "",
                                                actionIcon = HyaIcons.Settings,
                                                actionIconContentDescription = "se",
                                                onActionClick = { appState.navController.navigateToSetting() },
                                            )
                                        } else {
                                            TopAppBar(
                                                title = { Text("Setting") },
                                                navigationIcon = {
                                                    IconButton(onClick = { appState.navController.popBackStack() }) {
                                                        Icon(HyaIcons.ArrowBack, "back")
                                                    }
                                                },
                                            )
                                        }
                                    }
                                },
                                floatingActionButton = {
                                    if (appState.isMain) {
                                        ExtendedFloatingActionButton(
                                            modifier = Modifier.testTag("main:add"),
                                            text = { Text("Add Note") },
                                            icon = {
                                                Icon(
                                                    HyaIcons.Add,
                                                    contentDescription = "add",
                                                )
                                            },
                                            onClick = {
                                                appState.navController.navigateToDetail(
                                                    Detail(-1),
                                                )
                                            },
                                        )
                                    }
                                },
                                bottomBar = {
                                    if (appState.shouldShowBottomBar) {
                                        CommonBar(navController = appState.navController)
                                    }
                                },
                            ) { padding ->

                                Column(
                                    Modifier
                                        .fillMaxSize()
                                        .padding(padding)
                                        .consumeWindowInsets(padding)
                                        .windowInsetsPadding(
                                            WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal),
                                        ),
                                ) {
                                    HydraulicAppNavHost(
                                        appState = appState,
                                        onShowSnackbar = { message, action ->
                                            snackbarHostState.showSnackbar(
                                                message = message,
                                                actionLabel = action,
                                                duration = SnackbarDuration.Short,
                                            ) == SnackbarResult.ActionPerformed
                                        },
                                    )
//                                            }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// @Composable
// private fun chooseTheme(uiState: MainActivityUiState): ThemeBrand =
//    when (uiState) {
//        MainActivityUiState.Loading -> ThemeBrand.DEFAULT
//        is MainActivityUiState.Success -> uiState.userData.themeBrand
//    }

@Composable
private fun shouldUseAndroidTheme(uiState: MainActivityUiState): Boolean =
    when (uiState) {
        MainActivityUiState.Loading -> false
        is MainActivityUiState.Success ->
            when (uiState.userData.themeBrand) {
                ThemeBrand.DEFAULT -> false
                ThemeBrand.GREEN -> true
            }
    }

@Composable
private fun shouldDisableDynamicTheming(uiState: MainActivityUiState): Boolean =
    when (uiState) {
        MainActivityUiState.Loading -> false
        is MainActivityUiState.Success -> !uiState.userData.useDynamicColor
    }

@Composable
fun shouldUseDarkTheme(uiState: MainActivityUiState): Boolean =
    when (uiState) {
        MainActivityUiState.Loading -> isSystemInDarkTheme()
        is MainActivityUiState.Success ->
            when (uiState.userData.darkThemeConfig) {
                DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
                DarkThemeConfig.LIGHT -> false
                DarkThemeConfig.DARK -> true
            }
    }
