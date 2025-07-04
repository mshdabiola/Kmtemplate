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
                            tasks.named("detekt"), // Ensure detekt runs after spotlessApply
                        )
                    }
                }
            }
        }
    }
}
