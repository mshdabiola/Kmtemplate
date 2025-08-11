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
import org.gradle.api.tasks.TaskAction
import kotlin.collections.any
import kotlin.collections.getOrNull
import kotlin.collections.indexOfFirst
import kotlin.collections.joinToString
import kotlin.collections.lastOrNull
import kotlin.collections.toMutableList
import kotlin.io.readLines
import kotlin.io.writeText
import kotlin.text.equals
import kotlin.text.isBlank
import kotlin.text.isLetter
import kotlin.text.isNotBlank
import kotlin.text.isNotEmpty
import kotlin.text.startsWith
import kotlin.text.substring
import kotlin.text.trim

/**
 * A Gradle task to prepend the 'Unreleased' section to CHANGELOG.md,
 * placing it before the first existing version or after the preamble.
 */
abstract class PrependUnreleasedToChangelogTask : DefaultTask() {

    @get:Input
    abstract val newVersionName: Property<String> // Should be the last released version for the compare link

    @get:InputFile
    abstract val changelogFile: RegularFileProperty

    @TaskAction
    fun prependUnreleasedSection() {
        val versionGet = newVersionName.get()
        val versionNameToSet = if (versionGet.isNotEmpty() && versionGet[0].isLetter()) {
            versionGet.substring(1) // Remove the first character if it's an alphabet
        } else {
            versionGet // Otherwise, keep it as is
        }

        val changelog = changelogFile.asFile.get()
        val originalLines = if (changelog.exists()) changelog.readLines() else emptyList()

        if (originalLines.any { it.trim() == "## [Unreleased]" }) {
            println("${changelog.name} already contains an '## [Unreleased]' section. No changes made.")
            return
        }

        val unreleasedHeader = "## [Unreleased]"
        val unreleasedLink = "[Unreleased]: https://github.com/mshdabiola/kmtemplate/compare/$versionNameToSet...HEAD"

        val newLines = originalLines.toMutableList()
        val versionHeaderRegex = Regex("^## \\[.+\\]") // Matches "## [some.version.string]"

        var insertAt = -1

        // Try to find the first actual version header
        val firstVersionHeaderIndex = newLines.indexOfFirst { versionHeaderRegex.matches(it.trim()) }

        if (firstVersionHeaderIndex != -1) {
            insertAt = firstVersionHeaderIndex
        } else {
            // No version header found, try to insert after "# Changelog" and any preamble
            val changelogTitleIndex = newLines.indexOfFirst { it.trim().equals("# Changelog", ignoreCase = true) }
            if (changelogTitleIndex != -1) {
                insertAt = changelogTitleIndex + 1
                // Skip standard preamble lines or blank lines after title
                while (insertAt < newLines.size &&
                    (
                        newLines[insertAt].isBlank() ||
                            newLines[insertAt].trim().startsWith("All notable changes") ||
                            newLines[insertAt].trim().startsWith("The format is based on") ||
                            newLines[insertAt].trim().startsWith("and this project adheres to")
                        )
                ) {
                    insertAt++
                }
            } else {
                // Absolute fallback: insert at the beginning
                insertAt = 0
            }
        }

        val linesToInsert = mutableListOf<String>()

        if (insertAt > 0 && newLines.getOrNull(insertAt - 1)?.isNotBlank() == true) {
            linesToInsert.add("") // Add a blank line before ## [Unreleased]
        }

        linesToInsert.add(unreleasedHeader)
        linesToInsert.add(unreleasedLink)

        // Add a blank line after the unreleasedLink if there's content following it that isn't already blank
        if (insertAt < newLines.size && newLines.getOrNull(insertAt)?.isNotBlank() == true) {
            linesToInsert.add("")
        } else if (insertAt == newLines.size && linesToInsert.lastOrNull()?.isNotBlank() == true) {
            // If inserting at the very end of the file, ensure a trailing blank line if our last insert wasn't one.
            linesToInsert.add("")
        }

        newLines.addAll(insertAt, linesToInsert)

        changelog.writeText(newLines.joinToString("\n"))
        println("Successfully added Unreleased section to ${changelog.name}.")
    }
}
