package com.mshdabiola.data.di

import com.mshdabiola.data.repository.NoteRepository
import com.mshdabiola.data.repository.RealModelRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val dataModule: Module
    get() =
        module {
            includes(commonModule)
            single { Dispatchers.Default } bind CoroutineDispatcher::class
            singleOf(::RealModelRepository) bind NoteRepository::class
        }
