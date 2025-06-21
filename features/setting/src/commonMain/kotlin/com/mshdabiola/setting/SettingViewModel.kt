/*
 * Copyright (C) 2022-2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
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

            userDataRepository.setThemeBrand(themeBrand)

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
                        themeBrand = it.themeBrand,
                        darkThemeConfig = it.darkThemeConfig,
                    )
                }.first()
        }
    }
}
