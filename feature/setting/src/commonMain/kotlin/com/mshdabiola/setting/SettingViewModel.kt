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
package com.mshdabiola.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.NetworkRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ReleaseInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingViewModel(
    private val userDataRepository: UserDataRepository,
    private val networkRepository: NetworkRepository,
    private val platform: Platform,
) : ViewModel() {

    private val releaseInfoFlow = MutableStateFlow<ReleaseInfo?>(null)
    val settingState = combine(
        userDataRepository.userSettings,
        releaseInfoFlow,
    ) { userSettings, releaseInfo ->
        SettingState(
            platform = platform,
            userSettings = userSettings,
            releaseInfo = releaseInfo,
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = SettingState(platform = platform),
        )

    fun setContrast(contrast: Int) {
        viewModelScope.launch {
            userDataRepository.setContrast(contrast)
        }
    }

    fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch {
            userDataRepository.setDarkThemeConfig(darkThemeConfig)
        }
    }

    fun setGradientBackground(gradientBackground: Boolean) {
        viewModelScope.launch {
            userDataRepository.setShouldShowGradientBackground(gradientBackground)
        }
    }

    fun setLanguage(language: String) {
        viewModelScope.launch {
            userDataRepository.setLanguage(language)
        }
    }

    fun setUpdateFromPreRelease(updateFromPreRelease: Boolean) {
        viewModelScope.launch {
            userDataRepository.setUpdateFromPreRelease(updateFromPreRelease)
        }
    }

    fun setShowDialog(showDialog: Boolean) {
        viewModelScope.launch {
            userDataRepository.setShowUpdateDialog(showDialog)
        }
    }

    fun checkForUpdate(currentVersion: String) {
        viewModelScope.launch {
            val allowPreRelease = settingState.value.userSettings.updateFromPreRelease
            val releaseInfo = networkRepository.getLatestReleaseInfo(currentVersion, allowPreRelease)
            releaseInfoFlow.value = releaseInfo
        }
    }

    fun hideUpdateDialog() {
        releaseInfoFlow.value = null
    }
}
