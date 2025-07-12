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

/**
 * A Gradle task to read, increment, and update the Conveyor revision.
 */
abstract class BumpConveyorRevisionTask : DefaultTask() {

    @get:InputFile
    abstract val revisionFile: RegularFileProperty

    @get:OutputFile
    abstract val outputRevisionFile: RegularFileProperty
    // This is often the same as revisionFile if you're updating in place

    @get:InputFile
    abstract val conveyorConfigFile: RegularFileProperty

    @TaskAction
    fun bumpRevision() {
        val revFile = revisionFile.asFile.get()
        val conveyorConf = conveyorConfigFile.asFile.get()

        // 1. Read and increment revision
        val currentRevision = if (revFile.exists()) {
            revFile.readText().trim().toIntOrNull() ?: 0
        } else {
            0
        }
        val newRevision = currentRevision + 1
        println("Current revision: $currentRevision")
        println("New revision: $newRevision")

        // 2. Write new revision back to .revision-version
        revFile.writeText(newRevision.toString())
        println("Updated ${revFile.name} with new revision: $newRevision")

        // 3. Update revision in ci.conveyor.conf
        // Read all lines, find the line(s) to update, and rewrite the file
        val updatedLines = conveyorConf.readLines().map { line ->
            // Regex to match 'revision = "X"' or 'revision = X'
            val regex = """(revision\s*=\s*)(["']?)\d+\2""".toRegex()
            if (line.contains("revision = ")) {
                val newLine = regex.replace(line) { matchResult ->
                    val (prefix, quote) = matchResult.destructured
                    "$prefix$quote$newRevision$quote"
                }
                if (newLine != line) {
                    println("Updating line in ${conveyorConf.name}: '$line' -> '$newLine'")
                    newLine
                } else {
                    line // No change
                }
            } else {
                line // Keep line as is
            }
        }
        conveyorConf.writeText(updatedLines.joinToString("\n"))
        println("Updated revision in ${conveyorConf.name} to $newRevision")

        // You could also output the new revision for use in other tasks/workflows
        // For example, if you wanted to pass it back to GitHub Actions:
        // project.setProperty("conveyor.revision", newRevision.toString())
    }
}
