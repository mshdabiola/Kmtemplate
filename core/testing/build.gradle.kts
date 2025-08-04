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
    debugApi(libs.androidx.compose.ui.testManifest)
}
kotlin {

    sourceSets {
        @OptIn(ExperimentalComposeLibrary::class)
        commonMain.dependencies {
                api(kotlin("test"))
//                api(compose.uiTest)
                api(projects.core.data)
                api(projects.core.designsystem)

                api(libs.kotlinx.coroutines.test)
                api(libs.turbine)
                api(libs.koin.test)
                api(libs.kermit.test)
            }

         jvmMain.dependencies {
             api(libs.koin.test.junit)

             api(compose.desktop.currentOs)

                api(compose.desktop.uiTestJUnit4)
            }



         androidMain.dependencies {
                api(libs.androidx.test.core)
               // api(libs.androidx.test.espresso.core)
                //api(libs.androidx.test.runner)
               // api(libs.androidx.test.rules)
                api(libs.androidx.compose.ui.test)
                api(libs.koin.android.test)
            }

    }
}
