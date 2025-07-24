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
package com.mshdabiola.kotlinmultiplatformtemplate.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mshdabiola.analytics.AnalyticsHelper
import com.mshdabiola.analytics.LocalAnalyticsHelper
import com.mshdabiola.designsystem.component.KmtBackground
import com.mshdabiola.designsystem.component.KmtGradientBackground
import com.mshdabiola.designsystem.theme.GradientColors
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.designsystem.theme.LocalGradientColors
import com.mshdabiola.kotlinmultiplatformtemplate.MainActivityUiState
import com.mshdabiola.kotlinmultiplatformtemplate.MainAppViewModel
import com.mshdabiola.kotlinmultiplatformtemplate.navigation.KotlinMultiplatformTemplateAppNavHost
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.ui.LocalSharedTransitionScope
import com.mshdabiola.ui.semanticsCommon
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(
    KoinExperimentalAPI::class,
    ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class,
    ExperimentalMaterial3ExpressiveApi::class,
)
@Composable
fun KmtApp() {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()

    val appState =
        rememberKmtAppState(
            windowSizeClass = windowAdaptiveInfo.windowSizeClass,
        )
    val shouldShowGradientBackground = false

    val viewModel: MainAppViewModel = koinViewModel()
    val analyticsHelper = koinInject<AnalyticsHelper>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val darkTheme = shouldUseDarkTheme(uiState)
    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalAnalyticsHelper provides analyticsHelper,
            LocalSharedTransitionScope provides this,
        ) {
            KmtTheme(
                contrast = chooseContrast(uiState),
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
                        KmtScaffold(

                            modifier = Modifier.semanticsCommon {},
                            containerColor = Color.Transparent,
                            contentWindowInsets = WindowInsets(0, 0, 0, 0),
                            appState = appState,
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
                                KotlinMultiplatformTemplateAppNavHost(appState = appState)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun chooseContrast(uiState: MainActivityUiState): Int =
    when (uiState) {
        MainActivityUiState.Loading -> 0
        is MainActivityUiState.Success -> uiState.userData.contrast
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
