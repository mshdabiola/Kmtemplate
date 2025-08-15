/*
 * Unit tests for UserPreferences <-> UserSettings extension conversions.
 *
 * Testing library/framework: Kotlin test (kotlin.test.*). These tests should run on JUnit 5 if configured.
 */
package com.mshdabiola.data

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

import com.mshdabiola.datastore.model.UserPreferences
import com.mshdabiola.model.UserSettings
import com.mshdabiola.model.DarkThemeConfig

class ModelExtensionExtensionsTest {

    @Test
    fun `asUserSettings maps all fields on happy path`() {
        // Given
        val prefs = UserPreferences(
            useDynamicColor = true,
            shouldHideOnboarding = true,
            shouldShowGradientBackground = false,
            language = "es",
            darkThemeConfig = DarkThemeConfig.DARK.ordinal, // pick a non-default ordinal if possible
            contrast = 1.25f,
            showUpdateDialog = true,
            updateFromPreRelease = false,
        )

        // When
        val settings = prefs.asUserSettings()

        // Then
        assertTrue(settings.useDynamicColor, "useDynamicColor should be true")
        assertTrue(settings.shouldHideOnboarding, "shouldHideOnboarding should be true")
        assertFalse(settings.shouldShowGradientBackground, "shouldShowGradientBackground should be false")
        assertEquals("es", settings.language, "language should map directly")
        assertEquals(
            DarkThemeConfig.DARK,
            settings.darkThemeConfig,
            "darkThemeConfig should map from ordinal to enum"
        )
        assertEquals(1.25f, settings.contrast, "contrast should map directly")
        assertTrue(settings.showUpdateDialog, "showUpdateDialog should be true")
        assertFalse(settings.updateFromPreRelease, "updateFromPreRelease should be false")
    }

    @Test
    fun `asUserSettings falls back to FOLLOW_SYSTEM when ordinal is out of range (negative)`() {
        val prefs = UserPreferences(
            useDynamicColor = false,
            shouldHideOnboarding = false,
            shouldShowGradientBackground = false,
            language = "en",
            darkThemeConfig = -1, // invalid negative ordinal
            contrast = 1.0f,
            showUpdateDialog = false,
            updateFromPreRelease = false,
        )

        val settings = prefs.asUserSettings()

        assertEquals(
            DarkThemeConfig.FOLLOW_SYSTEM,
            settings.darkThemeConfig,
            "Invalid negative ordinal should fallback to FOLLOW_SYSTEM"
        )
    }

    @Test
    fun `asUserSettings falls back to FOLLOW_SYSTEM when ordinal equals size`() {
        val invalidOrdinal = DarkThemeConfig.entries.size // out-of-bounds (last index is size-1)
        val prefs = UserPreferences(
            useDynamicColor = false,
            shouldHideOnboarding = false,
            shouldShowGradientBackground = false,
            language = "en",
            darkThemeConfig = invalidOrdinal,
            contrast = 0.75f,
            showUpdateDialog = false,
            updateFromPreRelease = true,
        )

        val settings = prefs.asUserSettings()

        assertEquals(
            DarkThemeConfig.FOLLOW_SYSTEM,
            settings.darkThemeConfig,
            "Out-of-range ordinal should fallback to FOLLOW_SYSTEM"
        )
    }

    @Test
    fun `asUserSettings supports highest valid ordinal`() {
        val lastValidOrdinal = DarkThemeConfig.entries.last().ordinal
        val prefs = UserPreferences(
            useDynamicColor = false,
            shouldHideOnboarding = true,
            shouldShowGradientBackground = true,
            language = "fr",
            darkThemeConfig = lastValidOrdinal,
            contrast = 2.0f,
            showUpdateDialog = true,
            updateFromPreRelease = true,
        )

        val settings = prefs.asUserSettings()

        assertEquals(
            DarkThemeConfig.entries[lastValidOrdinal],
            settings.darkThemeConfig,
            "Should map to the enum at the last valid ordinal"
        )
    }

    @Test
    fun `asUserPreferences maps all fields correctly`() {
        // Given
        val settings = UserSettings(
            useDynamicColor = true,
            shouldHideOnboarding = false,
            shouldShowGradientBackground = true,
            language = "yo",
            darkThemeConfig = DarkThemeConfig.LIGHT,
            contrast = 1.5f,
            showUpdateDialog = false,
            updateFromPreRelease = true,
        )

        // When
        val prefs = settings.asUserPreferences()

        // Then
        assertTrue(prefs.useDynamicColor, "useDynamicColor should be true")
        assertFalse(prefs.shouldHideOnboarding, "shouldHideOnboarding should be false")
        assertTrue(prefs.shouldShowGradientBackground, "shouldShowGradientBackground should be true")
        assertEquals("yo", prefs.language, "language should map directly")
        assertEquals(
            DarkThemeConfig.LIGHT.ordinal,
            prefs.darkThemeConfig,
            "darkThemeConfig should map from enum to ordinal"
        )
        assertEquals(1.5f, prefs.contrast, "contrast should map directly")
        assertFalse(prefs.showUpdateDialog, "showUpdateDialog should be false")
        assertTrue(prefs.updateFromPreRelease, "updateFromPreRelease should be true")
    }

    @Test
    fun `round-trip conversion preserves values for valid ordinals`() {
        for (entry in DarkThemeConfig.entries) {
            val original = UserSettings(
                useDynamicColor = true,
                shouldHideOnboarding = true,
                shouldShowGradientBackground = false,
                language = "de",
                darkThemeConfig = entry,
                contrast = 0.9f,
                showUpdateDialog = false,
                updateFromPreRelease = false,
            )

            val roundTripped = original.asUserPreferences().asUserSettings()

            assertEquals(original.useDynamicColor, roundTripped.useDynamicColor)
            assertEquals(original.shouldHideOnboarding, roundTripped.shouldHideOnboarding)
            assertEquals(original.shouldShowGradientBackground, roundTripped.shouldShowGradientBackground)
            assertEquals(original.language, roundTripped.language)
            assertEquals(original.darkThemeConfig, roundTripped.darkThemeConfig, "darkThemeConfig should survive round-trip")
            assertEquals(original.contrast, roundTripped.contrast)
            assertEquals(original.showUpdateDialog, roundTripped.showUpdateDialog)
            assertEquals(original.updateFromPreRelease, roundTripped.updateFromPreRelease)
        }
    }

    @Test
    fun `invalid ordinal maps to FOLLOW_SYSTEM then to ordinal of FOLLOW_SYSTEM on round-trip`() {
        val invalidOrdinal = Int.MAX_VALUE
        val prefs = UserPreferences(
            useDynamicColor = false,
            shouldHideOnboarding = false,
            shouldShowGradientBackground = false,
            language = "en",
            darkThemeConfig = invalidOrdinal,
            contrast = 1.0f,
            showUpdateDialog = false,
            updateFromPreRelease = false,
        )

        val settings = prefs.asUserSettings()
        val roundTrippedPrefs = settings.asUserPreferences()

        assertEquals(DarkThemeConfig.FOLLOW_SYSTEM, settings.darkThemeConfig, "Invalid ordinal -> FOLLOW_SYSTEM")
        assertEquals(
            DarkThemeConfig.FOLLOW_SYSTEM.ordinal,
            roundTrippedPrefs.darkThemeConfig,
            "Round-trip back to ordinal of FOLLOW_SYSTEM"
        )
    }
}