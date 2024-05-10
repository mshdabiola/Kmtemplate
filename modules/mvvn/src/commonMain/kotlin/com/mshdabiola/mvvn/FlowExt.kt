package com.mshdabiola.mvvn

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
expect fun <T> StateFlow<T>.collectAsStateWithLifecycleCommon(): State<T>

@Composable
expect fun <T> Flow<T>.collectAsStateWithLifecycleCommon(initialValue: T): State<T>
