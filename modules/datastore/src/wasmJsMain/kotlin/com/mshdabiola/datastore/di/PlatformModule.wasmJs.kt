package com.mshdabiola.datastore.di

import com.mshdabiola.datastore.Store
import com.mshdabiola.datastore.StoreImple
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual val datastoreModule: Module
    get() =
        module {
            single {
                StoreImple()
            } bind Store::class
        }
