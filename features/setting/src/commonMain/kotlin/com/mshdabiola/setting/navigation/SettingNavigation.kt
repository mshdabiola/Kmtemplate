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
package com.mshdabiola.setting.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.dialog
import com.mshdabiola.setting.SettingRoute
import com.mshdabiola.setting.SettingViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

fun NavController.navigateToSetting(navOptions: NavOptions = androidx.navigation.navOptions { }) =
    navigate(
        Setting,
        navOptions,
    )

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.settingScreen(
    modifier: Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
) {
    dialog<Setting> {
        val viewModel: SettingViewModel = koinViewModel()

        SettingRoute(
            modifier = modifier,
            onShowSnack = onShowSnack,
            viewModel = viewModel,
            onBack = onBack,
        )
    }
}
