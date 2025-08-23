/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.mshdabiola.app.DowngradeBuildToolsTask
import com.mshdabiola.app.PrependUnreleasedToChangelogTask
import com.mshdabiola.app.RemoveFirebaseReferencesTask
import com.mshdabiola.app.RenameProjectArtifactsTask
import com.mshdabiola.app.SetVersionTagTask
import com.mshdabiola.app.UpdateBuildVersionsPreReleaseTask
import com.mshdabiola.app.UpdateBuildVersionsTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import kotlin.text.set

class CiTaskPlugin : Plugin<Project> {

    override fun apply(target: Project) {
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

        target.tasks.register<PrependUnreleasedToChangelogTask>("prependUnreleasedChangelog") {
            description = "Prepends a new [Unreleased] section to CHANGELOG.md for the next development cycle."
            group = "CI Utilities"

            // This should be the version that was *just released* to correctly form the compare link.
            newVersionName.set(project.providers.gradleProperty("newVersionName").orElse("0.0.1"))
            changelogFile.set(target.rootProject.file("CHANGELOG.md"))
            outputs.upToDateWhen { false } // Ensure it always runs if invoked
        }

        target.tasks.register<DowngradeBuildToolsTask>("downgradeBuildLogicAndAgp") {
            group = "maintenance"
            description = "Downgrades build-logic's Gradle wrapper and root project's AGP version."

            // Input files
            gradleWrapperPropertiesFile.set(project.file("build-logic/gradle/wrapper/gradle-wrapper.properties"))
            libsVersionsTomlFile.set(project.file("gradle/libs.versions.toml"))

            // Target versions and keys
            targetGradleVersion.set("8.13")
            targetAgpVersion.set("8.11.1")
            agpVersionKeyInToml.set("androidGradlePlugin") // Or your actual key

            // Output files (pointing to the same files as inputs for in-place modification)
            outputGradleWrapperPropertiesFile.set(project.file("build-logic/gradle/wrapper/gradle-wrapper.properties"))
            outputLibsVersionsTomlFile.set(project.file("gradle/libs.versions.toml"))
        }

        target.tasks.register<UpdateBuildVersionsTask>("updateBuildVersions") {
            description =
                "Updates version information in BuildConfig.kt, ci.conveyor.conf, and gradle/libs.versions.toml."
            group = "CI Utilities"

            newVersionName.set(project.providers.gradleProperty("newVersionName").orElse("0.0.1"))
            buildConfigFile.set(
                target.rootProject.file("core/model/src/commonMain/kotlin/com/mshdabiola/model/BuildConfig.kt"),
            )
            conveyorConfFile.set(target.rootProject.file("ci.conveyor.conf"))
            libsVersionsTomlFile.set(target.rootProject.file("gradle/libs.versions.toml"))

            outputs.upToDateWhen { false } // Ensure it always runs if invoked
        }
        target.tasks.register<UpdateBuildVersionsPreReleaseTask>("updateBuildVersionsPreRelease") {
            description =
                "Updates version information in BuildConfig.kt, ci.conveyor.conf, and gradle/libs.versions.toml."
            group = "CI Utilities"

            newVersionName.set(project.providers.gradleProperty("newVersionName").orElse("0.0.1-alpha01"))
            buildConfigFile.set(
                target.rootProject.file("core/model/src/commonMain/kotlin/com/mshdabiola/model/BuildConfig.kt"),
            )
            conveyorConfFile.set(target.rootProject.file("ci.conveyor.conf"))
            libsVersionsTomlFile.set(target.rootProject.file("gradle/libs.versions.toml"))

            outputs.upToDateWhen { false } // Ensure it always runs if invoked
        }
        target.tasks.register<SetVersionTagTask>("setVersionTag") {
            description =
                "Updates version information in BuildConfig.kt, ci.conveyor.conf, and gradle/libs.versions.toml."
            group = "CI Utilities"

            newVersionName.set(project.providers.gradleProperty("newVersionName").orElse("0.0.1-alpha01"))
            buildConfigFile.set(
                target.rootProject.file("core/model/src/commonMain/kotlin/com/mshdabiola/model/BuildConfig.kt"),
            )
            libsVersionsTomlFile.set(target.rootProject.file("gradle/libs.versions.toml"))
            changelogFile.set(target.rootProject.file("CHANGELOG.md"))

            outputs.upToDateWhen { false } // Ensure it always runs if invoked
        }
    }
}
