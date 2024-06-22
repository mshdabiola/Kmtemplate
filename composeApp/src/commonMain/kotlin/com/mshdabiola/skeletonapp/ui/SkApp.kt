/*
 *abiola 2022
 */

package com.mshdabiola.skeletonapp.ui

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.navOptions
import com.mshdabiola.analytics.AnalyticsHelper
import com.mshdabiola.analytics.LocalAnalyticsHelper
import com.mshdabiola.designsystem.component.SkBackground
import com.mshdabiola.designsystem.component.SkGradientBackground
import com.mshdabiola.designsystem.theme.GradientColors
import com.mshdabiola.designsystem.theme.LocalGradientColors
import com.mshdabiola.designsystem.theme.SkTheme
import com.mshdabiola.main.navigation.MAIN_ROUTE
import com.mshdabiola.main.navigation.navigateToMain
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.setting.navigation.SETTING_ROUTE
import com.mshdabiola.setting.navigation.navigateToSetting
import com.mshdabiola.skeletonapp.MainActivityUiState
import com.mshdabiola.skeletonapp.MainAppViewModel
import com.mshdabiola.skeletonapp.navigation.SkNavHost
import com.mshdabiola.ui.CommonBar
import com.mshdabiola.ui.CommonNavigation
import com.mshdabiola.ui.CommonRail
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.semanticsCommon
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, KoinExperimentalAPI::class)
@Composable
fun SkeletonApp() {
    val windowSizeClass = calculateWindowSizeClass()
    val appState = rememberSkAppState(
        windowSizeClass = windowSizeClass,
    )
    val shouldShowGradientBackground = false
    val navigator: (String) -> Unit = {
        println("navigation $it seting is $SETTING_ROUTE")

        when (it) {
            MAIN_ROUTE -> {
                appState.navController.navigateToMain(navOptions = navOptions { })
            }

            SETTING_ROUTE -> {
                appState.navController.navigateToSetting()
            }
        }
    }

    val viewModel: MainAppViewModel = koinViewModel()
    val analyticsHelper = koinInject<AnalyticsHelper>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycleCommon()
    val darkTheme = shouldUseDarkTheme(uiState)

    CompositionLocalProvider(LocalAnalyticsHelper provides analyticsHelper) {
        SkTheme(
            darkTheme = darkTheme,
            disableDynamicTheming = shouldDisableDynamicTheming(uiState),
        ) {
            SkBackground {
                SkGradientBackground(
                    gradientColors = if (shouldShowGradientBackground) {
                        LocalGradientColors.current
                    } else {
                        GradientColors()
                    },
                ) {
                    val snackbarHostState = remember { SnackbarHostState() }

                    if (appState.shouldShowDrawer) {
                        PermanentNavigationDrawer(
                            drawerContent = {
                                CommonNavigation(
                                    modifier = Modifier
                                        .width(300.dp)
                                        .fillMaxHeight(),
                                    currentNavigation = appState.currentDestination?.route
                                        ?: "",
                                    onNavigate = navigator,
                                )
                            },
                        ) {
                            Scaffold(
                                modifier = Modifier.semanticsCommon {},
                                containerColor = Color.Transparent,
                                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                                snackbarHost = { SnackbarHost(snackbarHostState) },

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
                                    when (appState.windowSizeClass.widthSizeClass) {
                                        WindowWidthSizeClass.Compact -> {}
                                        else -> {
//                                            Row {

//                                                CommonNavigation (
//                                                    modifier = Modifier.weight(0.3f),
//                                                    currentNavigation = appState.currentDestination?.route ?:""
//                                                )
                                            SkNavHost(
//                                                    modifier = Modifier.weight(0.7f),
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
                    } else {
                        Row {
                            if (appState.shouldShowNavRail) {
                                CommonRail(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .fillMaxHeight(),
                                    currentNavigation = appState.currentDestination?.route
                                        ?: "",
                                    onNavigate = navigator,

                                )
                            }
                            Scaffold(
                                modifier = Modifier.semanticsCommon {},
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.onBackground,
                                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                                snackbarHost = { SnackbarHost(snackbarHostState) },
                                bottomBar = {
                                    if (appState.shouldShowBottomBar) {
                                        CommonBar(
                                            currentNavigation = appState.currentDestination?.route
                                                ?: "",
                                        ) { navigator(it) }
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
                                    SkNavHost(
//                                                    modifier = Modifier.weight(0.7f),
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

@Composable
private fun chooseTheme(
    uiState: MainActivityUiState,
): ThemeBrand = when (uiState) {
    MainActivityUiState.Loading -> ThemeBrand.DEFAULT
    is MainActivityUiState.Success -> uiState.userData.themeBrand
}

@Composable
private fun shouldUseAndroidTheme(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> false
    is MainActivityUiState.Success -> when (uiState.userData.themeBrand) {
        ThemeBrand.DEFAULT -> false
        ThemeBrand.GREEN -> true
    }
}

@Composable
private fun shouldDisableDynamicTheming(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> false
    is MainActivityUiState.Success -> !uiState.userData.useDynamicColor
}

@Composable
fun shouldUseDarkTheme(
    uiState: MainActivityUiState,
): Boolean =
    when (uiState) {
        MainActivityUiState.Loading -> isSystemInDarkTheme()
        is MainActivityUiState.Success -> when (uiState.userData.darkThemeConfig) {
            DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            DarkThemeConfig.LIGHT -> false
            DarkThemeConfig.DARK -> true
        }
    }
