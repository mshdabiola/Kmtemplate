package com.mshdabiola.network.di

import com.mshdabiola.network.INetworkDataSource
import com.mshdabiola.network.NetworkDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule =
    module {

        singleOf(::httpClient)
        singleOf(::NetworkDataSource) bind INetworkDataSource::class
    }
