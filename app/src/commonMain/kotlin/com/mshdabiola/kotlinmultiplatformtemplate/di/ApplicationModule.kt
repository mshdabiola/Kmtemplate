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
package com.mshdabiola.kotlinmultiplatformtemplate.di

import com.mshdabiola.data.di.dataModule
import com.mshdabiola.detail.detailModule
import com.mshdabiola.kotlinmultiplatformtemplate.MainAppViewModel
import com.mshdabiola.main.mainModule
import com.mshdabiola.setting.settingModule
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule =
    module {
        includes(dataModule, detailModule, mainModule, settingModule)
        viewModel { MainAppViewModel(get()) }
    }
