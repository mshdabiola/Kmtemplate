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
