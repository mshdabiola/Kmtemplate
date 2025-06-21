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