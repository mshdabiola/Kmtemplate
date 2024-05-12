package com.mshdabiola.mvvn

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@OptIn(KoinInternalApi::class)
@Composable
actual inline fun <reified T> get(
    qualifier: Qualifier?,
    noinline parameters: ParametersDefinition?,
): T = remember(qualifier, parameters) {
    GlobalContext.get().scopeRegistry.rootScope.get(qualifier, parameters)
}
