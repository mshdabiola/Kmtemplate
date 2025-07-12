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
import com.mshdabiola.app.BumpConveyorRevisionTask
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.kotlin.dsl.register

class CiTaskPlugin : Plugin<Project> {

    override fun apply(target: Project) {
//        target.tasks.register(MERGE_LINT_TASK_NAME, ReportMergeTask::class.java) {
//            group = JavaBasePlugin.VERIFICATION_GROUP
//            output.set(project.layout.buildDirectory.file("lint-merged.sarif"))
//        }
//        target.tasks.register(MERGE_DETEKT_TASK_NAME, ReportMergeTask::class.java) {
//            group = JavaBasePlugin.VERIFICATION_GROUP
//            output.set(project.layout.buildDirectory.file("detekt-merged.sarif"))
//        }
        target.tasks.register<BumpConveyorRevisionTask>("bumpConveyorRevision") {
            description = "Reads, increments, and updates the Conveyor revision number in files."

            // Point the task to the necessary files
            revisionFile.set(target.rootProject.file(".github/workflows/.revision-version"))
            outputRevisionFile.set(target.rootProject.file(".github/workflows/.revision-version")) // Same file for output
            conveyorConfigFile.set(target.rootProject.file("ci.conveyor.conf"))

            // Ensure the task always runs if needed, useful for tasks that modify files.
            // However, if you want Gradle to track inputs and outputs for caching,
            // you might define specific output files and let Gradle determine up-to-dateness.
            // For this kind of file manipulation, making it always run or careful input/output declaration is key.
            outputs.upToDateWhen { false }
        }
    }

//    companion object {
//        const val MERGE_LINT_TASK_NAME: String = "mergeLintSarif"
//        const val MERGE_DETEKT_TASK_NAME: String = "mergeDetektSarif"
//    }
}
