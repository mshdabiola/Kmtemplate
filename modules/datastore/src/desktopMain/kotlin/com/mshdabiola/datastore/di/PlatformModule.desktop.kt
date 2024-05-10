package com.mshdabiola.datastore.di

import com.mshdabiola.datastore.createDataStoreUserData
import com.mshdabiola.model.generalPath
import org.koin.core.module.Module
import org.koin.dsl.module

actual val datastoreModule: Module
    get() = module {
        includes(commonModule)
        single {
            createDataStoreUserData { "$generalPath/userdata" }
        }
    }
