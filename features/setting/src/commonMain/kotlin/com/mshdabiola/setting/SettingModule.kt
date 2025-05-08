package com.mshdabiola.setting

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingModule =
    module {
        viewModelOf(::SettingViewModel)
    }
