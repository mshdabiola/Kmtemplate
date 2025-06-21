/*
 * Copyright (C) 2025 MshdAbiola
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

import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand

sealed class SettingState {
    data class Loading(val isLoading: Boolean = false) : SettingState()

    data class Success(
        val themeBrand: ThemeBrand = ThemeBrand.DEFAULT,
        val darkThemeConfig: DarkThemeConfig = DarkThemeConfig.DARK,
    ) : SettingState()

    data class Error(val exception: Throwable) : SettingState()
}

fun SettingState.getSuccess(value: (SettingState.Success) -> SettingState.Success): SettingState {
    return if (this is SettingState.Success) {
        value(this)
    } else {
        this
    }
}
