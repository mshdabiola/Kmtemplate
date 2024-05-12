package com.mshdabiola.mvvn

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
actual fun <T> StateFlow<T>.collectAsStateWithLifecycleCommon(): State<T> {
    return this.collectAsState()
}

@Composable
actual fun <T> Flow<T>.collectAsStateWithLifecycleCommon(initialValue: T): State<T> {
    return this.collectAsState(initialValue)
}
