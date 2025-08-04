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
@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.gradle.kotlin.dsl.implementation
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

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
    applyDefaultHierarchyTemplate {
        common {
            group("nonJs") {
                withAndroidTarget()
                // withIos()
                withJvm()
            }
        }
    }

    sourceSets {
         commonMain.dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.ui)
                api(compose.materialIconsExtended)
                api(compose.components.resources)
                api(compose.material3AdaptiveNavigationSuite)
                api(compose.components.uiToolingPreview)
//                api(compose.material3)


                api(libs.androidx.navigation.compose.get())
                api(libs.kotlinx.collection.immutable)
                api(libs.androidx.lifecycle.viewmodelCompose)
                api(libs.androidx.lifecycle.runtimeCompose)
                api(libs.material3)


                api(libs.koin.compose)
                api(libs.koin.composeVM)

                api(libs.coil.kt)
                api(libs.coil.kt.compose)
                api(libs.coil.kt.svg)
                api(libs.coil.kt.network)
                api(compose.components.resources)

            }

        androidMain.dependencies {

                implementation(libs.androidx.ui.text.google.fonts)

            }


//         jvmMain.dependencies {
//                api(libs.kotlinx.coroutines.swing)
//            }



    }
}
