package com.mshdabiola.mvvn

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@Composable
actual inline fun <reified T : ViewModel> KoinCommonViewModel(
    qualifier: Qualifier?,
    noinline parameters: ParametersDefinition?,
): T {
    return getCommonViewModel(qualifier, parameters = parameters)
}

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.semanticsCommon(
    mergeDescendants: Boolean,
    properties: SemanticsPropertyReceiver.() -> Unit,
): Modifier {
    return this.semantics {
        this.testTagsAsResourceId = true
    }
}
