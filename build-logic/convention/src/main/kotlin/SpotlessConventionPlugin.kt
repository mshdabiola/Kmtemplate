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
import com.diffplug.gradle.spotless.SpotlessExtension
import com.mshdabiola.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class SpotlessConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.diffplug.spotless")
            }

            extensions.configure<SpotlessExtension> {
                val ktlintVersion = libs.findLibrary("ktlint.cli").get().get().version

                kotlin {
                    target("src/**/*.kt")
                    ktlint(ktlintVersion)
                    licenseHeaderFile(rootProject.file("spotless/copyright.kt")).updateYearWithLatest(true)
                }

                kotlinGradle {
                    target("*.gradle.kts")
                    ktlint(ktlintVersion)
                    licenseHeaderFile(rootProject.file("spotless/copyright.kt"), "(^(?![\\/ ]\\**).*$)")
                        .updateYearWithLatest(true)
                }
            }
        }
    }
}
