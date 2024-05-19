package com.mshdabiola.skeletonapp.di

import com.mshdabiola.data.di.dataModule
import com.mshdabiola.detail.detailModule
import com.mshdabiola.main.mainModule
import com.mshdabiola.setting.settingModule
import com.mshdabiola.skeletonapp.MainAppViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    includes(dataModule, detailModule, mainModule, settingModule)
    viewModel{ MainAppViewModel(get()) }
}
