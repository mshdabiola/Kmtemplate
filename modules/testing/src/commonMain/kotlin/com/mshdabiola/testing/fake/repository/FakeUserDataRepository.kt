/*
 *abiola 2022
 */

package com.mshdabiola.testing.fake.repository

import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Fake implementation of the [UserDataRepository] that returns hardcoded user data.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakeUserDataRepository : UserDataRepository {

    private val  _userData = MutableStateFlow(UserData())

    override val userData: Flow<UserData> =
        _userData.asStateFlow()

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        _userData.update {
            it.copy(themeBrand = themeBrand)
        }

    }


    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        _userData.update {
            it.copy(darkThemeConfig = darkThemeConfig)
        }
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        _userData.update {
            it.copy(useDynamicColor = useDynamicColor)
        }
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
       _userData.update{
           it.copy(shouldHideOnboarding = shouldHideOnboarding)
       }
    }
}
