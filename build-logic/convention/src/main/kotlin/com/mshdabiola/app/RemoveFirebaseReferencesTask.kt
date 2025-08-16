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
            val resultingLines = mutableListOf<String>()
            var i = 0
            while (i < lines.size) {
                val currentLineText = lines[i] // Keep original line for adding to resultingLines
                val trimmedLine = currentLineText.trim()

                // Check for foojay-resolver block:
                if (trimmedLine == "plugins {" &&
                    i + 1 < lines.size &&
                    lines[i + 1].trim().startsWith("id(\"org.gradle.toolchains.foojay-resolver\")") &&
                    i + 2 < lines.size &&
                    lines[i + 2].trim() == "}"
                ) {
                    i += 3 // Skip these 3 lines
                    logger.lifecycle("Removing foojay-resolver plugin block from settings.gradle.kts")
                    continue
                }

                // Check for toolchainManagement block
                if (trimmedLine == "toolchainManagement {") {
                    var braceCount = 0
                    var blockEndIndex = i
                    var foundBlock = false
                    for (j in i until lines.size) {
                        if (lines[j].contains("{")) braceCount++
                        if (lines[j].contains("}")) braceCount--
                        if (braceCount == 0 && j >= i) { // Found the end of the block
                            blockEndIndex = j
                            foundBlock = true
                            break
                        }
                    }
                    if (foundBlock) { // Successfully found the complete block
                        i = blockEndIndex + 1
                        logger.lifecycle("Removing toolchainManagement block from settings.gradle.kts")
                        continue
                    }
                }

                // Filters for specific maven repository URLs
                val urlsToRemove = listOf(
                    "https://androidx.dev/storage/compose-compiler/repository/",
                    "https://maven.pkg.jetbrains.space/public/p/compose/dev",
                    "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers",
                    "https://maven.pkg.jetbrains.space/public/p/kotlinx-coroutines/maven",
                    "https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental",
                )

                var lineShouldBeRemoved = false
                for (urlToRemove in urlsToRemove) {
                    // Check for both double and single quotes around the URL
                    if (trimmedLine.contains("maven(url = \"$urlToRemove\")") ||
                        trimmedLine.contains("maven(url = '$urlToRemove')")
                    ) {
                        logger.lifecycle("Removing maven repo: $urlToRemove from settings.gradle.kts")
                        lineShouldBeRemoved = true
                        break
                    }
                }
                if (lineShouldBeRemoved) {
                    i++
                    continue
                }

                resultingLines.add(currentLineText) // Add the original, non-trimmed line
                i++
            }
            resultingLines
        }

        // 2. Process AndroidApplicationFirebaseConventionPlugin.kt
        processFile(
            firebaseConventionPluginFile.get().asFile,
            outputFirebaseConventionPluginFile.get().asFile,
        ) {
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

    /**
     * Apply a line-based transformation to an input text file and write the result to an output file only if it changed.
     *
     * If the input file does not exist the function returns without writing. The file's lines are read, passed to
     * [transformer], and the transformed lines are compared to the original (joined with '\n'); [outputFile] is
     * overwritten with the transformed content only when it differs from the original.
     *
     * @param transformer Function that receives the file's lines and returns the transformed lines to be written.
     */
    private fun processFile(inputFile: File, outputFile: File, transformer: (List<String>) -> List<String>) {
        logger.lifecycle("Processing file for removal task: ${inputFile.path}")

        if (!inputFile.exists()) {
            logger.warn("File not found, skipping: ${inputFile.path}")
            return
        }

        val originalLines = inputFile.readLines()
        val modifiedLines = transformer(originalLines)

        val newContent = modifiedLines.joinToString("\n")
        val originalContent = originalLines.joinToString("\n")

        if (newContent != originalContent) {
            outputFile.writeText(newContent)
            logger.lifecycle("Updated file via removal task: ${outputFile.path}")
        } else {
            logger.lifecycle("No changes needed for file via removal task: ${outputFile.path}")
        }
    }
}
