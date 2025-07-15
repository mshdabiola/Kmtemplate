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
package com.mshdabiola.app

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class RemoveFirebaseReferencesTask : DefaultTask() {

    // Input/Output for settings.gradle.kts
    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE) // Only track changes relative to the project root
    abstract val settingsGradleKtsFile: RegularFileProperty

    @get:OutputFile
    abstract val outputSettingsGradleKtsFile: RegularFileProperty

    // Input/Output for AndroidApplicationFirebaseConventionPlugin.kt
    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val firebaseConventionPluginFile: RegularFileProperty

    @get:OutputFile
    abstract val outputFirebaseConventionPluginFile: RegularFileProperty

    // Input/Output for build-logic/convention/build.gradle.kts
    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val buildLogicConventionBuildGradleKtsFile: RegularFileProperty

    @get:OutputFile
    abstract val outputBuildLogicConventionBuildGradleKtsFile: RegularFileProperty

    // Input/Output for root build.gradle.kts
    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val rootBuildGradleKtsFile: RegularFileProperty

    @get:OutputFile
    abstract val outputRootBuildGradleKtsFile: RegularFileProperty

    @TaskAction
    fun removeReferences() {
        // 1. Process settings.gradle.kts
        processFile(
            settingsGradleKtsFile.get().asFile,
            outputSettingsGradleKtsFile.get().asFile,
        ) { lines ->
            lines.filterNot { it.contains("maven(url = \"https://androidx.dev/storage/compose-compiler") }
        }

        // 2. Process AndroidApplicationFirebaseConventionPlugin.kt
        processFile(
            firebaseConventionPluginFile.get().asFile,
            outputFirebaseConventionPluginFile.get().asFile,
        ) { lines ->
            val newcode = """
               import org.gradle.api.Plugin
               import org.gradle.api.Project

               class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {
                   override fun apply(target: Project) {
                   }
               }
            """.trimIndent()
            newcode.lines()
        }

        // 3. Process build-logic/convention/build.gradle.kts
        processFile(
            buildLogicConventionBuildGradleKtsFile.get().asFile,
            outputBuildLogicConventionBuildGradleKtsFile.get().asFile,
        ) { lines ->
            lines.filterNot { it.contains("libs.firebase") }
        }

        // 4. Process root build.gradle.kts
        processFile(
            rootBuildGradleKtsFile.get().asFile,
            outputRootBuildGradleKtsFile.get().asFile,
        ) { lines ->
            lines.filterNot { it.contains("alias(libs.plugins.firebase.") }
        }
    }

    // Helper function to abstract file processing
    private fun processFile(inputFile: File, outputFile: File, transformer: (List<String>) -> List<String>) {
        logger.lifecycle("Processing file: ${inputFile.absolutePath}")

        if (!inputFile.exists()) {
            logger.warn("File not found, skipping: ${inputFile.absolutePath}")
            return // Or throw an error if the file is mandatory
        }

        val originalLines = inputFile.readLines()
        val modifiedLines = transformer(originalLines)

        // Write back only if content changed to avoid unnecessary file writes
        // and potentially preserve file timestamps if nothing changed.
        val newContent = modifiedLines.joinToString("\n")
        val originalContent = originalLines.joinToString("\n")

        if (newContent != originalContent) {
            outputFile.writeText(newContent)
            logger.lifecycle("Updated file: ${outputFile.absolutePath}")
        } else {
            logger.lifecycle("No changes needed for file: ${outputFile.absolutePath}")
        }
    }
}
