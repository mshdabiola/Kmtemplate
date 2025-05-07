package com.mshdabiola.main

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule =
    module {
        viewModelOf(::MainViewModel)
    }
