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
import org.gradle.kotlin.dsl.implementation

/*
 *abiola 2024
 */
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")

}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.mshdabiola.designsystem"
}

dependencies {
//    lintPublish(projects.lint)

    debugApi(compose.uiTooling)
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api(compose.materialIconsExtended)
                api(compose.components.resources)
                api(compose.material3AdaptiveNavigationSuite)
                api(compose.components.uiToolingPreview)
                api(libs.kotlinx.collection.immutable)
                api(libs.lifecycle.runtime.compose)

//                implementation("org.jetbrains.compose.material:material-icons-core:1.7.3")
                implementation(project(":modules:model"))
                api(libs.androidx.navigation.compose.get())

                api(libs.koin.compose)
                api(libs.koin.composeVM)

            }
        }
        val androidMain by getting {
            dependencies {
                api(compose.preview)
                api(libs.androidx.lifecycle.runtimeCompose)
                api(libs.androidx.lifecycle.viewModelCompose)
                implementation(libs.androidx.ui.text.google.fonts)

            }
        }

        val jvmMain by getting {
            dependencies {
                api(compose.preview)
                api(libs.kotlinx.coroutines.swing)
            }
        }


    }
}