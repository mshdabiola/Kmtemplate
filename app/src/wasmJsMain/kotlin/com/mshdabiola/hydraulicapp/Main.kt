package com.mshdabiola.hydraulicapp

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
import com.mshdabiola.hydraulicapp.di.appModule
import com.mshdabiola.hydraulicapp.ui.HydraulicApp
import com.mshdabiola.ui.SplashScreen
import kotlinx.browser.document
import kotlinx.coroutines.delay
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
            HydraulicApp()
            if (show.value) {
                SplashScreen()
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
