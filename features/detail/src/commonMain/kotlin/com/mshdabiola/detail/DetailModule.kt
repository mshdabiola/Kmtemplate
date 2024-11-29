package com.mshdabiola.detail

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel { param ->
        DetailViewModel(param.get(), get())
    }
}
