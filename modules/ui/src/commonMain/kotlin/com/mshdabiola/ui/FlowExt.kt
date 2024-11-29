package com.mshdabiola.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyReceiver

expect fun Modifier.semanticsCommon(
    mergeDescendants: Boolean = false,
    properties: (SemanticsPropertyReceiver.() -> Unit),
): Modifier
