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
