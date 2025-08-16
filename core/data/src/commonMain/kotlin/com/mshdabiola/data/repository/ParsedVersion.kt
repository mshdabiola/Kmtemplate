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

    /**
     * Compares this ParsedVersion to another according to semantic-versioning-like rules.
     *
     * Comparison order:
     * 1. Major, then minor, then patch (first difference determines ordering).
     * 2. A version without a pre-release is considered newer than the same numeric version with a pre-release.
     * 3. If both have pre-releases, compare pre-release types by their enum ordinal (ALPHA < BETA < RC).
     * 4. If pre-release types are equal, compare pre-release version numbers, treating a missing number as 0.
     *
     * @return a negative integer, zero, or a positive integer as this version is less than, equal to,
     * or greater than the [other] version.
     */
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

        /**
         * Parse a semantic version string into a ParsedVersion instance.
         *
         * Accepts versions like `v1.2.3`, `1.2.3-alpha1`, `1.2.3-beta`, `1.2.3-rc2` and variants with an optional general suffix.
         * The function first attempts a common/simple form and falls back to a more comprehensive pattern for other valid forms.
         *
         * @param versionString The version string to parse.
         * @return A ParsedVersion on successful parse, or `null` if the string is not a valid/recognized version
         *         (including cases such as a pre-release type with an empty numeric component immediately followed by a general suffix).
         */
        fun fromString(versionString: String): ParsedVersion? {
            // Try the simpler regex first for common cases without a general suffix.
            val simplerRegex = Regex("^v?(\\d+)\\.(\\d+)\\.(\\d+)(?:-([a-zA-Z]+)(\\d*))?$")
            val simpleMatch = simplerRegex.find(versionString)

            if (simpleMatch != null) {
                // Check if the simpler regex consumed the entire string.
                // If not, it means there might be a general suffix, so the simpler regex isn't enough.
                if (simpleMatch.value.length == versionString.length) {
                    val majorStr = simpleMatch.groups[1]!!.value
                    val minorStr = simpleMatch.groups[2]!!.value
                    val patchStr = simpleMatch.groups[3]!!.value
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

            val majorStr = match.groups[1]!!.value
            val minorStr = match.groups[2]!!.value
            val patchStr = match.groups[3]!!.value

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

        /**
         * Parse numeric version components and optional pre-release pieces into a ParsedVersion.
         *
         * Converts the provided major/minor/patch decimal strings to integers and, if a pre-release
         * type is present, maps it to PreReleaseType (`"alpha"`, `"beta"`, `"rc"` case-insensitive).
         * If a pre-release type is present but its numeric suffix is empty, the pre-release version
         * defaults to 0 (e.g. `1.0.0-alpha` -> preReleaseVersion = 0).
         *
         * @param majorStr Decimal string for the major version component.
         * @param minorStr Decimal string for the minor version component.
         * @param patchStr Decimal string for the patch version component.
         * @param preReleaseTypeStr Optional pre-release type string (case-insensitive): `"alpha"`, `"beta"`, or `"rc"`.
         * @param preReleaseVersionNumStr Optional numeric string for the pre-release version; when present it must parse to an integer.
         * @param generalSuffixStr Optional general suffix that is not used by this routine (kept for call-site validation).
         * @return A ParsedVersion on successful parsing; `null` if any numeric parse fails or if an invalid pre-release type/name is provided.
         */
        private fun parseComponents(
            majorStr: String,
            minorStr: String,
            patchStr: String,
            preReleaseTypeStr: String?,
            preReleaseVersionNumStr: String?,
            @Suppress("UNUSED_PARAMETER") generalSuffixStr: String?,
            // Suffix is used for validation before calling this
        ): ParsedVersion? {
            val major = majorStr.toInt()
            val minor = minorStr.toInt()
            val patch = patchStr.toInt()

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

        /**
         * Returns true when the first version string represents a newer semantic version than the second.
         *
         * Both inputs are parsed using ParsedVersion.fromString. Supported formats are semantic versions
         * like `vX.Y.Z` with optional pre-release (`-alphaN`, `-betaN`, `-rcN`) and an optional general suffix.
         * If either string cannot be parsed, this function returns false.
         *
         * @param version1 Candidate newer version string.
         * @param version2 Candidate older version string to compare against.
         * @return `true` if `version1` is strictly more recent than `version2`, otherwise `false`.
         */
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
