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

plugins {
    id("mshdabiola.android.library")
//    id("mshdabiola.android.room")
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)



}
android {
    namespace = "com.mshdabiola.database"
}
room {
    schemaDirectory("$projectDir/schemas")
}
dependencies {
    //add("implementation", libs.findLibrary("room.runtime").get())
    //add("implementation", libs.findLibrary("room.ktx").get())
    //add("implementation", libs.findLibrary("room.paging").get())
    //add("ksp", libs.findLibrary("room.compiler").get())
    // add("kspAndroid", libs.findLibrary("room.compiler").get())
    add("ksp", libs.room.compiler)


}
kotlin {
    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }
        commonMain.dependencies {
            implementation(projects.modules.model)
        }
        wasmJsMain.dependencies{
        }
        jvmTest.dependencies {
            implementation(projects.modules.testing)
        }

        val nonJsMain by getting {
            dependencies {
                implementation(libs.room.runtime)
//                implementation(libs.room.ktx)
                implementation(libs.sqlite.bundled)
            }
        }
    }
}
//configurations.commonMainApi {
//            exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-android")
//        }