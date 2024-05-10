package com.mshdabiola.mvvn

import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.qualifier.Qualifier

actual inline fun <reified T : ViewModel> org.koin.core.module.Module.commonViewModel(
    qualifier: Qualifier?,
    noinline definition: Definition<T>,
): KoinDefinition<T> {
    return factory(qualifier = qualifier, definition = definition)
}
