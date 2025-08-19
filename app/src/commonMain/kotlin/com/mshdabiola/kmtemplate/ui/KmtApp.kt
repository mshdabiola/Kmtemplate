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
package com.mshdabiola.kmtemplate.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.mshdabiola.analytics.AnalyticsHelper
import com.mshdabiola.analytics.LocalAnalyticsHelper
import com.mshdabiola.designsystem.component.KmtBackground
import com.mshdabiola.designsystem.component.KmtGradientBackground
import com.mshdabiola.designsystem.strings.KmtStrings
import com.mshdabiola.designsystem.theme.GradientColors
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.designsystem.theme.LocalGradientColors
import com.mshdabiola.designsystem.theme.extendedColorScheme
import com.mshdabiola.kmtemplate.LocalAppLocale
import com.mshdabiola.kmtemplate.MainActivityUiState
import com.mshdabiola.kmtemplate.MainAppViewModel
import com.mshdabiola.kmtemplate.navigation.KmtNavHost
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ReleaseInfo
import com.mshdabiola.model.Type
import com.mshdabiola.setting.navigation.getWindowRepository
import com.mshdabiola.ui.LocalSharedTransitionScope
import com.mshdabiola.ui.ReleaseUpdateDialog
import com.mshdabiola.ui.semanticsCommon
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

// Test Tags for KmtApp
object KmtAppTestTags {
    const val APP_ROOT_LAYOUT = "kmt_app:root_layout" // For SharedTransitionLayout or KmtBackground
    const val GRADIENT_BACKGROUND = "kmt_app:gradient_background"
    const val MAIN_SCAFFOLD = "kmt_app:main_scaffold" // Instance of KmtScaffold
    const val NAV_HOST = "kmt_app:nav_host"
}

@OptIn(
    KoinExperimentalAPI::class,
    ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class,
    ExperimentalMaterial3ExpressiveApi::class,
)
@Composable
fun KmtApp(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    appState: KmtAppState = rememberKmtAppState(
        windowSizeClass = windowSizeClass,
    ),
) {
    val viewModel: MainAppViewModel = koinViewModel()
    val analyticsHelper = koinInject<AnalyticsHelper>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val darkTheme = shouldUseDarkTheme(uiState)
    val languageCode = getLanguage(uiState)
    var releaseInfo by remember { mutableStateOf<ReleaseInfo.NewUpdate?>(null) }
    val windowRepository = getWindowRepository()
    val currentVersion = KmtStrings.version

    LaunchedEffect(Unit) {
        val info = viewModel.getLatestReleaseInfo(currentVersion).await()
        when (info) {
            is ReleaseInfo.NewUpdate -> {
                viewModel.log("$info")
                releaseInfo = info
            }
            is ReleaseInfo.Error -> {
                viewModel.log("$info")
            }
            is ReleaseInfo.UpToDate -> {
                viewModel.log("$info")
            }
        }
    }
    SharedTransitionLayout(
        modifier = Modifier.testTag(KmtAppTestTags.APP_ROOT_LAYOUT), // Tagging the outer layout
    ) {
        CompositionLocalProvider(
            LocalAnalyticsHelper provides analyticsHelper,
            LocalSharedTransitionScope provides this,
            LocalAppLocale provides languageCode,

        ) {
            key(languageCode) {
                KmtTheme(
                    contrast = chooseContrast(uiState),
                    darkTheme = darkTheme,
                    disableDynamicTheming = shouldDisableDynamicTheming(uiState),
                ) {
                    KmtBackground {
                        // This could also be APP_ROOT_LAYOUT if preferred
                        KmtGradientBackground(
                            modifier = Modifier.testTag(KmtAppTestTags.GRADIENT_BACKGROUND),
                            gradientColors =
                            if (shouldShowGradientBackground(uiState)) {
                                LocalGradientColors.current
                            } else {
                                GradientColors()
                            },
                        ) {
                            Box {
                                KmtScaffold(
                                    modifier = Modifier
                                        .semanticsCommon {}
                                        .testTag(KmtAppTestTags.MAIN_SCAFFOLD), // Tagging the KmtScaffold instance
                                    containerColor = Color.Transparent,
                                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                                    appState = appState,
                                    snackbarHost = {
                                        SnackbarHost(
                                            appState.snackbarHostState,
                                            snackbar = { snackbarData ->
                                                KmtSnackerBar(appState.notificationType, snackbarData)
                                            },
                                        )
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
                                        KmtNavHost(
                                            appState = appState,
                                            modifier = Modifier.testTag(KmtAppTestTags.NAV_HOST), // Tagging the NavHost
                                        )
                                    }
                                }

                                if (releaseInfo != null) {
                                    val info = releaseInfo!!
                                    ReleaseUpdateDialog(
                                        releaseInfo = info,
                                        onDownloadClick = { windowRepository.openUrl(info.asset) },
                                        onDismissRequest = { releaseInfo = null },
                                    )
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
private fun chooseContrast(uiState: MainActivityUiState): Int =
    when (uiState) {
        MainActivityUiState.Loading -> 0
        is MainActivityUiState.Success -> uiState.userSettings.contrast
    }

@Composable
private fun shouldDisableDynamicTheming(uiState: MainActivityUiState): Boolean =
    when (uiState) {
        MainActivityUiState.Loading -> false
        is MainActivityUiState.Success -> !uiState.userSettings.useDynamicColor
    }

@Composable
fun shouldUseDarkTheme(uiState: MainActivityUiState): Boolean =
    when (uiState) {
        MainActivityUiState.Loading -> isSystemInDarkTheme()
        is MainActivityUiState.Success ->
            when (uiState.userSettings.darkThemeConfig) {
                DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
                DarkThemeConfig.LIGHT -> false
                DarkThemeConfig.DARK -> true
            }
    }

@Composable
fun shouldShowGradientBackground(uiState: MainActivityUiState): Boolean =
    when (uiState) {
        MainActivityUiState.Loading -> false
        is MainActivityUiState.Success ->
            uiState.userSettings.shouldShowGradientBackground
    }

@Composable
fun getLanguage(uiState: MainActivityUiState): String =
    when (uiState) {
        MainActivityUiState.Loading -> "en"
        is MainActivityUiState.Success ->
            uiState.userSettings.language
    }

@Composable
fun KmtSnackerBar(type: Type, snackbarData: SnackbarData) {
    val containerColor: Color = when (type) {
        Type.Default -> SnackbarDefaults.color
        Type.Error -> MaterialTheme.colorScheme.errorContainer
        Type.Success -> extendedColorScheme.success.colorContainer
        Type.Warning -> extendedColorScheme.warning.colorContainer
    }
    val contentColor: Color = when (type) {
        Type.Default -> SnackbarDefaults.contentColor
        Type.Error -> MaterialTheme.colorScheme.onErrorContainer
        Type.Success -> extendedColorScheme.success.onColorContainer
        Type.Warning -> extendedColorScheme.warning.onColorContainer
    }
    val actionColor: Color = when (type) {
        Type.Default -> SnackbarDefaults.actionColor
        Type.Error -> MaterialTheme.colorScheme.error
        Type.Success -> extendedColorScheme.success.color
        Type.Warning -> extendedColorScheme.warning.color
    }
    val actionContentColor: Color = when (type) {
        Type.Default -> SnackbarDefaults.actionContentColor
        Type.Error -> MaterialTheme.colorScheme.onError
        Type.Success -> extendedColorScheme.success.onColor
        Type.Warning -> extendedColorScheme.warning.onColor
    }

    Snackbar(
        snackbarData = snackbarData,
        containerColor = containerColor,
        contentColor = contentColor,
        actionColor = actionColor,
        actionContentColor = actionContentColor,
        dismissActionContentColor = actionContentColor,
    )
}

@Preview
@Composable
fun KmtSnackerBarPreview() {
    val visuals = object : SnackbarVisuals {
        override val message: String
            get() = "Snackbar message"
        override val actionLabel: String?
            get() = "Testing"
        override val withDismissAction: Boolean
            get() = false
        override val duration: SnackbarDuration
            get() = SnackbarDuration.Short
    }
    KmtTheme {
        KmtSnackerBar(
            type = Type.Default,
            snackbarData = object : SnackbarData {
                override val visuals = visuals
                override fun performAction() {}
                override fun dismiss() {}
            },
        )
    }
}
