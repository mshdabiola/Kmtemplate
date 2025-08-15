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
        // Updated Regex: allows optional 'v' prefix and optional arbitrary suffix like -SNAPSHOT
        private val VERSION_REGEX = Regex("""^v?(\d+)\.(\d+)\.(\d+)(?:-([a-zA-Z]+)(\d*))?(?:-.+)?$""")

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
                    else -> {
                        // If preReleaseTypeStr is not recognized, but preReleaseVersionStr might be digits,
                        // this combination is invalid. However, if preReleaseVersionStr is empty,
                        // it could be something like "1.0.0-custom" which is fine (parsed as full version).
                        // The (?:-.+)? in regex handles suffixes not matching pre-release pattern.
                        // If preReleaseTypeStr is not empty and not recognized, it's an invalid format.
                        return null
                    }
                }
                preReleaseVersion = if (preReleaseVersionStr.isNotEmpty()) {
                    preReleaseVersionStr.toIntOrNull()
                } else {
                    0 // Default to 0 if only type is present e.g. "1.0.0-alpha"
                }
                // This check is tricky. If preReleaseVersionStr is "abc", toIntOrNull is null.
                // If preReleaseType was valid (e.g. "alpha") but version part is "abc",
                // it should be invalid.
                // Current regex (\d*) ensures preReleaseVersionStr is digits or empty,
                // so toIntOrNull on non-empty preReleaseVersionStr won't be null.
                // If preReleaseVersionStr is empty, preReleaseVersion becomes 0.
                // So, `preReleaseVersion` (the Int?) is effectively never null here.
                // The new condition: if (preReleaseVersion == null && preReleaseTypeStr.isNotEmpty() && preReleaseType == null) return null
                // This seems to intend to catch a state where preReleaseTypeStr was populated but NOT a valid enum,
                // and preReleaseVersionStr couldn't be parsed.
                // However, if preReleaseTypeStr is not empty and not a valid enum, we already returned null above.
                // So, the `preReleaseType == null` part of this new condition would only be met if preReleaseTypeStr was empty.
                // If preReleaseTypeStr is empty, then `preReleaseTypeStr.isNotEmpty()` is false, short-circuiting.
                // This specific new 'if' condition seems to have no effect with the current surrounding logic and regex.
                // The original `if (preReleaseVersion == null) return null;` also had no effect with \d*.
                if (preReleaseVersion == null && preReleaseTypeStr.isNotEmpty() && preReleaseType == null) return null

            }
            return ParsedVersion(major, minor, patch, preReleaseType, preReleaseVersion)
        }

        /**
         * Compares two version strings to determine if the first version is more recent than the second.
         *
         * Supports versions like:
         * - "1.2.3"
         * - "v1.2.3"
         * - "1.2.3-alpha1"
         * - "v1.2.3-beta02"
         * - "1.2.3-rc5-SNAPSHOT"
         *
         * @param version1 The first version string.
         * @param version2 The second version string to compare against.
         * @return True if version1 is more recent than version2, false otherwise.
         *         Returns false if either version string is invalid.
         */
        fun isMoreRecent(version1: String, version2: String): Boolean {
            val parsedV1 = fromString(version1)
            val parsedV2 = fromString(version2)

            if (parsedV1 == null || parsedV2 == null) {
                println("Warning: Invalid version string encountered. V1: '$version1', V2: '$version2'")
                // Consider whether to throw or return false. Current code throws.
                throw IllegalArgumentException("Invalid version string encountered. V1: '$version1', V2: '$version2'")
            }

            return parsedV1 > parsedV2
        }

    }
}
