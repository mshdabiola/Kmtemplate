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
import com.mshdabiola.app.libs
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.withType

class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("io.gitlab.arturbosch.detekt")

        extensions.configure<DetektExtension> {
            val rootDetektConfig = target.rootProject.file("detekt.yml")
            val localDetektConfig = target.file("detekt.yml")

            if (localDetektConfig.exists()) {
                config.from(localDetektConfig, rootDetektConfig)
            } else {
                config.from(rootDetektConfig)
            }
//            config.setFrom(files("$rootDir/detekt.yml"))
            buildUponDefaultConfig = true
            parallel = true
            ignoreFailures = true
            basePath = rootProject.projectDir.absolutePath
        }

        val reportMerge by tasks.registering(ReportMergeTask::class) {
            output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.sarif"))
        }
        tasks.withType<Detekt>().configureEach {
            jvmTarget = "21"
            exclude("**/build/**")
            finalizedBy(reportMerge)
        }

        reportMerge.configure {
            input.from(tasks.withType<Detekt>().map { it.sarifReportFile })
        }
    }
}
