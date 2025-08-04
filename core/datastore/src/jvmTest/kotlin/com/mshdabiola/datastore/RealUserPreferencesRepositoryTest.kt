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
package com.mshdabiola.datastore

// Removed MockK imports
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.mshdabiola.datastore.model.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class RealUserPreferencesRepositoryTest {

    private val initialUserSettings = UserPreferences()

    private fun getDataStore(name: String): RealUserPreferencesDataSource {
        val path = File(FileSystem.SYSTEM_TEMPORARY_DIRECTORY.toFile(), "$name.json")
        if (path.exists()) {
            path.delete()
        }
        val testDataStore = DataStoreFactory.create(
            storage =
            OkioStorage(
                fileSystem = FileSystem.SYSTEM,
                serializer = UserDataJsonSerializer,
                producePath = {
                    if (!path.parentFile.exists()) {
                        path.mkdirs()
                    }
                    path.toOkioPath()
                },
            ),
        )
        return RealUserPreferencesDataSource(testDataStore)
    }

    @Test
    fun `userData flow initially emits current DataStore data`() = runTest {
        val repository = getDataStore("userdata_flow")

        val currentUserData = repository.userPreferences.first()
        assertEquals(initialUserSettings, currentUserData)
    }

    @Test
    fun `setContrast updates DataStore and flow emits new contrast`() = runTest {
        val newContrast = 50
        val expectedUserData = initialUserSettings.copy(contrast = newContrast)
        val repository = getDataStore("userdata_setContrast")

        repository.setContrast(newContrast)

        val updatedUserData = repository.userPreferences.first() // Or fakeUserDataStore.data.first()
        assertEquals(expectedUserData, updatedUserData)
    }

    @Test
    fun `setDarkThemeConfig updates DataStore and flow emits new config`() = runTest {
        val newConfig = 1
        val expectedUserData = initialUserSettings.copy(darkThemeConfig = newConfig)

        val repository = getDataStore("userdata_setDarkThemeConfig")

        repository.setDarkThemeConfig(newConfig)

        val updatedUserData = repository.userPreferences.first()
        assertEquals(expectedUserData, updatedUserData)
    }

    @Test
    fun `setDynamicColorPreference updates DataStore and flow emits new preference`() = runTest {
        val newDynamicColor = true
        val expectedUserData = initialUserSettings.copy(useDynamicColor = newDynamicColor)

        val repository = getDataStore("userdata_setDynamicColorPreference")

        repository.setDynamicColorPreference(newDynamicColor)

        val updatedUserData = repository.userPreferences.first()
        assertEquals(expectedUserData, updatedUserData)
    }

    @Test
    fun `setShouldHideOnboarding updates DataStore and flow emits new value`() = runTest {
        val newShouldHide = true
        val expectedUserData = initialUserSettings.copy(shouldHideOnboarding = newShouldHide)
        val repository = getDataStore("userdata_setShouldHideOnboarding")

        repository.setShouldHideOnboarding(newShouldHide)

        val updatedUserData = repository.userPreferences.first()
        assertEquals(expectedUserData, updatedUserData)
    }

    @Test
    fun `multiple updates are reflected in the flow`() = runTest {
        val repository = getDataStore("userdata_multiple_updates")

        // Initial state check
        assertEquals(0, repository.userPreferences.first().darkThemeConfig)

        // First update
        repository.setDarkThemeConfig(1)
        assertEquals(1, repository.userPreferences.first().darkThemeConfig)

        // Second update
        repository.setContrast(10)
        assertEquals(10, repository.userPreferences.first().contrast)
        assertEquals(1, repository.userPreferences.first().darkThemeConfig)
        // Ensure previous update persists

        val expectedUserSettings = UserPreferences(
            contrast = 10,
            darkThemeConfig = 1,
            useDynamicColor = false,
            shouldHideOnboarding = false,
        )
        assertEquals(expectedUserSettings, repository.userPreferences.first())
    }

    @Test
    fun `setShouldShowGradientBackground updates DataStore and flow emits new value`() = runTest {
        val newShouldShowGradient = false
        val expectedUserData = initialUserSettings.copy(shouldShowGradientBackground = newShouldShowGradient)
        val repository = getDataStore("userdata_setShouldShowGradientBackground")
        repository.setShouldShowGradientBackground(newShouldShowGradient)
        val updatedUserData = repository.userPreferences.first()
        assertEquals(expectedUserData, updatedUserData)
    }

    @Test
    fun `setLanguage updates DataStore and flow emits new language`() = runTest {
        val newLanguage = "en Us"
        val expectedUserData = initialUserSettings.copy(language = newLanguage)
        val repository = getDataStore("userdata_setLanguage")

        repository.setLanguage(newLanguage)

        val updatedUserData = repository.userPreferences.first()
        assertEquals(expectedUserData, updatedUserData)
    }
}
