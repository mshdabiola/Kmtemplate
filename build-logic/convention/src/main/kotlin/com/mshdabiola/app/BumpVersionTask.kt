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
 * A Gradle task to update versionName and increment versionCode in gradle/libs.versions.toml.
 */
abstract class BumpVersionTask : DefaultTask() {

    @get:InputFile
    abstract val revisionFile: RegularFileProperty

    @get:InputFile
    abstract val libsVersionsTomlFile: RegularFileProperty

    @get:OutputFile
    abstract val outputLibsVersionsTomlFile: RegularFileProperty // Typically the same file for in-place updates

    @TaskAction
    fun bumpVersion() {
        val tomlFile = libsVersionsTomlFile.asFile.get()
        val revFile = revisionFile.asFile.get()

        // 1. Read and increment revision
        val currentRevision = if (revFile.exists()) {
            revFile.readText().trim().toIntOrNull() ?: 0
        } else {
            0
        }


        // Read all lines from the TOML file
        val lines = tomlFile.readLines()
        val updatedLines = mutableListOf<String>()
        var versionCodeFound = false
        var currentVersionCode = 0

        // Process lines to update versionName and versionCode
        for (line in lines) {
            var modifiedLine = line


            // 2. Increment versionCode
            // This regex needs to handle both `versionCode = "123"` and `versionCode = 123`
            val versionCodeRegex = """(versionCode\s*=\s*)(["']?)(\d+)\2""".toRegex()
            if (versionCodeRegex.containsMatchIn(modifiedLine)) {
                versionCodeFound = true
                modifiedLine = versionCodeRegex.replace(modifiedLine) { matchResult ->
                    val (prefix, quote, codeStr) = matchResult.destructured
                    currentVersionCode = codeStr.toInt()
                    val newCode = currentVersionCode + 1+currentRevision
                    println("Incrementing versionCode: $currentVersionCode -> $newCode")
                    "$prefix$quote$newCode$quote"
                }
            }
            updatedLines.add(modifiedLine)
        }

        if (!versionCodeFound) {
            logger.warn("Warning: 'versionCode' definition not found in ${tomlFile.name}.")
        }

        // Write the updated lines back to the file
        tomlFile.writeText(updatedLines.joinToString("\n"))
        println("Successfully updated ${tomlFile.name}.")
    }
}
