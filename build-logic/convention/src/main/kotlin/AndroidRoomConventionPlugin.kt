/*
 * Copyright 2022 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-multiplatform")
                apply("com.google.devtools.ksp")
                apply("androidx.room")

            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.configure<KspExtension> {
                // The schemas directory contains a schema file for each version of the Room database.
                // This is required to enable Room auto migrations.
                // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
                // arg(RoomSchemaArgProvider(File(projectDir, "schemas")))
            }


            dependencies {
                //add("implementation", libs.findLibrary("room.runtime").get())
                //add("implementation", libs.findLibrary("room.ktx").get())
                //add("implementation", libs.findLibrary("room.paging").get())
                //add("ksp", libs.findLibrary("room.compiler").get())
                // add("kspAndroid", libs.findLibrary("room.compiler").get())
                add("ksp", libs.findLibrary("room.compiler").get())


            }
//            extensions.configure<RoomExtension>{
//                schemaDirectory("$projectDir/schemas")
//
//
//            }

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget()
                jvm("desktop")
                // val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                with(sourceSets) {

                    getByName("commonMain") {
                        this.dependencies {
                            implementation(project(":modules:model"))
                            implementation(libs.findLibrary("room.runtime").get())
                            implementation(libs.findLibrary("room.ktx").get())
                            implementation(libs.findLibrary("room.paging").get())
//                            implementation(libs.findLibrary("paging.common").get())

                            api(libs.findLibrary("sqlite.bundled").get())//sqlite-bundled

                            //ksp(libs.findLibrary("room.compiler").get())


                        }

                    }
                }

            }
        }
    }

    /**
     * https://issuetracker.google.com/issues/132245929
     * [Export schemas](https://developer.android.com/training/data-storage/room/migrating-db-versions#export-schemas)
     */
//    class RoomSchemaArgProvider(
//        @get:InputDirectory
//        @get:PathSensitive(PathSensitivity.RELATIVE)
//        val schemaDir: File,
//    ) : CommandLineArgumentProvider {
//        override fun asArguments() = listOf("room.schemaLocation=${schemaDir.path}")
//    }
}