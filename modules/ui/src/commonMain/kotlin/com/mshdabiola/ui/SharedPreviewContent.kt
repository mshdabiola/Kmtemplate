package com.mshdabiola.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
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
        AnimatedContentScope,
    ) -> Unit,
) {
    SharedTransitionLayout {
        AnimatedContent(1) {
            if (it == 1) {
                content(this@SharedTransitionLayout, this)
            }
        }
    }
}
