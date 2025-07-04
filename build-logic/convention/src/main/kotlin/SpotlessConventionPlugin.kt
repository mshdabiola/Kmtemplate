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
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jlleitschuh.gradle.ktlint.KtlintExtension

class SpotlessConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.diffplug.spotless")
                apply("org.jlleitschuh.gradle.ktlint")
                apply("io.gitlab.arturbosch.detekt")
            }

            extensions.configure<KtlintExtension> {
                debug.set(true)
                // ignoreFailures.set(true)
//        reporters {
//            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML)
//            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
//            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
//        }
            }

            extensions.configure<SpotlessExtension> {
                kotlin {
                    target("**/*.kt")
                    targetExclude("**/build/**/*.kt")
                    ktlint()
//                            .setUseExperimental(true)
//                            .userData(mapOf("android" to "true"))
//                            .editorConfigOverride(mapOf("indent_size" to 2, "continuation_indent_size" to 2))
                    licenseHeaderFile(rootProject.file("$rootDir/spotless/copyright.kt"))
                        .updateYearWithLatest(true)
                }
                format("kts") {
                    target("**/*.kts")
                    targetExclude("**/build/**/*.kts")
                    // Look for the first line that doesn't have a block comment (assumed to be the license)
                    licenseHeaderFile(rootProject.file("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
                        .updateYearWithLatest(true)
                }
                format("xml") {
                    target("**/*.xml")
                    targetExclude("**/build/**/*.xml")
                    // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
                    licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
                        .updateYearWithLatest(true)
                }


            }
            target.plugins.withId("io.gitlab.arturbosch.detekt") {
                val rootProject = target.rootProject

                target.extensions.configure<DetektExtension> {

                    source = files(
                        "src/main/kotlin",
                        "src/commonMain/kotlin",
                        "src/jvmMain/kotlin",
                        "src/androidMain/kotlin",
                        "src/iosMain/kotlin",
                        "src/nativeMain/kotlin",
                        "src/desktop/kotlin",
                        "src/js/kotlin",
                    )
                    config.setFrom(rootProject.file("detekt.yml"))
                    buildUponDefaultConfig = true
                    ignoreFailures = false

                }
            }



            dependencies {
                add("ktlint", project(":ktlint"))
            }

            // Configure tasks after the project has been evaluated to ensure all tasks are available
            afterEvaluate {
                // Make 'ktlintCheck' run after 'spotlessCheck'
                tasks.withType(com.diffplug.gradle.spotless.SpotlessTask::class.java) {
                    if (name == "spotlessCheck") {
                        finalizedBy(tasks.named("ktlintCheck")) // Ensure ktlintCheck runs after spotlessCheck
                    }
                }

                // Make 'ktlintFormat' and 'detekt' run after 'spotlessApply'
                tasks.withType(com.diffplug.gradle.spotless.SpotlessTask::class.java) {
                    if (name == "spotlessApply") {
                        finalizedBy(
                            tasks.named("ktlintFormat"), // Ensure ktlintFormat runs after spotlessApply
                            tasks.named("detekt")       // Ensure detekt runs after spotlessApply
                        )
                    }
                }
            }

        }
    }
}
