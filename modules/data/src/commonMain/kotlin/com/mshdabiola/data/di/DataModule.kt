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
