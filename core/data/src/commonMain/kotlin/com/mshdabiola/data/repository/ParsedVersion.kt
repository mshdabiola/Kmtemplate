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
package com.mshdabiola.data.repository

data class ParsedVersion(
    val major: Int,
    val minor: Int,
    val patch: Int,
    val preReleaseType: PreReleaseType?,
    val preReleaseVersion: Int?,
) : Comparable<ParsedVersion> {

    enum class PreReleaseType {
        ALPHA,
        BETA,
        RC,
    }

    override fun compareTo(other: ParsedVersion): Int {
        if (major != other.major) return major.compareTo(other.major)
        if (minor != other.minor) return minor.compareTo(other.minor)
        if (patch != other.patch) return patch.compareTo(other.patch)

        // Handling pre-releases: A version without pre-release is newer
        if (preReleaseType == null && other.preReleaseType != null) return 1
        if (preReleaseType != null && other.preReleaseType == null) return -1
        if (preReleaseType == null && other.preReleaseType == null) return 0
        // Both are full releases and equal numeric parts

        // Both have pre-releases
        val typeComparison = (preReleaseType?.ordinal ?: -1).compareTo(other.preReleaseType?.ordinal ?: -1)
        if (typeComparison != 0) return typeComparison

        // Same pre-release type, compare pre-release versions
        return (preReleaseVersion ?: 0).compareTo(other.preReleaseVersion ?: 0)
    }

    companion object {
        // Regex:
        // Group 1: major version (digits)
        // Group 2: minor version (digits)
        // Group 3: patch version (digits)
        // Group 4: (Optional) pre-release type (letters, e.g., "alpha", "beta", "rc")
        // Group 5: (Optional, only if Group 4 exists) pre-release version number (digits, can be empty)
        // Group 6: (Optional) general suffix, starts with a hyphen
        // (e.g., "-SNAPSHOT", "-build123", or the "-1" in "alpha-1")
        private val VERSION_REGEX = Regex("^v?(\\d+)\\.(\\d+)\\.(\\d+)(?:-([a-zA-Z]+)(\\d*))?(?:-(.+))?$")

        fun fromString(versionString: String): ParsedVersion? {
            // Try the simpler regex first for common cases without a general suffix.
            val simplerRegex = Regex("^v?(\\d+)\\.(\\d+)\\.(\\d+)(?:-([a-zA-Z]+)(\\d*))?$")
            val simpleMatch = simplerRegex.find(versionString)

            if (simpleMatch != null) {
                // Check if the simpler regex consumed the entire string.
                // If not, it means there might be a general suffix, so the simpler regex isn't enough.
                if (simpleMatch.value.length == versionString.length) {
                    val majorStr = simpleMatch.groups[1]?.value ?: return null
                    val minorStr = simpleMatch.groups[2]?.value ?: return null
                    val patchStr = simpleMatch.groups[3]?.value ?: return null
                    val preReleaseTypeStr = simpleMatch.groups[4]?.value
                    val preReleaseVersionNumStr = simpleMatch.groups[5]?.value
                    return parseComponents(
                        majorStr,
                        minorStr,
                        patchStr,
                        preReleaseTypeStr,
                        preReleaseVersionNumStr,
                        null, // No general suffix for simpler match
                    )
                }
                // If simpleMatch matched but not the whole string, proceed to the more complex regex.
            }

            // If the simpler regex didn't match, or matched only a part of the string,
            // try the more comprehensive VERSION_REGEX.
            val match = VERSION_REGEX.find(versionString) ?: return null // Truly invalid format if this also fails

            val majorStr = match.groups[1]?.value ?: return null
            val minorStr = match.groups[2]?.value ?: return null
            val patchStr = match.groups[3]?.value ?: return null

            val preReleaseTypeStr = match.groups[4]?.value
            val preReleaseVersionNumStr = match.groups[5]?.value
            val generalSuffixStr = match.groups[6]?.value

            // Check for invalid format like "1.2.3-alpha--1" or "1.2.3-beta--foo"
            // This is when a pre-release type is present, its specific version number is empty,
            // AND a general suffix part immediately follows.
            if (preReleaseTypeStr != null &&
                (preReleaseVersionNumStr != null && preReleaseVersionNumStr.isEmpty()) &&
                generalSuffixStr != null
            ) {
                // This specific invalid case (e.g., "X.Y.Z-TYPE--SUFFIX") might have been
                // partially matched by simplerRegex if simplerRegex was allowed to not match the whole string.
                // However, VERSION_REGEX is more specific for this.
                return null
            }

            return parseComponents(
                majorStr,
                minorStr,
                patchStr,
                preReleaseTypeStr,
                preReleaseVersionNumStr,
                generalSuffixStr,
            )
        }

        private fun parseComponents(
            majorStr: String,
            minorStr: String,
            patchStr: String,
            preReleaseTypeStr: String?,
            preReleaseVersionNumStr: String?,
            @Suppress("UNUSED_PARAMETER") generalSuffixStr: String?,
            // Suffix is used for validation before calling this
        ): ParsedVersion? {
            val major = majorStr.toIntOrNull() ?: return null
            val minor = minorStr.toIntOrNull() ?: return null
            val patch = patchStr.toIntOrNull() ?: return null

            var parsedPreReleaseType: PreReleaseType? = null
            var parsedPreReleaseVersion: Int? = null

            if (preReleaseTypeStr != null) {
                parsedPreReleaseType = when (preReleaseTypeStr.lowercase()) {
                    "alpha" -> PreReleaseType.ALPHA
                    "beta" -> PreReleaseType.BETA
                    "rc" -> PreReleaseType.RC
                    else -> return null // Invalid pre-release type name
                }

                parsedPreReleaseVersion = if (preReleaseVersionNumStr != null &&
                    preReleaseVersionNumStr.isNotEmpty()
                ) {
                    preReleaseVersionNumStr.toIntOrNull() ?: return null // Should be digits due to regex (\d*)
                } else {
                    0 // Default to 0 if only type is present (e.g., "1.0.0-alpha")
                }
            }
            return ParsedVersion(major, minor, patch, parsedPreReleaseType, parsedPreReleaseVersion)
        }

        fun isMoreRecent(version1: String, version2: String): Boolean {
            val parsedV1 = fromString(version1)
            val parsedV2 = fromString(version2)

            if (parsedV1 == null || parsedV2 == null) {
                return false
            }
            return parsedV1 > parsedV2
        }
    }
}
