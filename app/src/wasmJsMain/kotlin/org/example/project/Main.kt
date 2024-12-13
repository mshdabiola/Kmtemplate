package org.example.project

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import co.touchlab.kermit.Logger
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.mshdabiola.skeletonapp.di.appModule
import com.mshdabiola.skeletonapp.ui.SkeletonApp
import kotlinx.browser.document
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val logger = Logger(
        loggerConfigInit(platformLogWriter(), Writer()),
        "DesktopLogger,",
    )
    val logModule = module {
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
        ComposeViewport(document.body!!) {
            SkeletonApp()
        }
    } catch (e: Exception) {
        logger.e("crash exceptions", e)
        throw e
    }

}
