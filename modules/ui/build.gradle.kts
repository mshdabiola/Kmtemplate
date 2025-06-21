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
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")

//    id("mshdabiola.android.library.jacoco")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.mshdabiola.ui"

}

dependencies {
  androidTestImplementation(projects.modules.testing)

}

kotlin {
    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(project(":modules:analytics"))
                implementation(project(":modules:designsystem"))
                implementation(project(":modules:model"))
                api(libs.coil.kt)
                api(libs.coil.kt.compose)
                api(libs.coil.kt.svg)
                api(libs.coil.kt.network)

                api(compose.components.resources)


            }
        }
    }
}