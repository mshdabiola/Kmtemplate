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

    @get:Input
    abstract val newVersionCode: Property<Int> // Using Int directly as it's a number

    @get:InputFile
    abstract val libsVersionsTomlFile: RegularFileProperty

    @get:OutputFile
    abstract val outputLibsVersionsTomlFile: RegularFileProperty // Typically the same file for in-place updates

    @TaskAction
    fun setVersion() {
        val tomlFile = libsVersionsTomlFile.asFile.get()
        val versionNameToSet = newVersionName.get()
        val versionCodeToSet = newVersionCode.get()

        println("Setting versionName to: $versionNameToSet")
        println("Setting versionCode to: $versionCodeToSet")

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
}
