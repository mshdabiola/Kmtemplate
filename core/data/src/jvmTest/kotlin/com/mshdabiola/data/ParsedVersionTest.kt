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
package com.mshdabiola.data

import com.mshdabiola.data.repository.ParsedVersion
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ParsedVersionTest {

    @Test
    fun `fromString parses simple version`() {
        val parsed = ParsedVersion.fromString("1.2.3")
        assertNotNull(parsed)
        assertEquals(1, parsed!!.major)
        assertEquals(2, parsed.minor)
        assertEquals(3, parsed.patch)
        assertNull(parsed.preReleaseType)
        assertNull(parsed.preReleaseVersion)
    }

    @Test
    fun `fromString parses version with v prefix`() {
        val parsed = ParsedVersion.fromString("v0.10.5")
        assertNotNull(parsed)
        assertEquals(0, parsed!!.major)
        assertEquals(10, parsed.minor)
        assertEquals(5, parsed.patch)
        assertNull(parsed.preReleaseType)
        assertNull(parsed.preReleaseVersion)
    }

    @Test
    fun `fromString parses alpha pre-release without number`() {
        val parsed = ParsedVersion.fromString("1.0.0-alpha")
        assertNotNull(parsed)
        assertEquals(1, parsed!!.major)
        assertEquals(0, parsed.minor)
        assertEquals(0, parsed.patch)
        assertEquals(ParsedVersion.PreReleaseType.ALPHA, parsed.preReleaseType)
        assertEquals(0, parsed.preReleaseVersion) // Defaults to 0
    }

    @Test
    fun `fromString parses alpha pre-release with number`() {
        val parsed = ParsedVersion.fromString("2.3.4-alpha1")
        assertNotNull(parsed)
        assertEquals(2, parsed!!.major)
        assertEquals(3, parsed.minor)
        assertEquals(4, parsed.patch)
        assertEquals(ParsedVersion.PreReleaseType.ALPHA, parsed.preReleaseType)
        assertEquals(1, parsed.preReleaseVersion)
    }

    @Test
    fun `fromString parses beta pre-release with multi-digit number`() {
        val parsed = ParsedVersion.fromString("v3.0.1-beta023")
        assertNotNull(parsed)
        assertEquals(3, parsed!!.major)
        assertEquals(0, parsed.minor)
        assertEquals(1, parsed.patch)
        assertEquals(ParsedVersion.PreReleaseType.BETA, parsed.preReleaseType)
        assertEquals(23, parsed.preReleaseVersion)
    }

    @Test
    fun `fromString parses rc pre-release`() {
        val parsed = ParsedVersion.fromString("0.0.1-rc5")
        assertNotNull(parsed)
        assertEquals(0, parsed!!.major)
        assertEquals(0, parsed.minor)
        assertEquals(1, parsed.patch)
        assertEquals(ParsedVersion.PreReleaseType.RC, parsed.preReleaseType)
        assertEquals(5, parsed.preReleaseVersion)
    }

    @Test
    fun `fromString parses case-insensitive pre-release type`() {
        val parsedAlpha = ParsedVersion.fromString("1.0.0-AlPhA2")
        assertNotNull(parsedAlpha)
        assertEquals(ParsedVersion.PreReleaseType.ALPHA, parsedAlpha!!.preReleaseType)
        assertEquals(2, parsedAlpha.preReleaseVersion)

        val parsedBeta = ParsedVersion.fromString("1.0.0-BeTa")
        assertNotNull(parsedBeta)
        assertEquals(ParsedVersion.PreReleaseType.BETA, parsedBeta!!.preReleaseType)
        assertEquals(0, parsedBeta.preReleaseVersion)

        val parsedRC = ParsedVersion.fromString("1.0.0-rC99")
        assertNotNull(parsedRC)
        assertEquals(ParsedVersion.PreReleaseType.RC, parsedRC!!.preReleaseType)
        assertEquals(99, parsedRC.preReleaseVersion)
    }

    // --- Test fromString invalid inputs ---

    @Test
    fun `fromString returns null for missing minor patch`() {
        assertNull(ParsedVersion.fromString("1"))
        assertNull(ParsedVersion.fromString("1.2"))
    }

    @Test
    fun `fromString returns null for non-numeric components`() {
        assertNull(ParsedVersion.fromString("a.b.c"))
        assertNull(ParsedVersion.fromString("1.x.3"))
    }

    @Test
    fun `fromString returns null for invalid pre-release type`() {
        assertNull(ParsedVersion.fromString("1.2.3-gamma1"))
        assertNull(ParsedVersion.fromString("1.2.3-dev"))
    }

    @Test
    fun `fromString returns null for pre-release type with non-numeric version`() {
        assertNull(ParsedVersion.fromString("1.2.3-alphaX"))
        assertNull(ParsedVersion.fromString("1.2.3-betaY2")) // even if part is numeric
    }

    @Test
    fun `fromString returns null for just hyphen`() {
        assertNull(ParsedVersion.fromString("1.2.3-"))
    }

    @Test
    fun `fromString returns null for extra characters after valid simple version`() {
        // These are rejected because the simplerRegex has `$`
        assertNull(ParsedVersion.fromString("1.2.3abc"))
    }

    @Test
    fun `fromString returns null for dot prefix or suffix`() {
        assertNull(ParsedVersion.fromString(".1.2.3"))
        assertNull(ParsedVersion.fromString("1.2.3."))
    }

    // --- Test specific regex handling from ParsedVersion.kt ---

    @Test
    fun `fromString correctly invalidates X_Y_Z-TYPE--SUFFIX format`() {
        // This format is specifically invalidated after being matched by VERSION_REGEX
        // preReleaseTypeStr != null && (preReleaseVersionNumStr != null &&
        // preReleaseVersionNumStr.isEmpty()) && generalSuffixStr != null
        assertNull(ParsedVersion.fromString("1.0.0-alpha-SNAPSHOT"))
        // alpha (group 4), "" (group 5), SNAPSHOT (group 6) -> invalid
        assertNull(ParsedVersion.fromString("2.1.0-beta-rc1"))
        // beta (group 4), "" (group 5), rc1 (group 6) -> invalid
        assertNull(ParsedVersion.fromString("v3.3.3-rc-custom"))
        // rc (group 4), "" (group 5), custom (group 6) -> invalid
    }

    @Test
    fun `fromString allows X_Y_Z-TYPE_VERSION-SUFFIX format`() {
        // This format is valid because preReleaseVersionNumStr is NOT empty
        val parsed = ParsedVersion.fromString("1.0.0-alpha1-SNAPSHOT")
        assertNotNull(parsed)
        assertEquals(1, parsed!!.major)
        assertEquals(0, parsed.minor)
        assertEquals(0, parsed.patch)
        assertEquals(ParsedVersion.PreReleaseType.ALPHA, parsed.preReleaseType)
        assertEquals(1, parsed.preReleaseVersion) // "SNAPSHOT" suffix is ignored by current parseComponents
    }

    // --- Test comparison logic (isMoreRecent and compareTo) ---

    @Test
    fun `isMoreRecent basic comparisons`() {
        assertTrue(ParsedVersion.isMoreRecent("1.2.3", "1.2.2"))
        assertTrue(ParsedVersion.isMoreRecent("1.3.0", "1.2.9"))
        assertTrue(ParsedVersion.isMoreRecent("2.0.0", "1.9.9"))
        assertFalse(ParsedVersion.isMoreRecent("1.2.3", "1.2.3"))
        assertFalse(ParsedVersion.isMoreRecent("1.2.3", "1.2.4"))
    }

    @Test
    fun `isMoreRecent with v prefix`() {
        assertTrue(ParsedVersion.isMoreRecent("v1.2.3", "1.2.2"))
        assertFalse(ParsedVersion.isMoreRecent("1.2.2", "v1.2.3"))
    }

    @Test
    fun `isMoreRecent full release vs pre-release`() {
        assertTrue(ParsedVersion.isMoreRecent("1.0.0", "1.0.0-rc1"))
        assertTrue(ParsedVersion.isMoreRecent("1.0.0", "1.0.0-beta5"))
        assertTrue(ParsedVersion.isMoreRecent("1.0.0", "1.0.0-alpha10"))
        assertFalse(ParsedVersion.isMoreRecent("1.0.0-alpha1", "1.0.0"))
    }

    @Test
    fun `isMoreRecent pre-release type comparisons`() {
        assertTrue(ParsedVersion.isMoreRecent("1.0.0-rc1", "1.0.0-beta2"))
        assertTrue(ParsedVersion.isMoreRecent("1.0.0-beta3", "1.0.0-alpha4"))
        assertFalse(ParsedVersion.isMoreRecent("1.0.0-alpha1", "1.0.0-beta1"))
        assertFalse(ParsedVersion.isMoreRecent("1.0.0-beta1", "1.0.0-rc1"))
    }

    @Test
    fun `isMoreRecent same pre-release type, different versions`() {
        assertTrue(ParsedVersion.isMoreRecent("1.0.0-alpha2", "1.0.0-alpha1"))
        assertTrue(ParsedVersion.isMoreRecent("1.0.0-beta10", "1.0.0-beta2"))
        assertTrue(ParsedVersion.isMoreRecent("1.0.0-rc5", "1.0.0-rc0"))
        assertFalse(ParsedVersion.isMoreRecent("1.0.0-alpha1", "1.0.0-alpha2"))
    }

    @Test
    fun `isMoreRecent with pre-release no number vs number`() {
        // "alpha" is treated as "alpha0"
        assertTrue(ParsedVersion.isMoreRecent("1.0.0-alpha1", "1.0.0-alpha"))
        assertFalse(ParsedVersion.isMoreRecent("1.0.0-alpha", "1.0.0-alpha1"))
        assertTrue(ParsedVersion.isMoreRecent("1.0.0-beta2", "1.0.0-beta"))
    }

    @Test
    fun `isMoreRecent returns false for invalid versions`() {
        assertFalse(ParsedVersion.isMoreRecent("invalid", "1.0.0"))
        assertFalse(ParsedVersion.isMoreRecent("1.0.0", "invalid"))
        assertFalse(ParsedVersion.isMoreRecent("invalid1", "invalid2"))
    }

    @Test
    fun `compareTo directly`() {
        val v100 = ParsedVersion.fromString("1.0.0")!!
        val v100a1 = ParsedVersion.fromString("1.0.0-alpha1")!!
        val v100b1 = ParsedVersion.fromString("1.0.0-beta1")!!
        val v100rc1 = ParsedVersion.fromString("1.0.0-rc1")!!
        val v101 = ParsedVersion.fromString("1.0.1")!!
        val v090 = ParsedVersion.fromString("0.9.0")!!

        assertTrue(v100 > v100rc1)
        assertTrue(v100rc1 > v100b1)
        assertTrue(v100b1 > v100a1)
        assertTrue(v100a1 > v090)
        assertTrue(v101 > v100)

        assertEquals(0, v100.compareTo(ParsedVersion.fromString("1.0.0")!!))
        assertTrue(v100.compareTo(v100a1) > 0)
        assertTrue(v100a1.compareTo(v100) < 0)
    }

    @Test
    fun `sorting versions`() {
        val versions = listOf(
            "1.0.0-alpha1",
            "1.2.3",
            "0.9.0",
            "1.0.0-rc1",
            "1.0.0-alpha2",
            "1.0.0",
            "v0.8.0",
            "1.0.0-beta10",
            "1.0.0-beta2",
        )
        val parsedAndSorted = versions.mapNotNull { ParsedVersion.fromString(it) }.sorted()

        val expectedOrder = listOf(
            ParsedVersion.fromString("v0.8.0")!!,
            ParsedVersion.fromString("0.9.0")!!,
            ParsedVersion.fromString("1.0.0-alpha1")!!,
            ParsedVersion.fromString("1.0.0-alpha2")!!,
            ParsedVersion.fromString("1.0.0-beta2")!!,
            ParsedVersion.fromString("1.0.0-beta10")!!,
            ParsedVersion.fromString("1.0.0-rc1")!!,
            ParsedVersion.fromString("1.0.0")!!,
            ParsedVersion.fromString("1.2.3")!!,
        ).sorted() // Ensure expected is also sorted by the same logic for safety

        assertEquals(expectedOrder.map { it.toString() }, parsedAndSorted.map { it.toString() })
    }
}
