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

    @get:Input
    abstract val newVersionName: Property<String>

    @get:InputFile
    abstract val libsVersionsTomlFile: RegularFileProperty

    @get:OutputFile
    abstract val outputLibsVersionsTomlFile: RegularFileProperty // Typically the same file for in-place updates

    @TaskAction
    fun bumpVersion() {
        val tomlFile = libsVersionsTomlFile.asFile.get()
        val versionNameToSet = newVersionName.get()

        println("Updating versionName to: $versionNameToSet")

        // Read all lines from the TOML file
        val lines = tomlFile.readLines()
        val updatedLines = mutableListOf<String>()
        var versionCodeFound = false
        var currentVersionCode = 0

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

            // 2. Increment versionCode
            // This regex needs to handle both `versionCode = "123"` and `versionCode = 123`
            val versionCodeRegex = """(versionCode\s*=\s*)(["']?)(\d+)\2""".toRegex()
            if (versionCodeRegex.containsMatchIn(modifiedLine)) {
                versionCodeFound = true
                modifiedLine = versionCodeRegex.replace(modifiedLine) { matchResult ->
                    val (prefix, quote, codeStr) = matchResult.destructured
                    currentVersionCode = codeStr.toInt()
                    val newCode = currentVersionCode + 1
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
