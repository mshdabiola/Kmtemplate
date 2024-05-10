/*
 *abiola 2022
 */

package com.mshdabiola.skeletonapp.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.arkivanov.decompose.DefaultComponentContext
import com.mshdabiola.analytics.AnalyticsHelper
import com.mshdabiola.analytics.LocalAnalyticsHelper
import com.mshdabiola.designsystem.component.SkBackground
import com.mshdabiola.designsystem.component.SkGradientBackground
import com.mshdabiola.designsystem.theme.GradientColors
import com.mshdabiola.designsystem.theme.LocalGradientColors
import com.mshdabiola.designsystem.theme.SkTheme
import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.mvvn.KoinCommonViewModel
import com.mshdabiola.mvvn.collectAsStateWithLifecycleCommon
import com.mshdabiola.mvvn.get
import com.mshdabiola.mvvn.semanticsCommon
import com.mshdabiola.navigation.IRootComponent
import com.mshdabiola.navigation.RootComponent
import com.mshdabiola.skeletonapp.MainActivityUiState
import com.mshdabiola.skeletonapp.MainAppViewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SkeletonApp(
    context: DefaultComponentContext,

) {
    val rootComp = remember { RootComponent(context) }
    val windowSizeClass = calculateWindowSizeClass()
    val appState = rememberSkAppState(
        windowSizeClass = windowSizeClass,
        navController = rootComp,
    )
    val shouldShowGradientBackground = false

    val viewModel: MainAppViewModel = KoinCommonViewModel()
    val analyticsHelper = get<AnalyticsHelper>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycleCommon()
    val darkTheme = shouldUseDarkTheme(uiState)

    CompositionLocalProvider(LocalAnalyticsHelper provides analyticsHelper) {
        SkTheme(
            darkTheme = darkTheme,
            themeBrand = chooseTheme(uiState),
            themeContrast = chooseContrast(uiState),
            disableDynamicTheming = shouldDisableDynamicTheming(uiState),
            useAndroidTheme = shouldUseAndroidTheme(uiState),
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

//            val isOffline by appState.isOffline.collectAsStateWithLifecycle()

                    // If user is not connected to the internet show a snack bar to inform them.
                    val notConnectedMessage = "Not connected"
//            LaunchedEffect(isOffline) {
//                if (isOffline) {
//                    snackbarHostState.showSnackbar(
//                        message = notConnectedMessage,
//                        duration = Indefinite,
//                    )
//                }
//            }

                    Scaffold(
                        modifier = Modifier.semanticsCommon {},
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        contentWindowInsets = WindowInsets(0, 0, 0, 0),
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                        floatingActionButton = {
                            if (appState.currentDestination.value.active.instance is IRootComponent.RootScreen.MainRootScreen) {
                                ExtendedFloatingActionButton(
                                    modifier = Modifier
                                        .windowInsetsPadding(WindowInsets.safeDrawing)
                                        .testTag("add"),
                                    onClick = { appState.navController.navigateToDetail() },
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Add,
                                        contentDescription = "add note",
                                    )
//                            Spacer(modifier = )
                                    Text(text = "Add note")
                                }
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
                            SkeletonAppNavHost(appState)
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
private fun chooseContrast(
    uiState: MainActivityUiState,
): Contrast = when (uiState) {
    MainActivityUiState.Loading -> Contrast.Normal
    is MainActivityUiState.Success -> uiState.userData.contrast
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
