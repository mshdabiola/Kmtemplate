package com.mshdabiola.mvvn

import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.qualifier.Qualifier

expect inline fun <reified T : ViewModel> org.koin.core.module.Module.commonViewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>,
): KoinDefinition<T>
