/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import com.mshdabiola.designsystem.theme.KmtTheme

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope: ProvidableCompositionLocal<SharedTransitionScope> =
    compositionLocalOf {
        throw IllegalStateException(
            "Not declare",
        )
    }

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalNavAnimatedContentScope: ProvidableCompositionLocal<AnimatedContentScope> =
    compositionLocalOf {
        throw IllegalStateException(
            "Not declare",
        )
    }

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionContainer(
    isDarkMode: Boolean = false,
    content: @Composable () -> Unit,
) {
    KmtTheme(darkTheme = isDarkMode) {
        SharedTransitionScope {
            AnimatedContent(true) {
                CompositionLocalProvider(
                    LocalNavAnimatedContentScope provides this,
                    LocalSharedTransitionScope provides this@SharedTransitionScope,
                ) {
                    if (it) {
                        content()
                    }
                }
            }
        }
    }
}
