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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.collections.indexOfFirst
import kotlin.collections.joinToString
import kotlin.collections.toMutableList
import kotlin.io.readLines
import kotlin.io.writeText
import kotlin.text.contains
import kotlin.text.isLetter
import kotlin.text.isNotEmpty
import kotlin.text.replace
import kotlin.text.split
import kotlin.text.substring
import kotlin.text.toLongOrNull
import kotlin.text.toRegex
import kotlin.text.trim

/**
 * A Gradle task to set versionName and versionCode in gradle/libs.versions.toml and update CHANGELOG.md.
 */
abstract class SetVersionFromTagTask2 : DefaultTask() {

    @get:Input
    abstract val newVersionName: Property<String>

    @get:InputFile
    abstract val libsVersionsTomlFile: RegularFileProperty

    @get:InputFile // Added for the changelog
    abstract val changelogFile: RegularFileProperty

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
            throw GradleException("versionCode not found in ${tomlFile.name}. Cannot increment.") as Throwable
        }

        val versionCodeToSet = currentVersionCode + 1
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
                println("Updated versionName line: '$line' -> '$modifiedLine'") // This println is from your existing code
            }

            // 2. Update versionCode
            // This regex needs to handle both `versionCode = "123"` and `versionCode = 123`
            val versionCodeUpdateRegex = """(versionCode\s*=\s*)(["']?)\d+\2""".toRegex()
            if (versionCodeUpdateRegex.containsMatchIn(modifiedLine)) {
                modifiedLine = versionCodeUpdateRegex.replace(modifiedLine) { matchResult ->
                    val (prefix, quote) = matchResult.destructured
                    "$prefix$quote$versionCodeToSet$quote" // Use the new incremented versionCodeToSet
                }
                println("Updated versionCode line: '$line' -> '$modifiedLine'") // This println is from your existing code
            }
            updatedLines.add(modifiedLine)
        }

        // Write the updated lines back to the file
        tomlFile.writeText(updatedLines.joinToString("\n"))
        println("Successfully updated ${tomlFile.name}.")

        // Update changelog (assuming this part remains the same)
        updateChangelog(versionNameToSet)
    }


    private fun updateChangelog(newVersion: String) {
        val changelog = changelogFile.asFile.get()
        val lines = changelog.readLines().toMutableList()
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        val unreleasedHeaderIndex = lines.indexOfFirst { it.trim() == "## [Unreleased]" }
        if (unreleasedHeaderIndex != -1) {
            lines[unreleasedHeaderIndex] = "## [$newVersion] - $currentDate"
        }

        val newVersionLink = "[$newVersion]: https://github.com/mshdabiola/kmtemplate/$newVersion"
        val versionLinkIndex = lines.indexOfFirst { it.contains("[Unreleased]") }
        if (versionLinkIndex != -1) {
            lines[versionLinkIndex] = newVersionLink
        }

        changelog.writeText(lines.joinToString("\n"))
        println("Successfully updated ${changelog.name} with version $newVersion.")
    }
}
