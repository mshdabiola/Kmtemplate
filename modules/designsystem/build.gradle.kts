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

    debugApi(compose.uiTooling)
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.ui)
//                api(compose.material3)
                api("org.jetbrains.compose.material3:material3:1.9.0-alpha04")
                api(compose.materialIconsExtended)
                api(compose.components.resources)
                api(compose.material3AdaptiveNavigationSuite)
                api(compose.components.uiToolingPreview)
                api(libs.kotlinx.collection.immutable)
                api(libs.androidx.lifecycle.viewmodelCompose)
                api(libs.androidx.lifecycle.runtimeCompose)
                implementation(project(":modules:model"))
                api(libs.androidx.navigation.compose.get())

                api(libs.koin.compose)
                api(libs.koin.composeVM)

            }
        }
        val androidMain by getting {
            dependencies {

                implementation(libs.androidx.ui.text.google.fonts)

            }
        }

        val jvmMain by getting {
            dependencies {
                api(libs.kotlinx.coroutines.swing)
            }
        }


    }
}
