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
import com.android.build.gradle.LibraryExtension
import com.mshdabiola.app.configureGradleManagedDevices
import com.mshdabiola.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("mshdabiola.android.library")
                apply("mshdabiola.android.library.compose")
                // apply("mshdabiola.android.library.jacoco")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner =
                        "androidx.test.runner.AndroidJUnitRunner"
                }
                testOptions.animationsDisabled = true
                configureGradleManagedDevices(this)
            }

            dependencies {
//                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
//                add("implementation", libs.findLibrary("androidx.tracing.ktx").get())
                //   add("testImplementation", project(":modules:testing"))

                add("androidTestImplementation", project(":modules:testing"))
            }

            extensions.configure<KotlinMultiplatformExtension> {
                with(sourceSets) {
                    getByName("commonMain") {
                        this.dependencies {
                            implementation(project(":modules:data"))

                            implementation(project(":modules:model"))
                            implementation(project(":modules:ui"))

                            implementation(project(":modules:designsystem"))
                            implementation(project(":modules:analytics"))
                            implementation(libs.findLibrary("kotlinx.serialization.json").get())
                        }
                    }
                }
            }
        }
    }
}
