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
package com.mshdabiola.data

import com.mshdabiola.data.doubles.TestUserPreferenceDataSource
import com.mshdabiola.data.repository.RealUserDataRepository
import com.mshdabiola.model.DarkThemeConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserDataRepositoryTest {

    private lateinit var userPreferenceDataSource: TestUserPreferenceDataSource
    private lateinit var repository: RealUserDataRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        userPreferenceDataSource = TestUserPreferenceDataSource()
        repository = RealUserDataRepository(userPreferenceDataSource, testDispatcher)
    }

    @Test
    fun `initial userSettings are correct`() = runTest(testDispatcher) {
        val userSettings = repository.userSettings.first()

        assertEquals(DarkThemeConfig.FOLLOW_SYSTEM, userSettings.darkThemeConfig)
        assertFalse(userSettings.useDynamicColor)
        assertFalse(userSettings.shouldHideOnboarding)
        assertEquals(0, userSettings.contrast)
        assertTrue(userSettings.shouldShowGradientBackground)
        assertEquals("en-US", userSettings.language) // مطابق با UserSettings.kt
        assertFalse(userSettings.updateFromPreRelease)
        assertFalse(userSettings.showUpdateDialog)
    }

    @Test
    fun `setContrast updates contrast`() = runTest(testDispatcher) {
        val newContrast = 5
        repository.setContrast(newContrast)
        val userSettings = repository.userSettings.first()
        assertEquals(newContrast, userSettings.contrast)
    }

    @Test
    fun `setDarkThemeConfig updates darkThemeConfig`() = runTest(testDispatcher) {
        val newConfig = DarkThemeConfig.DARK
        repository.setDarkThemeConfig(newConfig)
        val userSettings = repository.userSettings.first()
        assertEquals(newConfig, userSettings.darkThemeConfig)
    }

    @Test
    fun `setDynamicColorPreference updates useDynamicColor`() = runTest(testDispatcher) {
        val newPreference = true
        repository.setDynamicColorPreference(newPreference)
        val userSettings = repository.userSettings.first()
        assertEquals(newPreference, userSettings.useDynamicColor)
    }

    @Test
    fun `setShouldHideOnboarding updates shouldHideOnboarding`() = runTest(testDispatcher) {
        val newValue = true
        repository.setShouldHideOnboarding(newValue)
        val userSettings = repository.userSettings.first()
        assertEquals(newValue, userSettings.shouldHideOnboarding)
    }

    @Test
    fun `setShouldShowGradientBackground updates shouldShowGradientBackground`() = runTest(testDispatcher) {
        val newValue = false
        repository.setShouldShowGradientBackground(newValue)
        val userSettings = repository.userSettings.first()
        assertEquals(newValue, userSettings.shouldShowGradientBackground)
    }

    @Test
    fun `setLanguage updates language`() = runTest(testDispatcher) {
        val newLanguage = "fr"
        repository.setLanguage(newLanguage)
        val userSettings = repository.userSettings.first()
        assertEquals(newLanguage, userSettings.language)
    }

    @Test
    fun `setUpdateFromPreRelease updates updateFromPreRelease`() = runTest(testDispatcher) {
        val newValue = true
        repository.setUpdateFromPreRelease(newValue)
        val userSettings = repository.userSettings.first()
        assertEquals(newValue, userSettings.updateFromPreRelease)
    }

    @Test
    fun `setShowUpdateDialog updates showUpdateDialog`() = runTest(testDispatcher) {
        val newValue = true
        repository.setShowUpdateDialog(newValue)
        val userSettings = repository.userSettings.first()
        assertEquals(newValue, userSettings.showUpdateDialog)
    }
}
