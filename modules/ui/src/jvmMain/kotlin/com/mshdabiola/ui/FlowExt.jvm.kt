package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.swing.Swing
import java.lang.AutoCloseable
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
actual fun <T> StateFlow<T>.collectAsStateWithLifecycleCommon(): State<T> {
    return this.collectAsState()
}

@Composable
actual fun <T> Flow<T>.collectAsStateWithLifecycleCommon(initialValue: T): State<T> {
    return this.collectAsState(initialValue)
}

internal fun createViewModelScope(): CloseableCoroutineScope {
    val dispatcher = try {
        // In platforms where `Dispatchers.Main` is not available, Kotlin Multiplatform will throw
        // an exception (the specific exception type may depend on the platform). Since there's no
        // direct functional alternative, we use `EmptyCoroutineContext` to ensure that a coroutine
        // launched within this scope will run in the same context as the caller.
        Dispatchers.Main.immediate
    } catch (_: ClassNotFoundException) {
        // In Native environments where `Dispatchers.Main` might not exist (e.g., Linux):
        EmptyCoroutineContext
    } catch (_: IllegalStateException) {
        // In JVM Desktop environments where `Dispatchers.Main` might not exist (e.g., Swing):
        EmptyCoroutineContext
    }
    val dis=Dispatchers.Swing
    return CloseableCoroutineScope(coroutineContext = dis + SupervisorJob())
}

internal class CloseableCoroutineScope(
    override val coroutineContext: CoroutineContext,
) : AutoCloseable, CoroutineScope {

    constructor(coroutineScope: CoroutineScope) : this(coroutineScope.coroutineContext)

    override fun close() = coroutineContext.cancel()
}

actual val ViewModel.viewModelScope: CoroutineScope
    get() = createViewModelScope()



actual fun Modifier.semanticsCommon(
    mergeDescendants: Boolean,
    properties: SemanticsPropertyReceiver.() -> Unit,
): Modifier {
    return this.semantics(mergeDescendants, properties)
}