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
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.mshdabiola.datastore"
    //proguard here
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:model"))



                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines.core)


            }
        }
        wasmJsMain.dependencies{
            api(libs.kstore.storage)
            api(libs.kstore)
            api("org.jetbrains.kotlinx:kotlinx-browser:0.3")



        }
        val nonJsMain by getting {
            dependencies {
                api(libs.androidx.dataStore.core)
                api(libs.androidx.datastore.core.okio)
            }
        }
    }
}