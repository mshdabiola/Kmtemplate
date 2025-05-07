package com.mshdabiola.datastore.di

import com.mshdabiola.datastore.Store
import com.mshdabiola.datastore.StoreImpl
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.module

internal val commonModule =
    module {
        // single { Dispatchers.IO }

        single {
            StoreImpl(
                userdata = get(qualifier = qualifier("userdata")),
                coroutineDispatcher = get(),
            )
        } bind Store::class
    }
