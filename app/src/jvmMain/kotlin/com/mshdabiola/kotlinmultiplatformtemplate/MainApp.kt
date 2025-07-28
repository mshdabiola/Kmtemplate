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
package com.mshdabiola.kotlinmultiplatformtemplate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.koin.KermitKoinLogger
import co.touchlab.kermit.koin.kermitLoggerModule
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.mshdabiola.designsystem.drawable.KmtDrawable
import com.mshdabiola.designsystem.strings.KmtStrings
import com.mshdabiola.kotlinmultiplatformtemplate.di.appModule
import com.mshdabiola.kotlinmultiplatformtemplate.ui.KmtApp
import com.mshdabiola.kotlinmultiplatformtemplate.ui.SplashScreen
import com.mshdabiola.model.CustomLogWriter
import kotlinx.coroutines.delay
import org.koin.core.context.GlobalContext.startKoin

fun mainApp() {
    application {
        val windowState =
            rememberWindowState(
                size = DpSize(width = 1100.dp, height = 600.dp),
                placement = WindowPlacement.Maximized,
                position = WindowPosition.Aligned(Alignment.Center),
            )

        val version = "1.2.9"
        Window(
            onCloseRequest = ::exitApplication,
            title = "${KmtStrings.brand} v${KmtStrings.version}",
            icon = KmtDrawable.brandImage,
            state = windowState,
        ) {
            val show = remember { mutableStateOf(true) }
            LaunchedEffect(Unit) {
                delay(2000)
                show.value = false
            }
            Box(Modifier.fillMaxSize()) {
                KmtApp()
                if (show.value) {
                    SplashScreen()
                }
            }
        }
    }
}

fun main() {
    val logger =
        Logger(
            loggerConfigInit(
                minSeverity = Severity.Verbose,
                logWriters = arrayOf(platformLogWriter(DefaultFormatter), CustomLogWriter()),
            ),
        )

    startKoin {
        logger(
            KermitKoinLogger(Logger.withTag("koin")),
        )

        modules(
            appModule,
            kermitLoggerModule(logger),
        )
    }

    mainApp()
}
