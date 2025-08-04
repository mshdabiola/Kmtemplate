package com.mshdabiola.ui

import co.touchlab.kermit.Logger
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

actual inline fun <reified L : Logger> Scope.getLoggerWithTag(tag: String): L {
    return get(parameters = { parametersOf(tag) })
}
