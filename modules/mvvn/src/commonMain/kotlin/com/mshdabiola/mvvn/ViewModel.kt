package com.mshdabiola.mvvn

import kotlinx.coroutines.CoroutineScope

expect open class ViewModel() {
    val viewModelScope: CoroutineScope

    protected open fun onCleared()
} // : ViewModel()
