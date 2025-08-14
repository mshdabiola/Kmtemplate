package com.mshdabiola.data.repository
data class ParsedVersion(
    val major: Int,
    val minor: Int,
    val patch: Int,
    val preReleaseType: PreReleaseType?,
    val preReleaseVersion: Int?
) : Comparable<ParsedVersion> {

    enum class PreReleaseType {
        ALPHA, BETA, RC
    }

    override fun compareTo(other: ParsedVersion): Int {
        if (major != other.major) return major.compareTo(other.major)
        if (minor != other.minor) return minor.compareTo(other.minor)
        if (patch != other.patch) return patch.compareTo(other.patch)

        // Handling pre-releases: A version without pre-release is newer
        if (preReleaseType == null && other.preReleaseType != null) return 1
        if (preReleaseType != null && other.preReleaseType == null) return -1
        if (preReleaseType == null && other.preReleaseType == null) return 0 // Both are full releases and equal numeric parts

        // Both have pre-releases
        val typeComparison = (preReleaseType?.ordinal ?: -1).compareTo(other.preReleaseType?.ordinal ?: -1)
        if (typeComparison != 0) return typeComparison

        // Same pre-release type, compare pre-release versions
        return (preReleaseVersion ?: 0).compareTo(other.preReleaseVersion ?: 0)
    }

    companion object {
        private val VERSION_REGEX = Regex("""^(\d+)\.(\d+)\.(\d+)(?:-([a-zA-Z]+)(\d*))?""")

        fun fromString(versionString: String): ParsedVersion? {
            val match = VERSION_REGEX.find(versionString) ?: return null
            val (majorStr, minorStr, patchStr, preReleaseTypeStr, preReleaseVersionStr) = match.destructured

            val major = majorStr.toIntOrNull() ?: return null
            val minor = minorStr.toIntOrNull() ?: return null
            val patch = patchStr.toIntOrNull() ?: return null

            var preReleaseType: PreReleaseType? = null
            var preReleaseVersion: Int? = null

            if (preReleaseTypeStr.isNotEmpty()) {
                preReleaseType = when (preReleaseTypeStr.lowercase()) {
                    "alpha" -> PreReleaseType.ALPHA
                    "beta" -> PreReleaseType.BETA
                    "rc" -> PreReleaseType.RC
                    else -> null // Or throw an error for unsupported pre-release types
                }
                preReleaseVersion = if (preReleaseVersionStr.isNotEmpty()) {
                    preReleaseVersionStr.toIntOrNull() ?: 0 // Default to 0 if number is missing after type
                } else {
                    0 // Default to 0 if only type is present e.g. "1.0.0-alpha"
                }
            }
            return ParsedVersion(major, minor, patch, preReleaseType, preReleaseVersion)
        }

        /**
         * Compares two version strings to determine if the first version is more recent than the second.
         *
         * Supports versions like:
         * - "1.2.3"
         * - "1.2.3-alpha1"
         * - "1.2.3-beta02"
         * - "1.2.3-rc5"
         *
         * @param version1 The first version string.
         * @param version2 The second version string to compare against.
         * @return True if version1 is more recent than version2, false otherwise.
         *         Returns false if either version string is invalid.
         */
        fun isMoreRecent(version1: String, version2: String): Boolean {
            val parsedV1 = ParsedVersion.fromString(version1)
            val parsedV2 = ParsedVersion.fromString(version2)

            if (parsedV1 == null || parsedV2 == null) {
                println("Warning: Invalid version string encountered. V1: '$version1', V2: '$version2'")
                return false // Or throw an IllegalArgumentException
            }

            return parsedV1 > parsedV2
        }

    }
}
