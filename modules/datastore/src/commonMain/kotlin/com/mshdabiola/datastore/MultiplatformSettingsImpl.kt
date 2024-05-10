package com.mshdabiola.datastore

import androidx.datastore.core.DataStore
import com.mshdabiola.datastore.model.UserDataSer
import com.mshdabiola.datastore.model.toData
import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class MultiplatformSettingsImpl(
    private val settings: DataStore<UserDataSer>,
    private val coroutineDispatcher: CoroutineDispatcher,
) : MultiplatformSettings {

    override val userData: Flow<UserData>
        get() = settings
            .data
            .map { it.toData() }
            .flowOn(coroutineDispatcher)

    // MutableStateFlow(Keys.Defaults.defaultDummy.toDummySetting())

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        withContext(coroutineDispatcher) {
            settings.updateData { it.copy(themeBrand = themeBrand) }
        }
    }

    override suspend fun setThemeContrast(contrast: Contrast) {
        withContext(coroutineDispatcher) {
            settings.updateData { it.copy(contrast = contrast) }
        }
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        withContext(coroutineDispatcher) {
            settings.updateData { it.copy(useDynamicColor = useDynamicColor) }
        }
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        withContext(coroutineDispatcher) {
            settings.updateData { it.copy(darkThemeConfig = darkThemeConfig) }
        }
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        withContext(coroutineDispatcher) {
            settings.updateData { it.copy(shouldHideOnboarding = shouldHideOnboarding) }
        }
    }
}
