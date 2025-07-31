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

import org.gradle.api.logging.Logger
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun updateVersionInfoInStringsXml(
    newVersionCode: String,
    newVersionName: String,
    stringsXmlFile: File,
    logger: Logger,
) {
    if (!stringsXmlFile.exists()) {
        logger.error("ERROR: strings.xml file not found at ${stringsXmlFile.path}")
        return
    }

    val currentDateTime = getCurrentFormattedDateTime()

    logger.lifecycle("Updating strings in ${stringsXmlFile.path} using Regex:")
    logger.lifecycle("  - version_name: $newVersionName")
    logger.lifecycle("  - version (code): $newVersionCode")
    logger.lifecycle("  - last_update: $currentDateTime")

    try {
        var content = stringsXmlFile.readText(Charsets.UTF_8)
        var modified = false

        // Regex to find and replace version_name
        // It looks for <string name="version_name">...</string>
        // (?s) allows . to match newline characters if the content spans multiple lines
        // (.*?) is a non-greedy match for the content between the tags.
        val versionNameRegex = """(<string name="version_name">)(.*?)(</string>)"""
        val updatedVersionNameContent = content.replace(
            Regex(versionNameRegex, RegexOption.DOT_MATCHES_ALL),
        ) { matchResult ->
            modified = true
            "${matchResult.groupValues[1]}$newVersionName${matchResult.groupValues[3]}"
        }
        if (content != updatedVersionNameContent) {
            logger.lifecycle("  SUCCESS: Updated version_name")
            content = updatedVersionNameContent
        } else {
            logger.warn("  WARNING: version_name tag not found or not updated.")
        }

        // Regex to find and replace version (assuming this corresponds to newVersionCode)
        val versionRegex = """(<string name="version">)(.*?)(</string>)"""
        val updatedVersionCodeContent = content.replace(
            Regex(
                versionRegex,
                RegexOption.DOT_MATCHES_ALL,
            ),
        ) { matchResult ->
            modified = true
            "${matchResult.groupValues[1]}$newVersionCode${matchResult.groupValues[3]}"
        }
        if (content != updatedVersionCodeContent) {
            logger.lifecycle("  SUCCESS: Updated version (code)")
            content = updatedVersionCodeContent
        } else {
            logger.warn("  WARNING: version tag not found or not updated.")
        }

        // Regex to find and replace last_update
        val lastUpdateRegex = """(<string name="last_update">)(.*?)(</string>)"""
        val updatedLastUpdateContent = content.replace(
            Regex(
                lastUpdateRegex,
                RegexOption.DOT_MATCHES_ALL,
            ),
        ) { matchResult ->
            modified = true
            "${matchResult.groupValues[1]}$currentDateTime${matchResult.groupValues[3]}"
        }
        if (content != updatedLastUpdateContent) {
            logger.lifecycle("  SUCCESS: Updated last_update")
            content = updatedLastUpdateContent
        } else {
            logger.warn("  WARNING: last_update tag not found or not updated.")
        }

        if (modified) {
            stringsXmlFile.writeText(content, Charsets.UTF_8)
            logger.lifecycle("SUCCESS: strings.xml was modified and saved.")
        } else {
            logger.lifecycle("INFO: No changes were made to strings.xml.")
        }
    } catch (e: Exception) {
        logger.error("ERROR: Failed to update strings.xml using Regex: ${e.message}", e)
        // Consider re-throwing or handling more specifically if this task is critical
    }
}

// Helper function (can be the same as before)
private fun getCurrentFormattedDateTime(): String {
    val sdf = SimpleDateFormat("dd, MMMM yyyy HH:mm", Locale.ENGLISH)
    return sdf.format(Date())
}
