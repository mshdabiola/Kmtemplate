package com.mshdabiola.desktop

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.mshdabiola.designsystem.drawable.defaultAppIcon
import com.mshdabiola.designsystem.string.appName
import com.mshdabiola.skeletonapp.di.appModule
import com.mshdabiola.skeletonapp.ui.SkeletonApp
import org.koin.core.context.GlobalContext.startKoin
import java.io.File
import java.io.PrintWriter

fun mainApp() {

    application {
        val windowState = rememberWindowState(
            size = DpSize(width = 1100.dp, height = 600.dp),
            placement = WindowPlacement.Maximized,
            position = WindowPosition.Aligned(Alignment.Center),
        )



        val version ="1.0.7"
        Window(
            onCloseRequest = ::exitApplication,
            title = "$appName v$version",
            icon = defaultAppIcon,
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


        mainApp()
    } catch (e: Exception) {
        e.printStackTrace(PrintWriter(file.bufferedWriter()))
        throw e
    }
}
