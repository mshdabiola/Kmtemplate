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
package com.mshdabiola.app

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.io.readLines
import kotlin.io.writeText

abstract class ReleaseChangeLogTask : DefaultTask() {

    @get:Input
    abstract val newVersionName: Property<String> // This will be the new TAG for VERSION_NAME

    @get:InputFile // Added for the changelog
    abstract val changelogFile: RegularFileProperty

    @TaskAction
    fun updateVersions() {
        val versionGet = newVersionName.get()
        val newVersion = if (versionGet.startsWith("v")) {
            versionGet
                .removePrefix("v")
        } else {
            versionGet
        }

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
