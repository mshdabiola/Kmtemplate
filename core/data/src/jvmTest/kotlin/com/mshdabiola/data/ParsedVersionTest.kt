package com.mshdabiola.data

import com.mshdabiola.data.repository.ParsedVersion
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ParsedVersionTest {

    // Test cases for ParsedVersion.fromString()
    @Test
    fun `fromString handles valid full versions`() {
        val version = ParsedVersion.fromString("1.2.3")
        assertNotNull(version)
        assertEquals(1, version.major)
        assertEquals(2, version.minor)
        assertEquals(3, version.patch)
        assertNull(version.preReleaseType)
        assertNull(version.preReleaseVersion)
    }

    @Test
    fun `fromString handles valid versions with alpha pre-release`() {
        val version = ParsedVersion.fromString("1.0.0-alpha1")
        assertNotNull(version)
        assertEquals(1, version.major)
        assertEquals(0, version.minor)
        assertEquals(0, version.patch)
        assertEquals(ParsedVersion.PreReleaseType.ALPHA, version.preReleaseType)
        assertEquals(1, version.preReleaseVersion)
    }

    @Test
    fun `fromString handles valid versions with beta pre-release and leading zero`() {
        val version = ParsedVersion.fromString("2.3.4-beta02")
        assertNotNull(version)
        assertEquals(2, version.major)
        assertEquals(3, version.minor)
        assertEquals(4, version.patch)
        assertEquals(ParsedVersion.PreReleaseType.BETA, version.preReleaseType)
        assertEquals(2, version.preReleaseVersion)
    }

    @Test
    fun `fromString handles valid versions with rc pre-release`() {
        val version = ParsedVersion.fromString("3.0.0-rc5")
        assertNotNull(version)
        assertEquals(3, version.major)
        assertEquals(0, version.minor)
        assertEquals(0, version.patch)
        assertEquals(ParsedVersion.PreReleaseType.RC, version.preReleaseType)
        assertEquals(5, version.preReleaseVersion)
    }

    @Test
    fun `fromString handles valid versions with pre-release type but no number`() {
        val versionAlpha = ParsedVersion.fromString("1.0.0-alpha")
        assertNotNull(versionAlpha)
        assertEquals(ParsedVersion.PreReleaseType.ALPHA, versionAlpha.preReleaseType)
        assertEquals(0, versionAlpha.preReleaseVersion, "Expected pre-release version 0 for -alpha")

        val versionBeta = ParsedVersion.fromString("1.0.0-beta")
        assertNotNull(versionBeta)
        assertEquals(ParsedVersion.PreReleaseType.BETA, versionBeta.preReleaseType)
        assertEquals(0, versionBeta.preReleaseVersion, "Expected pre-release version 0 for -beta")

        val versionRC = ParsedVersion.fromString("1.0.0-rc")
        assertNotNull(versionRC)
        assertEquals(ParsedVersion.PreReleaseType.RC, versionRC.preReleaseType)
        assertEquals(0, versionRC.preReleaseVersion, "Expected pre-release version 0 for -rc")
    }
    @Test
    fun `fromString returns null for invalid version short string`() {
        assertNull(ParsedVersion.fromString("1.2"))
    }

    @Test
    fun `fromString returns null for non-numeric component`() {
        assertNull(ParsedVersion.fromString("1.a.3"))
    }

    @Test
    fun `fromString returns null for unsupported pre-release type`() {
        assertNull(ParsedVersion.fromString("1.2.3-gamma1"))
    }
    @Test
    fun `fromString returns null for invalid pre-release format`() {
        assertNull(ParsedVersion.fromString("1.2.3-alpha-1")) // Multiple hyphens not standard
    }

    @Test
    fun `fromString returns null for completely invalid string`() {
        assertNull(ParsedVersion.fromString("abc"))
    }

    @Test
    fun `fromString returns null for empty string`() {
        assertNull(ParsedVersion.fromString(""))
    }

    @Test
    fun `fromString handles large version numbers`() {
        val version = ParsedVersion.fromString("12345.67890.112233")
        assertNotNull(version)
        assertEquals(12345, version.major)
        assertEquals(67890, version.minor)
        assertEquals(112233, version.patch)
    }

    // Test cases for ParsedVersion.compareTo()
    @Test
    fun `compareTo distinguishes major versions`() {
        assertTrue(ParsedVersion.fromString("2.0.0")!! > ParsedVersion.fromString("1.9.9")!!)
        assertTrue(ParsedVersion.fromString("1.0.0")!! < ParsedVersion.fromString("2.0.0")!!)
    }

    @Test
    fun `compareTo distinguishes minor versions`() {
        assertTrue(ParsedVersion.fromString("1.2.0")!! > ParsedVersion.fromString("1.1.9")!!)
        assertTrue(ParsedVersion.fromString("1.1.0")!! < ParsedVersion.fromString("1.2.0")!!)
    }

    @Test
    fun `compareTo distinguishes patch versions`() {
        assertTrue(ParsedVersion.fromString("1.0.2")!! > ParsedVersion.fromString("1.0.1")!!)
        assertTrue(ParsedVersion.fromString("1.0.1")!! < ParsedVersion.fromString("1.0.2")!!)
    }

    @Test
    fun `compareTo handles equal full versions`() {
        assertEquals(0, ParsedVersion.fromString("1.0.0")!!.compareTo(ParsedVersion.fromString("1.0.0")!!))
    }

    @Test
    fun `compareTo full release is greater than pre-release`() {
        assertTrue(ParsedVersion.fromString("1.0.0")!! > ParsedVersion.fromString("1.0.0-rc1")!!)
        assertTrue(ParsedVersion.fromString("1.0.0-alpha1")!! < ParsedVersion.fromString("1.0.0")!!)
    }

    @Test
    fun `compareTo distinguishes pre-release types`() {
        assertTrue(ParsedVersion.fromString("1.0.0-beta1")!! > ParsedVersion.fromString("1.0.0-alpha1")!!)
        assertTrue(ParsedVersion.fromString("1.0.0-rc1")!! > ParsedVersion.fromString("1.0.0-beta1")!!)
        assertTrue(ParsedVersion.fromString("1.0.0-alpha2")!! < ParsedVersion.fromString("1.0.0-beta1")!!)
    }

    @Test
    fun `compareTo distinguishes pre-release versions of same type`() {
        assertTrue(ParsedVersion.fromString("1.0.0-alpha2")!! > ParsedVersion.fromString("1.0.0-alpha1")!!)
        assertTrue(ParsedVersion.fromString("1.0.0-beta0")!! < ParsedVersion.fromString("1.0.0-beta1")!!)
    }
     @Test
    fun `compareTo handles pre-release with implicit zero version`() {
        // "1.0.0-alpha" is equivalent to "1.0.0-alpha0"
        val vAlpha0 = ParsedVersion.fromString("1.0.0-alpha")!!
        val vAlpha1 = ParsedVersion.fromString("1.0.0-alpha1")!!
        assertTrue(vAlpha1 > vAlpha0)
        assertEquals(0, vAlpha0.compareTo(ParsedVersion.fromString("1.0.0-alpha0")!!))
    }


    @Test
    fun `compareTo handles equal pre-release versions`() {
        assertEquals(0, ParsedVersion.fromString("1.0.0-alpha1")!!.compareTo(ParsedVersion.fromString("1.0.0-alpha1")!!))
    }

    // Test cases for ParsedVersion.isMoreRecent()
    @Test
    fun `isMoreRecent basic cases`() {
        assertTrue(ParsedVersion.isMoreRecent("1.0.1", "1.0.0"))
        assertFalse(ParsedVersion.isMoreRecent("1.0.0", "1.0.1"))
        assertFalse(ParsedVersion.isMoreRecent("1.0.0", "1.0.0")) // Not more recent if equal
    }

    @Test
    fun `isMoreRecent with pre-releases`() {
        assertTrue(ParsedVersion.isMoreRecent("1.0.0", "1.0.0-rc1"))
        assertFalse(ParsedVersion.isMoreRecent("1.0.0-rc1", "1.0.0"))
        assertTrue(ParsedVersion.isMoreRecent("1.0.0-beta2", "1.0.0-beta1"))
        assertTrue(ParsedVersion.isMoreRecent("1.0.0-rc1", "1.0.0-beta5"))
    }

    @Test
    fun `isMoreRecent with one invalid version string`() {
        assertFalse(ParsedVersion.isMoreRecent("1.0.0", "invalid"))
        assertFalse(ParsedVersion.isMoreRecent("invalid", "1.0.0"))
    }

    @Test
    fun `isMoreRecent with both invalid version strings`() {
        assertFalse(ParsedVersion.isMoreRecent("invalid1", "invalid2"))
    }

    @Test
    fun `fromString with mixed case pre-release types`() {
        val versionAlpha = ParsedVersion.fromString("1.0.0-AlpHa1")
        assertNotNull(versionAlpha)
        assertEquals(ParsedVersion.PreReleaseType.ALPHA, versionAlpha.preReleaseType)
        assertEquals(1, versionAlpha.preReleaseVersion)

        val versionBeta = ParsedVersion.fromString("2.3.4-BeTa02")
        assertNotNull(versionBeta)
        assertEquals(ParsedVersion.PreReleaseType.BETA, versionBeta.preReleaseType)
        assertEquals(2, versionBeta.preReleaseVersion)

        val versionRC = ParsedVersion.fromString("3.0.0-rC5")
        assertNotNull(versionRC)
        assertEquals(ParsedVersion.PreReleaseType.RC, versionRC.preReleaseType)
        assertEquals(5, versionRC.preReleaseVersion)
    }
}
