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
import com.mshdabiola.app.BumpVersionTask
import com.mshdabiola.app.SetVersionFromTagTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class CiTaskPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.tasks.register<BumpConveyorRevisionTask>("bumpConveyorRevision") {
            description = "Reads, increments, and updates the Conveyor revision number in files."

            // Point the task to the necessary files
            revisionFile.set(target.rootProject.file(".github/workflows/.revision-version"))
            outputRevisionFile.set(target.rootProject.file(".github/workflows/.revision-version"))
            // Same file for output
            conveyorConfigFile.set(target.rootProject.file("ci.conveyor.conf"))

            // Ensure the task always runs if needed, useful for tasks that modify files.
            // However, if you want Gradle to track inputs and outputs for caching,
            // you might define specific output files and let Gradle determine up-to-dateness.
            // For this kind of file manipulation, making it always run or careful input/output declaration is key.
            outputs.upToDateWhen { false }
        }

        target.tasks.register<BumpVersionTask>("bumpVersionCode") {
            description = "Updates the versionName and " +
                "increments the versionCode in gradle/libs.versions.toml."

            revisionFile.set(target.rootProject.file(".github/workflows/.revision-version"))
            // Provide a default for local testing, will be overridden by -P

            libsVersionsTomlFile.set(target.rootProject.file("gradle/libs.versions.toml"))
            outputLibsVersionsTomlFile.set(target.rootProject.file("gradle/libs.versions.toml"))
            // Same file

            // For tasks that modify files and are part of a CI/CD pipeline,
            // it's often practical to make them always run.
            outputs.upToDateWhen { false }
        }

        target.tasks.register<SetVersionFromTagTask>("setVersionFromTag") {
            description = "Sets the versionName and versionCode in " +
                "gradle/libs.versions.toml based on provided tag values."

            // These properties must be set when the task is called, e.g.,
            // via -PnewVersionName=... -PnewVersionCode=...
            // Provide convention values for local testing/default, but expect them to be overridden.
            newVersionName.set(project.providers.gradleProperty("newVersionName").orElse("0.0.1"))

            libsVersionsTomlFile.set(target.rootProject.file("gradle/libs.versions.toml"))
            outputLibsVersionsTomlFile.set(target.rootProject.file("gradle/libs.versions.toml"))
            // Same file
            outputRevisionFile.set(target.rootProject.file(".github/workflows/.revision-version"))

            // For tasks that modify files and are part of a CI/CD pipeline,
            // it's often practical to make them always run.
            outputs.upToDateWhen { false }
        }
    }
}
