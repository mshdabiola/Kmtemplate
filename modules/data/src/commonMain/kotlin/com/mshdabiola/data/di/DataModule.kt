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
package com.mshdabiola.data.di

import com.mshdabiola.analytics.di.analyticsModule
import com.mshdabiola.data.repository.INetworkRepository
import com.mshdabiola.data.repository.OfflineFirstUserDataRepository
import com.mshdabiola.data.repository.RealINetworkRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.datastore.di.datastoreModule
import com.mshdabiola.network.di.networkModule
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val dataModule: Module
//    module {
//    includes(datastoreModule, databaseModule, networkModule, analyticsModule)
//    single { Dispatchers.IO } bind CoroutineDispatcher::class
//    singleOf(::RealINetworkRepository) bind INetworkRepository::class
//    singleOf(::RealModelRepository) bind NoteRepository::class
//    singleOf(::OfflineFirstUserDataRepository) bind UserDataRepository::class
// }

val commonModule =
    module {
        includes(datastoreModule, networkModule, analyticsModule)
        singleOf(::RealINetworkRepository) bind INetworkRepository::class
        singleOf(::OfflineFirstUserDataRepository) bind UserDataRepository::class
    }
