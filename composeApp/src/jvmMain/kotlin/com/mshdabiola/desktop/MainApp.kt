package com.mshdabiola.desktop

import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.mshdabiola.skeletonapp.di.appModule
import com.mshdabiola.skeletonapp.ui.SkeletonApp
import org.koin.core.context.GlobalContext.startKoin
import java.io.File
import java.io.PrintWriter
import java.nio.file.Paths
import kotlin.io.path.exists
import kotlin.io.path.inputStream

fun mainApp(appArgs: AppArgs) {
    application {
        val windowState = rememberWindowState(
            size = DpSize(width = 1100.dp, height = 600.dp),
            placement = WindowPlacement.Maximized,
            position = WindowPosition.Aligned(Alignment.Center),
        )

        val appIcon = remember {
            System.getProperty("app.dir")
                ?.let { Paths.get(it, "icon-square-512.png") }
                ?.takeIf { it.exists() }
                ?.inputStream()
                ?.buffered()
                ?.use { BitmapPainter(loadImageBitmap(it)) }
        }

        Window(
            onCloseRequest = ::exitApplication,
            title = "${appArgs.appName} (${appArgs.version})",
            icon = appIcon,
            state = windowState,
        ) {
            SkeletonApp()
        }
    }
}

fun main() {
    val path = File("${System.getProperty("user.home")}/AppData/Local/hydraulic")
    if (path.exists().not()) {
        path.mkdirs()
    }
    val file = File(path, "main error.txt")

    try {
        startKoin {
            modules(appModule)
        }

        val appArgs = AppArgs(
            appName = "Skeleton App", // To show on title bar
            version = "v1.0.6", // To show on title inside brackets
            versionCode = 100, // To compare with latest version code (in case if you want to prompt update)
        )

        mainApp(appArgs)
    } catch (e: Exception) {
//        file.bufferedWriter()
//            .write("Catch")
        // file.writeText(e.stackTraceToString())
        e.printStackTrace(PrintWriter(file.bufferedWriter()))
//        file.close()
        throw e
    }
}
