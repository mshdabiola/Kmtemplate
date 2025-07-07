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
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class DetektConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("io.gitlab.arturbosch.detekt")
            }

            target.plugins.withId("io.gitlab.arturbosch.detekt") {
                val rootProject = target.rootProject

                target.extensions.configure<DetektExtension> {
                    buildUponDefaultConfig = true
                    baseline = target.file("detekt-baseline.xml")
                    basePath = rootProject.projectDir.absolutePath

                    val localDetektConfig = target.file("detekt.yml")
                    val rootDetektConfig = target.rootProject.file("detekt.yml")
//                    val rootDetektComposeConfig = target.rootProject.file("detekt-compose.yml")
                    if (localDetektConfig.exists()) {
                        config.from(localDetektConfig, rootDetektConfig) // , rootDetektComposeConfig)
                    } else {
                        config.from(rootDetektConfig) // rootDetektComposeConfig)
                    }
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
//                    config.setFrom(rootProject.file("detekt.yml"))
//                    buildUponDefaultConfig = true
//                    ignoreFailures = false
                }

                val detektTask = target.tasks.named("detekt", Detekt::class.java)
                detektTask.configure {
                    reports.sarif.required.set(true)
                }
                rootProject.plugins.withId("mshdabiola.collect.sarif") {
                    rootProject.tasks.named(
                        CollectSarifPlugin.MERGE_DETEKT_TASK_NAME,
                        ReportMergeTask::class.java,
                    ) {
                        input.from(detektTask.map { it.sarifReportFile }.orNull)
                        mustRunAfter(detektTask)
                    }
                }
            }

            dependencies {
//                add("detektPlugins", libs.findLibrary("detekt-compose").get())
//                add("ktlint", project(":ktlint"))
            }
        }
    }
}
