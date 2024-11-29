package com.mshdabiola.ui

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.semanticsCommon(
    mergeDescendants: Boolean,
    properties: SemanticsPropertyReceiver.() -> Unit,
): Modifier {
    return this.semantics {
        this.testTagsAsResourceId = true
    }
}
