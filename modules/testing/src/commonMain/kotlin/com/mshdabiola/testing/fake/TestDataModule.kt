/*
 * Copyright (C) 2024-2025 MshdAbiola
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
package com.mshdabiola.testing.fake

import com.mshdabiola.analytics.di.analyticsModule
import com.mshdabiola.data.repository.INetworkRepository
import com.mshdabiola.data.repository.NoteRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.testing.di.testDispatcherModule
import com.mshdabiola.testing.fake.repository.FakeNetworkRepository
import com.mshdabiola.testing.fake.repository.FakeNoteRepository
import com.mshdabiola.testing.fake.repository.FakeUserDataRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val testDataModule =
    module {
        includes(testDispatcherModule, analyticsModule)
        singleOf(::FakeNetworkRepository) bind INetworkRepository::class
        singleOf(::FakeNoteRepository) bind NoteRepository::class
        singleOf(::FakeUserDataRepository) bind UserDataRepository::class
    }
