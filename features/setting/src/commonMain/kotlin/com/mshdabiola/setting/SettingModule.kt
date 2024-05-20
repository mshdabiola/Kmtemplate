package com.mshdabiola.setting

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingModule = module {
    viewModel {
        SettingViewModel(get())
    }
}
