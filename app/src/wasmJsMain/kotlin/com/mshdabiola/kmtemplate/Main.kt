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
package com.mshdabiola.kmtemplate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.NavHostController
import androidx.navigation.bindToBrowserNavigation
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.mshdabiola.designsystem.component.SplashScreen
import com.mshdabiola.kmtemplate.di.appModule
import com.mshdabiola.kmtemplate.ui.KmtApp
import com.mshdabiola.kmtemplate.ui.KmtAppState
import com.mshdabiola.kmtemplate.ui.rememberKmtAppState
import com.mshdabiola.model.BuildConfig
import com.mshdabiola.model.Platform
import kotlinx.browser.document
import kotlinx.coroutines.delay
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Sets up and launches the Compose-based web UI.
 *
 * Initializes a ComposeViewport attached to the browser document body, computes the current
 * window size class, creates a navigation controller and remembered app state, and composes
 * the app UI. Displays a splash screen for two seconds after composition; once the splash is
 * hidden, binds the navigation controller to browser history so browser navigation controls
 * reflect app navigation.
 *
 * Side effects:
 * - Attaches the Compose UI root to `document.body` (non-null asserted).
 * - Starts a 2-second timer to hide the splash screen.
 * - Binds the NavHostController to browser navigation after the splash is dismissed.
 */
@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalBrowserHistoryApi::class,
)
fun mainApp() {
    ComposeViewport(document.body!!) {
        val show = remember { mutableStateOf(true) }
        val windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val navController: NavHostController = rememberNavController()
        val appState: KmtAppState = rememberKmtAppState(
            navController = navController,
            windowSizeClass = windowSizeClass,
        )
        LaunchedEffect(Unit) {
            delay(2000)
            show.value = false
        }
        Box(Modifier.fillMaxSize()) {
            KmtApp(windowSizeClass = windowSizeClass, appState = appState)
            if (show.value) {
                SplashScreen(brand = BuildConfig.BRAND_NAME)
            } else {
                LaunchedEffect(navController) {
                    navController.bindToBrowserNavigation(null)
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val logger =
        Logger(
            loggerConfigInit(
                minSeverity = Severity.Error,
                logWriters = arrayOf(platformLogWriter(DefaultFormatter)),
            ),
        )
    val applicationModule = module {
        single { Platform.Web } bind Platform::class
        single {
            logger
        }
    }

    startKoin {
        modules(
            appModule,
            applicationModule,
        )
    }
    mainApp()
}
