package com.mshdabiola.main

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        MainViewModel(get(), get())
    }
}
