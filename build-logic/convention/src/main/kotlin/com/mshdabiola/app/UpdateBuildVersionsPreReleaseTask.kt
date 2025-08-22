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
// import org.gradle.api.tasks.OutputFile // OutputFile is removed as we are modifying input files directly
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * A Gradle task to update version information across BuildConfig.kt, ci.conveyor.conf, and gradle/libs.versions.toml.
 * It reads BuildConfig.kt to increment REVISION_CODE and VERSION_CODE,
 * sets VERSION_NAME to a new input TAG, updates the revision in ci.conveyor.conf,
 * and updates VERSION_NAME and VERSION_CODE in gradle/libs.versions.toml.
 */
abstract class UpdateBuildVersionsPreReleaseTask : DefaultTask() {

    @get:Input
    abstract val newVersionName: Property<String> // This will be the new TAG for VERSION_NAME

    @get:InputFile
    abstract val buildConfigFile: RegularFileProperty

    @get:InputFile
    abstract val conveyorConfFile: RegularFileProperty

    @get:InputFile
    abstract val libsVersionsTomlFile: RegularFileProperty

    @TaskAction
    fun updateVersions() {
        val buildConfig = buildConfigFile.asFile.get()
        val conveyorConf = conveyorConfFile.asFile.get()
        val libsVersionsToml = libsVersionsTomlFile.asFile.get()
        val versionGet = newVersionName.get()
        val newTagName = if (versionGet.isNotEmpty() && versionGet[0].isLetter()) {
            versionGet
                .substring(1)
                .split('-')
                .first()

        } else {
            versionGet
                .split('-')
                .first()
        }

        println("Starting version updates with TAG: $newTagName")

        // Update BuildConfig.kt
        var buildConfigContent = buildConfig.readText()
        var newRevisionCode = -1
        var newVersionCode = -1

        val revisionCodeRegex = """(const val REVISION_CODE\s*=\s*)(\d+)""".toRegex()
        val versionCodeRegex = """(const val VERSION_CODE\s*=\s*)(\d+)""".toRegex()
        val versionNameRegex = """(const val VERSION_NAME\s*=\s*")([^"]+)(")""".toRegex()
        // Also update VERSION_CODE_DESKTOP if present, with the newTagName
//        val versionCodeDesktopRegex = """(const val VERSION_CODE_DESKTOP\s*=\s*")([^"]+)(")""".toRegex()


        buildConfigContent = revisionCodeRegex.replace(buildConfigContent) { matchResult ->
            val (prefix, currentValue) = matchResult.destructured
            newRevisionCode = currentValue.toInt() + 1
            println("BuildConfig.kt: REVISION_CODE changed from $currentValue to $newRevisionCode")
            "${prefix}$newRevisionCode"
        }

        buildConfigContent = versionCodeRegex.replace(buildConfigContent) { matchResult ->
            val (prefix, currentValue) = matchResult.destructured
            newVersionCode = currentValue.toInt() + 1
            println("BuildConfig.kt: VERSION_CODE changed from $currentValue to $newVersionCode")
            "${prefix}$newVersionCode"
        }

        buildConfigContent = versionNameRegex.replace(buildConfigContent) { matchResult ->
            val (prefix, _, suffix) = matchResult.destructured
            println("BuildConfig.kt: VERSION_NAME changed to $newTagName")
            "${prefix}$newTagName$suffix"
        }
//         buildConfigContent = versionCodeDesktopRegex.replace(buildConfigContent) { matchResult ->
//            val (prefix, _, suffix) = matchResult.destructured
//            println("BuildConfig.kt: VERSION_CODE_DESKTOP changed to $newTagName")
//            "${prefix}$newTagName$suffix"
//        }

        buildConfig.writeText(buildConfigContent)
        println("BuildConfig.kt updated successfully.")

        // Update ci.conveyor.conf
        if (newRevisionCode == -1) {
            throw GradleException("Could not read REVISION_CODE from BuildConfig.kt for conveyor.conf update")
        }
        var conveyorConfContent = conveyorConf.readText()
        val conveyorRevisionRegex = """(revision\s*=\s*")([^"]+)(")""".toRegex()
        conveyorConfContent = conveyorRevisionRegex.replace(conveyorConfContent) { matchResult ->
            val (prefix, _, suffix) = matchResult.destructured
            println("ci.conveyor.conf: revision changed to $newRevisionCode")
            "${prefix}$newRevisionCode$suffix"
        }
        conveyorConf.writeText(conveyorConfContent)
        println("ci.conveyor.conf updated successfully.")

        // Update gradle/libs.versions.toml
        if (newVersionCode == -1) {
             throw GradleException("Could not read VERSION_CODE from BuildConfig.kt for toml update")
        }
        var libsVersionsTomlContent = libsVersionsToml.readText()
        val tomlVersionNameRegex = """(versionName\s*=\s*")[^"]+(")""".toRegex()
        val tomlVersionCodeRegex = """(versionCode\s*=\s*)(["']?)(\d+)\2""".toRegex()
         // Also update desktopCode in toml if present
//        val tomlDesktopCodeRegex = """(desktopCode\s*=\s*")[^"]+(")""".toRegex()


        libsVersionsTomlContent = tomlVersionNameRegex.replace(libsVersionsTomlContent) { matchResult ->
            val (prefix, suffix) = matchResult.destructured
            println("libs.versions.toml: versionName changed to $newTagName")
            "${prefix}$newTagName$suffix"
        }

//        libsVersionsTomlContent = tomlDesktopCodeRegex.replace(libsVersionsTomlContent) { matchResult ->
//            val (prefix, suffix) = matchResult.destructured
//            println("libs.versions.toml: desktopCode changed to $newTagName")
//            "${prefix}$newTagName$suffix"
//        }

        libsVersionsTomlContent = tomlVersionCodeRegex.replace(libsVersionsTomlContent) { matchResult ->
            val (prefix, quote, _) = matchResult.destructured
            println("libs.versions.toml: versionCode changed to $newVersionCode")
            "${prefix}$quote$newVersionCode$quote"
        }
        libsVersionsToml.writeText(libsVersionsTomlContent)
        println("gradle/libs.versions.toml updated successfully.")

        println("All version updates completed successfully for TAG: $newTagName.")
    }
}
