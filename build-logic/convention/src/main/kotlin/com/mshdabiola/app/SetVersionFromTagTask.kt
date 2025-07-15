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
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * A Gradle task to set versionName and versionCode in gradle/libs.versions.toml directly from provided values.
 */
abstract class SetVersionFromTagTask : DefaultTask() {

    @get:Input
    abstract val newVersionName: Property<String>

    @get:InputFile
    abstract val libsVersionsTomlFile: RegularFileProperty

    @get:OutputFile
    abstract val outputRevisionFile: RegularFileProperty

    @get:OutputFile
    abstract val outputLibsVersionsTomlFile: RegularFileProperty // Typically the same file for in-place updates

    @TaskAction
    fun setVersion() {
        val tomlFile = libsVersionsTomlFile.asFile.get()
        val versionGet = newVersionName.get()
        val versionNameToSet = if (versionGet.isNotEmpty() && versionGet[0].isLetter()) {
            versionGet.substring(1) // Remove the first character if it's an alphabet
        } else {
            versionGet // Otherwise, keep it as is
        }
        val versionCodeToSet = versionStringToNumber(versionNameToSet)

        println("Setting versionName to: $versionNameToSet")
        println("Setting versionCode to: $versionCodeToSet")

        val revFile = outputRevisionFile.asFile.get()
        revFile.writeText("0")


        // Read all lines from the TOML file
        val lines = tomlFile.readLines()
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
            val versionCodeRegex = """(versionCode\s*=\s*)(["']?)\d+\2""".toRegex()
            if (versionCodeRegex.containsMatchIn(modifiedLine)) {
                modifiedLine = versionCodeRegex.replace(modifiedLine) { matchResult ->
                    val (prefix, quote) = matchResult.destructured
                    "$prefix$quote$versionCodeToSet$quote"
                }
                println("Updated versionCode line: '$line' -> '$modifiedLine'")
            }
            updatedLines.add(modifiedLine)
        }

        // Write the updated lines back to the file
        tomlFile.writeText(updatedLines.joinToString("\n"))
        println("Successfully updated ${tomlFile.name}.")
    }

    private fun versionStringToNumber(versionString: String): Long {
        // Remove all non-digit characters (like dots)
        val numericString = versionString.replace(".", "")

        // Convert the resulting string to an integer
        return numericString.toLongOrNull() ?: 1
    }
}
