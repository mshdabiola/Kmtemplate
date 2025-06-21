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
import com.google.devtools.ksp.gradle.KspExtension
import com.mshdabiola.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AndroidRoomConventionPlugin : Plugin<Project> {

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-multiplatform")
                apply("com.google.devtools.ksp")
                apply("androidx.room")
            }

            extensions.configure<KspExtension> {
                arg("room.generateKotlin", "true")
            }
//            extensions.configure<RoomExtension> {
//                // The schemas directory contains a schema file for each version of the Room database.
//                // This is required to enable Room auto migrations.
//                // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
//                schemaDirectory("$projectDir/schemas")
//            }

            dependencies {
                // add("implementation", libs.findLibrary("room.runtime").get())
                // add("implementation", libs.findLibrary("room.ktx").get())
                // add("implementation", libs.findLibrary("room.paging").get())
                // add("ksp", libs.findLibrary("room.compiler").get())
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
                jvm()
                jvmToolchain(21)
                applyDefaultHierarchyTemplate {
                    common {
                        group("nonJs") {
                            withAndroidTarget()
                            // withIos()
                            withJvm()
                        }
                    }
                }

                with(sourceSets) {
                    commonMain.dependencies {
                        implementation(project(":modules:model"))
//                        implementation(libs.findLibrary("kotlinx.coroutines.core").get())
                    }
                    getByName("nonJsMain") {
                        this.dependencies {
                            implementation(libs.findLibrary("room.runtime").get())
                            implementation(libs.findLibrary("room.ktx").get())
                            implementation(libs.findLibrary("room.paging").get())
//                            implementation(libs.findLibrary("paging.common").get())

                            api(libs.findLibrary("sqlite.bundled").get()) // sqlite-bundled
                        }
                    }
                    jvmTest.dependencies {
                        implementation(project(":modules:testing"))
                    }
                }
            }
        }
    }
}
