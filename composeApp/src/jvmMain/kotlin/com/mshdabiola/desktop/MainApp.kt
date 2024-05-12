package com.mshdabiola.desktop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
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
import java.nio.file.Paths
import java.util.prefs.Preferences
import kotlin.io.path.exists
import kotlin.io.path.inputStream

// import com.toxicbakery.logging.Arbor
// import com.toxicbakery.logging.Seedling

fun mainApp(appArgs: AppArgs) {
    val preference = Preferences.userRoot() // .node("main")
    val isLightKey = "isLight"

    application {
        val windowState = rememberWindowState(
            size = DpSize(width = 1100.dp, height = 600.dp),
            placement = WindowPlacement.Maximized,
            position = WindowPosition.Aligned(Alignment.Center),
        )
        var isLight by remember { mutableStateOf(preference.getBoolean(isLightKey, false)) }

        val appIcon = remember {
            System.getProperty("app.dir")
                ?.let { Paths.get(it, "icon-512.png") }
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
//            MenuBar {
//                Menu("Theme", 'T') {
//                    if (!isLight) {
//                        Item("Light Theme") {
//                            isLight = true
//                            preference.putBoolean(isLightKey, true)
//                            preference.flush()
//                        }
//                    }
//                    if (isLight) {
//                        Item("Dark Theme") {
//                            isLight = false
//                            preference.putBoolean(isLightKey, false)
//                            preference.flush()
//                        }
//                    }
//                }
//            }
            SkeletonApp()
        }
    }
}

fun main() {
    startKoin {
        modules(appModule)
    }

    val appArgs = AppArgs(
        appName = "Skeleton App", // To show on title bar
        version = "v2.0.0", // To show on title inside brackets
        versionCode = 100, // To compare with latest version code (in case if you want to prompt update)
    )

    mainApp(appArgs)
}
