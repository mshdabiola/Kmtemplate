package com.mshdabiola.mvvn

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics

@Composable
actual inline fun <reified T : ViewModel> KoinCommonViewModel(): T {
    return get()
}

actual fun Modifier.semanticsCommon(
    mergeDescendants: Boolean,
    properties: SemanticsPropertyReceiver.() -> Unit,
): Modifier {
    return this.semantics(mergeDescendants, properties)
}
