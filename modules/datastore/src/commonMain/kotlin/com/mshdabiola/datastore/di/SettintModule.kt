package com.mshdabiola.datastore.di

import com.mshdabiola.datastore.MultiplatformSettings
import com.mshdabiola.datastore.MultiplatformSettingsImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val commonModule = module {
    // single { Dispatchers.IO }

    singleOf(::MultiplatformSettingsImpl) bind MultiplatformSettings::class
}
