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
package com.mshdabiola.app // Or your appropriate package

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.util.Properties

abstract class DowngradeBuildToolsTask : DefaultTask() {

    @get:InputFile
    abstract val gradleWrapperPropertiesFile: RegularFileProperty

    @get:Input
    abstract val targetGradleVersion: Property<String> // e.g., "8.13"

    @get:InputFile
    abstract val libsVersionsTomlFile: RegularFileProperty

    @get:Input
    abstract val targetAgpVersion: Property<String> // e.g., "8.11.1"

    @get:Input
    abstract val agpVersionKeyInToml: Property<String> // Key for AGP in TOML, e.g., "androidGradlePlugin"

    // These output properties should be configured ONCE in your build script
    // to point to the same files as the input properties if modifying in-place.
    @get:OutputFile
    abstract val outputGradleWrapperPropertiesFile: RegularFileProperty

    @get:OutputFile
    abstract val outputLibsVersionsTomlFile: RegularFileProperty

    @TaskAction
    fun downgrade() {
        val wrapperFile = gradleWrapperPropertiesFile.get().asFile
        val targetGradle = targetGradleVersion.get()
        val tomlFile = libsVersionsTomlFile.get().asFile
        val targetAgp = targetAgpVersion.get()
        val agpTomlKey = agpVersionKeyInToml.get()

        // 1. Downgrade Gradle Wrapper
        if (wrapperFile.exists()) {
            val props = Properties()
            wrapperFile.inputStream().use { props.load(it) }

            val currentDistUrl = props.getProperty("distributionUrl")
            if (currentDistUrl != null) {
                val newDistUrl = currentDistUrl.replace(
                    Regex("gradle-[\\d.]+-bin\\.zip"),
                    "gradle-$targetGradle-bin.zip",
                )
                if (newDistUrl != currentDistUrl) {
                    props.setProperty("distributionUrl", newDistUrl)
                    wrapperFile.outputStream().use { props.store(it, null) }
                    logger.lifecycle(
                        "Downgraded Gradle distributionUrl in ${wrapperFile.name} to use version $targetGradle.",
                    )
                    // NO NEED to set outputGradleWrapperPropertiesFile here again
                } else {
                    logger.lifecycle(
                        "Gradle distributionUrl in ${wrapperFile.name} already targets a version compatible with $targetGradle or pattern did not match.",
                    )
                }
            } else {
                logger.warn("Could not find 'distributionUrl' in ${wrapperFile.name}.")
            }
        } else {
            logger.error("Gradle Wrapper properties file not found: ${wrapperFile.path}")
        }

        // 2. Downgrade AGP in libs.versions.toml
        if (tomlFile.exists()) {
            val lines = tomlFile.readLines()
            val newLines = mutableListOf<String>()
            var agpLineFoundAndReplaced = false

            val agpRegex = Regex("""^\s*${Regex.escape(agpTomlKey)}\s*=\s*"[^"]+"\s*$""")
            val agpVersionRegex = Regex(""""([^"]+)"""")

            for (line in lines) {
                if (line.matches(agpRegex)) {
                    val currentVersionMatch = agpVersionRegex.find(line)
                    val currentVersion = currentVersionMatch?.groups?.get(1)?.value ?: "unknown"

                    if (currentVersion == targetAgp) {
                        newLines.add(line)
                        agpLineFoundAndReplaced = true
                        logger.lifecycle("AGP version in ${tomlFile.name} (key: '$agpTomlKey') is already $targetAgp.")
                    } else {
                        val newLine = """$agpTomlKey = "$targetAgp""""
                        val leadingWhitespace = line.takeWhile { it.isWhitespace() }
                        newLines.add("$leadingWhitespace$newLine")
                        agpLineFoundAndReplaced = true
                        logger.lifecycle(
                            "Downgraded AGP version in ${tomlFile.name} (key: '$agpTomlKey') from $currentVersion to $targetAgp.",
                        )
                    }
                } else {
                    newLines.add(line)
                }
            }

            if (agpLineFoundAndReplaced) {
                tomlFile.writeText(newLines.joinToString(System.lineSeparator()))
                // NO NEED to set outputLibsVersionsTomlFile here again
            } else {
                logger.warn(
                    "Could not find or update AGP version key '$agpTomlKey' in ${tomlFile.name}. File untouched regarding AGP.",
                )
            }
        } else {
            logger.error("libs.versions.toml file not found: ${tomlFile.path}")
        }
    }
}
