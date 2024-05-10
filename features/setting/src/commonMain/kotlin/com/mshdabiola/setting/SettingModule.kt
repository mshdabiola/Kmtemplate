package com.mshdabiola.setting

import com.mshdabiola.mvvn.commonViewModel
import org.koin.dsl.module

val settingModule = module {
    commonViewModel {
        SettingViewModel(get())
    }
}
