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
import com.mshdabiola.app.configureAndroidCompose
import com.mshdabiola.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.compose")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
            pluginManager.apply("com.android.compose.screenshot")

            //  val extension = extensions.getByType<LibraryExtension>()
            extensions.configure<LibraryExtension> {
                extensions.configure<LibraryExtension> {
                    configureAndroidCompose(this)

                    experimentalProperties["android.experimental.enableScreenshotTest"] = true
                }
            }

            dependencies {
                add("screenshotTestImplementation", project(":modules:designsystem"))
                add("screenshotTestImplementation", project(":modules:ui"))

                add(
                    "screenshotTestImplementation",
                    libs.findLibrary("androidx.compose.ui.tooling").get(),
                )
                add("screenshotTestImplementation", project(":modules:model"))
                add("screenshotTestImplementation", project(":modules:testing"))
            }
        }
    }
}
