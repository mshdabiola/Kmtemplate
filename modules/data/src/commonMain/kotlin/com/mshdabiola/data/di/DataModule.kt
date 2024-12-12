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

val commonModule = module {
    includes(datastoreModule, networkModule, analyticsModule)
    singleOf(::RealINetworkRepository) bind INetworkRepository::class
    singleOf(::OfflineFirstUserDataRepository) bind UserDataRepository::class
}
