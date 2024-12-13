package com.mshdabiola.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics

actual fun Modifier.semanticsCommon(
    mergeDescendants: Boolean,
    properties: SemanticsPropertyReceiver.() -> Unit,
): Modifier {
    return this.semantics(mergeDescendants, properties)
}
