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

import com.android.build.gradle.LibraryExtension
import com.mshdabiola.app.configureGradleManagedDevices
import com.mshdabiola.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("mshdabiola.android.library")
                apply("mshdabiola.android.library.compose")
                apply("mshdabiola.android.library.jacoco")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner =
                        "com.mshdabiola.testing.TestRunner"
                }
                testOptions.animationsDisabled = true
                configureGradleManagedDevices(this)
            }

            dependencies {


                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.tracing.ktx").get())
                add("testImplementation", libs.findLibrary("robolectric").get())
                add("testImplementation", project(":modules:testing"))
                add("testImplementation", project(":modules:screenshot-testing"))


                add("androidTestImplementation", libs.findLibrary("androidx.lifecycle.runtimeTesting").get())
                add("androidTestImplementation", project(":modules:testing"))


            }

            extensions.configure<KotlinMultiplatformExtension> {

                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                with(sourceSets) {

                    getByName("commonMain") {
                        this.dependencies {
                            implementation(project(":modules:data"))

                            implementation(project(":modules:model"))
                            implementation(project(":modules:ui"))

                            implementation(project(":modules:designsystem"))
                            implementation(project(":modules:analytics"))
                        }

                    }
                    getByName("commonTest") {
                        this.dependencies {

                        }

                    }
                    getByName("androidMain") {
                        this.dependencies {

                        }

                    }
                    getByName("androidInstrumentedTest") {
                        this.dependencies {
//
                        }

                    }
                    getByName("jvmMain") {
                        this.dependencies {
                            // implementation(libs.findLibrary("koin.core").get())

                        }

                    }
                    getByName("jvmTest") {
                        this.dependencies {

                        }

                    }
                }

            }

        }
    }
}
