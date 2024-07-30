package com.mshdabiola.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedContentPreview(
    modifier: Modifier = Modifier,
    content: @Composable (
        SharedTransitionScope,
        AnimatedVisibilityScope,
    ) -> Unit,
) {
    SharedTransitionLayout {
        AnimatedVisibility(true) {

                content(this@SharedTransitionLayout, this)

        }
    }
}
