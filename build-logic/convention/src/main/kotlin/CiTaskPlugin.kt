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
import com.mshdabiola.app.PrependUnreleasedToChangelogTask
import com.mshdabiola.app.SetVersionFromTagTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class CiTaskPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.tasks.register<SetVersionFromTagTask>("setVersionFromTag") {
            description =
                "Sets the versionName and versionCode in gradle/libs.versions.toml and updates CHANGELOG.md based on provided tag values."
            group = "CI Utilities"

            newVersionName.set(project.providers.gradleProperty("newVersionName").orElse("0.0.1"))
            // newVersionCode is derived from newVersionName in the task if not explicitly set via property
            libsVersionsTomlFile.set(target.rootProject.file("gradle/libs.versions.toml"))
            outputLibsVersionsTomlFile.set(target.rootProject.file("gradle/libs.versions.toml"))
            changelogFile.set(target.rootProject.file("CHANGELOG.md"))
            outputs.upToDateWhen { false }
        }

        target.tasks.register<PrependUnreleasedToChangelogTask>("prependUnreleasedChangelog") {
            description = "Prepends a new [Unreleased] section to CHANGELOG.md for the next development cycle."
            group = "CI Utilities"

            // This should be the version that was *just released* to correctly form the compare link.
            newVersionName.set(project.providers.gradleProperty("newVersionName").orElse("0.0.1"))
            changelogFile.set(target.rootProject.file("CHANGELOG.md"))
            outputs.upToDateWhen { false } // Ensure it always runs if invoked
        }
    }
}
