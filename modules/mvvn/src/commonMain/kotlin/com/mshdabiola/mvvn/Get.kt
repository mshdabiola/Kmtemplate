package com.mshdabiola.mvvn

import androidx.compose.runtime.Composable
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

/**
 * Resolve a dependency for [Composable] functions
 * @param qualifier
 * @param parameters
 *
 * @return Lazy instance of type T
 *
 * @author Arnaud Giuliani
 * @author Henrique Horbovyi
 */
@OptIn(KoinInternalApi::class)
@Composable
expect inline fun <reified T> get(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T
//
// @OptIn(KoinInternalApi::class)
// @Composable
// inline fun <reified T> inject(
//    qualifier: Qualifier? = null,
//    scope: Scope = GlobalContext.get().scopeRegistry.rootScope,
//    noinline parameters: ParametersDefinition? = null,
// ): Lazy<T> = remember(qualifier, parameters) {
//    scope.inject(qualifier, LazyThreadSafetyMode.NONE, parameters)
// }
//
// @Composable
// fun getKoin(): Koin = remember {
//    GlobalContext.get()
// }
