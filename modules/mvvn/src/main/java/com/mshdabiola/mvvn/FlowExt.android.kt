package com.mshdabiola.mvvn

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
actual fun <T> StateFlow<T>.collectAsStateWithLifecycleCommon(): State<T> {
    return this.collectAsStateWithLifecycle()
}

@Composable
actual fun <T> Flow<T>.collectAsStateWithLifecycleCommon(initialValue: T): State<T> {
    return this.collectAsStateWithLifecycle(initialValue = initialValue)
}
