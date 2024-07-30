package com.mshdabiola.setting

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val settingModule = module {
    viewModelOf(::SettingViewModel)
}
