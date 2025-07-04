/*
 * Copyright (C) 2022-2025 MshdAbiola
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
package com.mshdabiola.kotlin_multiplatform_template.ui

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
import com.mshdabiola.designsystem.component.KmtBackground
import com.mshdabiola.designsystem.component.KmtGradientBackground
import com.mshdabiola.designsystem.component.KmtTopAppBar
import com.mshdabiola.designsystem.icon.KmtIcons
import com.mshdabiola.designsystem.theme.GradientColors
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.designsystem.theme.LocalGradientColors
import com.mshdabiola.detail.navigation.Detail
import com.mshdabiola.detail.navigation.navigateToDetail
import com.mshdabiola.kotlin_multiplatform_template.MainActivityUiState
import com.mshdabiola.kotlin_multiplatform_template.MainAppViewModel
import com.mshdabiola.kotlin_multiplatform_template.navigation.HydraulicAppNavHost
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
fun KotlinMultiplatformTemplate() {
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
        KmtTheme(
            androidTheme = shouldUseAndroidTheme(uiState),
            darkTheme = darkTheme,
            disableDynamicTheming = shouldDisableDynamicTheming(uiState),
        ) {
            KmtBackground {
                KmtGradientBackground(
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
                                            KmtTopAppBar(
                                                titleRes = "Note",
                                                navigationIcon = KmtIcons.Person,
                                                navigationIconContentDescription = "",
                                                actionIcon = KmtIcons.Settings,
                                                actionIconContentDescription = "se",
                                                onActionClick = { appState.navController.navigateToSetting() },
                                            )
                                        } else {
                                            TopAppBar(
                                                title = { Text("Setting") },
                                                navigationIcon = {
                                                    IconButton(onClick = { appState.navController.popBackStack() }) {
                                                        Icon(KmtIcons.ArrowBack, "back")
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
                                                    KmtIcons.Add,
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
