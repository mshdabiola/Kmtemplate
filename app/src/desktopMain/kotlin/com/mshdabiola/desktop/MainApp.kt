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
package com.mshdabiola.desktop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.mshdabiola.hydraulicapp.di.appModule
import com.mshdabiola.hydraulicapp.ui.HydraulicApp
import org.koin.core.context.GlobalContext.startKoin
import java.util.prefs.Preferences

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

        Window(
            onCloseRequest = ::exitApplication,
            title = "${appArgs.appName} (${appArgs.version})",
            icon = painterResource("launcher/system.png"),
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
            HydraulicApp()
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
