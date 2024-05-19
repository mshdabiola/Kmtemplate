package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
expect fun <T> StateFlow<T>.collectAsStateWithLifecycleCommon(): State<T>

@Composable
expect fun <T> Flow<T>.collectAsStateWithLifecycleCommon(initialValue: T): State<T>

expect val ViewModel.viewModelScope: CoroutineScope


expect fun Modifier.semanticsCommon(
    mergeDescendants: Boolean = false,
    properties: (SemanticsPropertyReceiver.() -> Unit),
): Modifier