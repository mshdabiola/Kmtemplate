/*
 * Copyright (C) 2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
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
import com.mshdabiola.kotlinmultiplatformtemplate.di.appModule
import com.mshdabiola.kotlinmultiplatformtemplate.ui.KotlinMultiplatformTemplate
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
            KotlinMultiplatformTemplate()
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
