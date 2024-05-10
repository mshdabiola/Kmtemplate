package com.mshdabiola.mvvn

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

actual open class ViewModel actual constructor() {
    actual val viewModelScope: CoroutineScope
        get() = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    protected actual open fun onCleared() {
        viewModelScope.coroutineContext.cancel()
        viewModelScope.cancel()
    }
}
