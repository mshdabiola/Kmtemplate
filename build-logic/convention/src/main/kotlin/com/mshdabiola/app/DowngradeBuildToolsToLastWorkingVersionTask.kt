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
import org.gradle.api.tasks.TaskAction
import java.util.Properties

abstract class DowngradeBuildToolsToLastWorkingVersionTask : DefaultTask() {

    @get:InputFile
    abstract val gradleWrapperPropertiesFile: RegularFileProperty

    @get:InputFile
    abstract val libsVersionsTomlFile: RegularFileProperty

    // Output files are the same as input files because we are modifying them in-place.
    // Gradle needs this to understand task dependencies and up-to-date checks correctly.
    @get:OutputFile
    abstract val outputGradleWrapperPropertiesFile: RegularFileProperty

    @get:OutputFile
    abstract val outputLibsVersionsTomlFile: RegularFileProperty

    // Define fixed target versions
    private val targetGradleVersion = "8.13"
    private val targetAgpVersion = "8.11.1"
    private val agpVersionKeyInToml = "androidGradlePlugin"


    @TaskAction
    fun downgrade() {
        val wrapperFile = gradleWrapperPropertiesFile.get().asFile
        val tomlFile = libsVersionsTomlFile.get().asFile

        // 1. Downgrade Gradle Wrapper
        if (wrapperFile.exists()) {
            val props = Properties()
            wrapperFile.inputStream().use { props.load(it) }

            val currentDistUrl = props.getProperty("distributionUrl")
            if (currentDistUrl != null) {
                val currentGradleVersion = Regex("gradle-([\\d.]+)-bin\\.zip").find(currentDistUrl)?.groupValues?.get(1)
                if (currentGradleVersion == targetGradleVersion) {
                    logger.lifecycle("Gradle version in ${wrapperFile.name} is already $targetGradleVersion. No changes needed.")
                } else {
                    val newDistUrl = currentDistUrl.replace(Regex("gradle-[\\d.]+-bin\\.zip"), "gradle-$targetGradleVersion-bin.zip")
                    if (newDistUrl != currentDistUrl) {
                        props.setProperty("distributionUrl", newDistUrl)
                        wrapperFile.outputStream().use { props.store(it, null) }
                        logger.lifecycle("Downgraded Gradle distributionUrl in ${wrapperFile.name} from $currentGradleVersion to $targetGradleVersion.")
                    } else {
                        logger.lifecycle("Gradle distributionUrl in ${wrapperFile.name} pattern did not match or already targets $targetGradleVersion.")
                    }
                }
            } else {
                logger.warn("Could not find 'distributionUrl' in ${wrapperFile.name}.")
            }
        } else {
            logger.error("Gradle Wrapper properties file not found: ${wrapperFile.path}")
        }

        // 2. Downgrade AGP in libs.versions.toml
        if (tomlFile.exists()) {
            val originalLines = tomlFile.readLines()
            val newLines = mutableListOf<String>()
            var agpLineFoundAndReplaced = false
            var agpVersionChanged = false

            val agpRegex = Regex("""^\s*${Regex.escape(agpVersionKeyInToml)}\s*=\s*"([^"]+)"\s*$""")

            for (line in originalLines) {
                val match = agpRegex.find(line)
                if (match != null) {
                    agpLineFoundAndReplaced = true
                    val currentVersion = match.groupValues[1]
                    if (currentVersion == targetAgpVersion) {
                        newLines.add(line) // Keep the line as is
                        logger.lifecycle("AGP version in ${tomlFile.name} (key: '$agpVersionKeyInToml') is already $targetAgpVersion.")
                    } else {
                        val newLineContent = """$agpVersionKeyInToml = "$targetAgpVersion""""
                        val leadingWhitespace = line.takeWhile { it.isWhitespace() }
                        newLines.add("$leadingWhitespace$newLineContent")
                        agpVersionChanged = true
                        logger.lifecycle("Downgraded AGP version in ${tomlFile.name} (key: '$agpVersionKeyInToml') from $currentVersion to $targetAgpVersion.")
                    }
                } else {
                    newLines.add(line)
                }
            }

            if (agpLineFoundAndReplaced && agpVersionChanged) {
                tomlFile.writeText(newLines.joinToString(System.lineSeparator()))
            } else if (!agpLineFoundAndReplaced) {
                logger.warn("Could not find AGP version key '$agpVersionKeyInToml' in ${tomlFile.name}. File untouched regarding AGP.")
            }
            // If agpLineFoundAndReplaced is true but agpVersionChanged is false, it means version was already correct, so no write needed.

        } else {
            logger.error("libs.versions.toml file not found: ${tomlFile.path}")
        }
    }
}
