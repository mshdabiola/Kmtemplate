/*
 *abiola 2023
 */

package com.mshdabiola.detail

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.DevicePreviews
import com.mshdabiola.designsystem.theme.darkDefaultScheme
import com.mshdabiola.designsystem.theme.lightDefaultScheme
import com.mshdabiola.ui.SharedContentPreview

class ScreenScreenshotTests {

    @OptIn(ExperimentalSharedTransitionApi::class)
    @DevicePreviews
    @Composable
    fun LoadingLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {
                SharedContentPreview { sharedTransitionScope, animatedContentScope ->
                    DetailScreen(
                        state = DetailState.Loading(),
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope,
                        title = rememberTextFieldState("title"),
                        content = rememberTextFieldState("content"),
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @DevicePreviews
    @Composable
    fun LoadingDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {
                SharedContentPreview { sharedTransitionScope, animatedContentScope ->
                    DetailScreen(
                        state = DetailState.Loading(),
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope,
                        title = rememberTextFieldState("title"),
                        content = rememberTextFieldState("content"),
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @DevicePreviews
    @Composable
    fun MainLight() {
        MaterialTheme(colorScheme = lightDefaultScheme) {
            Surface {
                SharedContentPreview { sharedTransitionScope, animatedContentScope ->
                    DetailScreen(
                        state = DetailState.Success(1),
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope,
                        title = rememberTextFieldState("title"),
                        content = rememberTextFieldState("content"),
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @DevicePreviews
    @Composable
    fun MainDark() {
        MaterialTheme(colorScheme = darkDefaultScheme) {
            Surface {
                SharedContentPreview { sharedTransitionScope, animatedContentScope ->
                    DetailScreen(
                        state = DetailState.Success(1),
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope,
                        title = rememberTextFieldState("title"),
                        content = rememberTextFieldState("content"),
                    )
                }
            }
        }
    }
}
