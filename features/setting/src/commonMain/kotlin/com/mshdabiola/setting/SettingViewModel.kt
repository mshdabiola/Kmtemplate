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
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingViewModel constructor(
    private val userDataRepository: UserDataRepository,
) : ViewModel() {
    private val _settingState = MutableStateFlow<SettingState>(SettingState.Loading())
    val settingState = _settingState.asStateFlow()

    init {
        update()
    }

    fun setThemeBrand(themeBrand: ThemeBrand) {
        viewModelScope.launch {
            _settingState.value = SettingState.Loading()

            userDataRepository.setContrast(0)

            update()
        }
    }

    /**
     * Sets the desired dark theme config.
     */
    fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch {
            _settingState.value = SettingState.Loading()

            userDataRepository.setDarkThemeConfig(darkThemeConfig)

            update()
        }
    }

    private fun update() {
        viewModelScope.launch {
            _settingState.value =
                userDataRepository.userData.map {
                    SettingState.Success(
                        contrast = it.contrast,
                        darkThemeConfig = it.darkThemeConfig,
                    )
                }.first()
        }
    }
}
