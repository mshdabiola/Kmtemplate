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
import com.mshdabiola.app.RemoveFirebaseReferencesTask
import com.mshdabiola.app.RenameProjectArtifactsTask
import com.mshdabiola.app.SetVersionFromTagTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class CiTaskPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.tasks.register<BumpConveyorRevisionTask>("bumpConveyorRevision") {
            description = "Reads, increments, and updates the Conveyor revision number in files."
            group = "CI Utilities" // Good practice to group tasks

            revisionFile.set(target.rootProject.file(".revision-version"))
            outputRevisionFile.set(target.rootProject.file(".revision-version"))
            conveyorConfigFile.set(target.rootProject.file("ci.conveyor.conf"))
            newVersionName.set(project.providers.gradleProperty("newVersionName").orElse("0.0.1"))

            outputs.upToDateWhen { false }
        }

        target.tasks.register<BumpVersionTask>("bumpVersionCode") {
            description = "Updates the versionName and increments the versionCode in gradle/libs.versions.toml."
            group = "CI Utilities"

            revisionFile.set(target.rootProject.file(".revision-version"))
            libsVersionsTomlFile.set(target.rootProject.file("gradle/libs.versions.toml"))
            outputLibsVersionsTomlFile.set(target.rootProject.file("gradle/libs.versions.toml"))
            newVersionName.set(project.providers.gradleProperty("newVersionName").orElse("0.0.1"))

            outputs.upToDateWhen { false }
        }

        target.tasks.register<SetVersionFromTagTask>("setVersionFromTag") {
            description =
                "Sets the versionName and versionCode in gradle/libs.versions.toml based on provided tag values."
            group = "CI Utilities"

            newVersionName.set(project.providers.gradleProperty("newVersionName").orElse("0.0.1"))
            // newVersionCode is derived from newVersionName in the task if not explicitly set via property
            libsVersionsTomlFile.set(target.rootProject.file("gradle/libs.versions.toml"))
            outputLibsVersionsTomlFile.set(target.rootProject.file("gradle/libs.versions.toml"))
            outputRevisionFile.set(target.rootProject.file(".revision-version"))
            outputs.upToDateWhen { false }
        }

        target.tasks.register<RemoveFirebaseReferencesTask>("removeFirebaseReferences") {
            description = "Removes all known Firebase-related declarations from various Gradle files."
            group = "CI Utilities"

            settingsGradleKtsFile.set(target.rootProject.file("settings.gradle.kts"))
            outputSettingsGradleKtsFile.set(target.rootProject.file("settings.gradle.kts"))
            firebaseConventionPluginFile.set(
                target.rootProject.file(
                    "build-logic/convention/src/main/kotlin/AndroidApplicationFirebaseConventionPlugin.kt",
                ),
            )
            outputFirebaseConventionPluginFile.set(
                target.rootProject.file(
                    "build-logic/convention/src/main/kotlin/AndroidApplicationFirebaseConventionPlugin.kt",
                ),
            )
            buildLogicConventionBuildGradleKtsFile.set(
                target.rootProject.file("build-logic/convention/build.gradle.kts"),
            )
            outputBuildLogicConventionBuildGradleKtsFile.set(
                target.rootProject.file("build-logic/convention/build.gradle.kts"),
            )
            rootBuildGradleKtsFile.set(target.rootProject.file("build.gradle.kts"))
            outputRootBuildGradleKtsFile.set(target.rootProject.file("build.gradle.kts"))
            outputs.upToDateWhen { false }
        }

        target.tasks.register<RenameProjectArtifactsTask>("renameProjectArtifacts") {
            description = "Renames package, app name, and file prefixes across the project."
            group = "Project Refactoring"

            newPackageName.convention(target.providers.gradleProperty("newPackageName").orElse("com.example.newapp"))
            newPrefix.convention(target.providers.gradleProperty("newPrefix").orElse("nda"))
        }
    }
}
