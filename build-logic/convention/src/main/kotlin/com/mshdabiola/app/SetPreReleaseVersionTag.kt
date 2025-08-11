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
import org.gradle.api.GradleException
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * A Gradle task to set versionName and versionCode in gradle/libs.versions.toml directly from provided values.
 */
abstract class SetPreReleaseVersionTag : DefaultTask() {

    @get:Input
    abstract val newVersionName: Property<String>

    @get:InputFile
    abstract val libsVersionsTomlFile: RegularFileProperty

    @get:OutputFile
    abstract val outputRevisionFile: RegularFileProperty

    @get:OutputFile
    val stringsXmlFile: File by lazy {
        project.rootProject.projectDir.resolve(
            "core/designsystem/src/commonMain/composeResources/values/strings.xml",
        )
    }

    @TaskAction
    fun setVersion() {
        val revFile = outputRevisionFile.asFile.get()
        val currentRevision = if (revFile.exists()) {
            revFile.readText().trim().toIntOrNull() ?: 0
        } else {
            0
        }

        val tomlFile = libsVersionsTomlFile.asFile.get()
        val versionGet = newVersionName.get()
        val versionNameToSet = if (versionGet.isNotEmpty() && versionGet[0].isLetter()) {
            versionGet.substring(1) // Remove the first character if it's an alphabet
        } else {
            versionGet // Otherwise, keep it as is
        }

        println("Setting versionName to: $versionNameToSet")

        // Read all lines from the TOML file to find current versionCode
        val lines = tomlFile.readLines()
        var currentVersionCode = -1L // Initialize with a value indicating not found

        for (line in lines) {
            // Regex to find versionCode = "123" or versionCode = 123
            // It captures the optional quote (group 1) and the digits (group 2)
            val versionCodeExtractRegex = """versionCode\s*=\s*(["']?)(\d+)\1""".toRegex()
            val match = versionCodeExtractRegex.find(line)
            if (match != null) {
                currentVersionCode = match.groupValues[2].toLongOrNull() ?: -1L
                break // Found, no need to search further
            }
        }

        if (currentVersionCode == -1L) {
            // If versionCode is not found in the toml file, throw an error.
            // Alternatively, you could default to 0 (so new code becomes 1),
            // but the requirement is to increment the existing one.
            throw GradleException(
                "versionCode not found " +
                    "in ${tomlFile.name}. Cannot increment.",
            )
        }

        val versionCodeToSet = currentVersionCode + currentRevision + 1
        println("Setting versionCode to: $versionCodeToSet (incremented from $currentVersionCode)")

        val updatedLines = mutableListOf<String>()

        // Process lines to update versionName and versionCode
        for (line in lines) {
            var modifiedLine = line

            // 1. Update versionName
            val versionNameRegex = """(versionName\s*=\s*")[^"]+(")""".toRegex()
            if (line.contains("versionName = ") && versionNameRegex.containsMatchIn(line)) {
                modifiedLine = versionNameRegex.replace(line) { matchResult ->
                    val (prefix, suffix) = matchResult.destructured
                    "$prefix$versionNameToSet$suffix"
                }
                println("Updated versionName line: '$line' -> '$modifiedLine'")
            }

            // 2. Update versionCode
            // This regex needs to handle both `versionCode = "123"` and `versionCode = 123`
            val versionCodeUpdateRegex = """(versionCode\s*=\s*)(["']?)\d+\2""".toRegex()
            if (versionCodeUpdateRegex.containsMatchIn(modifiedLine)) {
                modifiedLine = versionCodeUpdateRegex.replace(modifiedLine) { matchResult ->
                    val (prefix, quote) = matchResult.destructured
                    "$prefix$quote$versionCodeToSet$quote" // Use the new incremented versionCodeToSet
                }
                println("Updated versionCode line: '$line' -> '$modifiedLine'")
            }
            updatedLines.add(modifiedLine)
        }

        // Write the updated lines back to the file
        tomlFile.writeText(updatedLines.joinToString("\n"))
        println("Successfully updated ${tomlFile.name}.")

        // Update changelog (assuming this part remains the same)
        updateVersionInfoInStringsXml(
            newVersionCode = versionCodeToSet.toString(),
            newVersionName = versionNameToSet,
            stringsXmlFile = stringsXmlFile,
            logger = logger,
        )
    }
}
