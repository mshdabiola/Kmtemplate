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
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.mshdabiola.app.configureFlavors
import com.mshdabiola.app.configureGradleManagedDevices
import com.mshdabiola.app.configureKotlinAndroid
import com.mshdabiola.app.configurePrintApksTask
import com.mshdabiola.app.disableUnnecessaryAndroidTests
import com.mshdabiola.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import org.jetbrains.kotlin.powerassert.gradle.PowerAssertGradleExtension

class AndroidLibraryConventionPlugin : Plugin<Project> {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-multiplatform")
                apply("com.android.library")
                apply("mshdabiola.android.lint")
                apply("org.jetbrains.kotlin.plugin.power-assert")
                apply("org.jetbrains.kotlinx.kover")
                apply("mshdabiola.spotless")


//                apply("screenshot-test-gradle-plugin")
            }

            extensions.configure<PowerAssertGradleExtension> {
                functions.set(
                    listOf(
                        "kotlin.assert",
                        "kotlin.test.assertTrue",
                        "kotlin.test.assertEquals",
                        "kotlin.test.assertNull",
                    ),
                )
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 35

                configureFlavors(this)

                configureGradleManagedDevices(this)
                // The resource prefix is derived from the module name,
                // so resources inside ":core:module1" must be prefixed with "core_module1_"
                resourcePrefix =
                    path.split("""\W""".toRegex()).drop(1).distinct().joinToString(separator = "_")
                        .lowercase() + "_"
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                configurePrintApksTask(this)
                disableUnnecessaryAndroidTests(target)
            }

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget()
                // jvm("desktop")
                jvm()

                @OptIn(ExperimentalWasmDsl::class)
                wasmJs {
                    browser {
                        val rootDirPath = project.rootDir.path
                        val projectDirPath = project.projectDir.path
                        commonWebpackConfig {
                            devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                                static = (static ?: mutableListOf()).apply {
                                    // Serve sources to debug inside browser
                                    add(rootDirPath)
                                    add(projectDirPath)
                                }
                            }
                        }
                    }
                }

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
                    getByName("nonJsMain") {
                        this.dependencies {
                        }
                    }
                    commonMain.dependencies {
                        implementation(libs.findLibrary("koin.core").get())
                        implementation(libs.findLibrary("kermit").get())
                    }

                    androidMain.dependencies {
                        implementation(libs.findLibrary("koin.android").get())
                    }
                    jvmMain.dependencies {
                        implementation(libs.findLibrary("slf4j.simple").get())
                    }

                    jvmTest.dependencies {
                        implementation(kotlin("test"))
                        implementation(project(":modules:testing"))
                    }
                }
            }
        }
    }
}
