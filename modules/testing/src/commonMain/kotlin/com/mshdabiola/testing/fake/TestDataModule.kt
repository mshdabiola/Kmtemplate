/*
 *abiola 2024
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
