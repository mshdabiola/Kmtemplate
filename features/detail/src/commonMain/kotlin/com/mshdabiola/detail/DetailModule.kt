package com.mshdabiola.detail

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel { param ->
        DetailViewModel(param.get(), get())
    }
}
