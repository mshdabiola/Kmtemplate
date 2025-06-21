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
import org.jetbrains.compose.ExperimentalComposeLibrary

/*
 *abiola 2024
 */
plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")
}

android {
    namespace = "com.mshdabiola.testing"
}
dependencies {
//
//
    debugApi(libs.androidx.compose.ui.testManifest)
//
}
kotlin {

    sourceSets {
        @OptIn(ExperimentalComposeLibrary::class) val commonMain by getting {
            dependencies {
                api(kotlin("test"))
//                api(compose.uiTest)
                api(projects.modules.analytics)
                api(projects.modules.data)
                api(projects.modules.model)
                api(projects.modules.designsystem)

                api(libs.kotlinx.coroutines.test)
                api(libs.turbine)
                api(libs.koin.test)
            }
        }
        val nonJsMain by getting {
            dependencies {
                api(libs.koin.test.junit)
            }
        }

        val jvmMain by getting {
            dependencies {
                api(compose.desktop.currentOs)

                api(compose.desktop.uiTestJUnit4)
            }
        }


        val androidMain by getting {
            dependencies {
                api(libs.androidx.test.core)
               // api(libs.androidx.test.espresso.core)
                //api(libs.androidx.test.runner)
               // api(libs.androidx.test.rules)
                api(libs.androidx.compose.ui.test)
                api(libs.koin.android.test)
            }
        }
    }
}