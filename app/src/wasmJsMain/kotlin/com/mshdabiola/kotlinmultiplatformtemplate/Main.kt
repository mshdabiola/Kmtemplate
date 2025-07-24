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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import co.touchlab.kermit.Logger
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.mshdabiola.kotlinmultiplatformtemplate.app.generated.resources.Res
import com.mshdabiola.kotlinmultiplatformtemplate.app.generated.resources.app_name
import com.mshdabiola.kotlinmultiplatformtemplate.di.appModule
import com.mshdabiola.kotlinmultiplatformtemplate.ui.KmtApp
import com.mshdabiola.kotlinmultiplatformtemplate.ui.SplashScreen
import kotlinx.browser.document
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

@OptIn(ExperimentalComposeUiApi::class)
fun mainApp() {
    ComposeViewport(document.body!!) {
        val version = "1.2.2"

        val show = remember { mutableStateOf(true) }
        LaunchedEffect(Unit) {
            delay(2000)
            show.value = false
        }
        Box(Modifier.fillMaxSize()) {
            KmtApp()
            if (show.value) {
                SplashScreen(
                    appName = stringResource(Res.string.app_name),
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val logger =
        Logger(
            loggerConfigInit(platformLogWriter(), Writer()),
            "DesktopLogger,",
        )
    val logModule =
        module {
            single {
                logger
            }
        }
    try {
        startKoin {
//            logger(
//                KermitKoinLogger(Logger.withTag("koin")),
//            )
            modules(
                appModule,
                logModule,
            )
        }
        mainApp()
    } catch (e: Exception) {
        logger.e("crash exceptions", e)
        throw e
    }
}
