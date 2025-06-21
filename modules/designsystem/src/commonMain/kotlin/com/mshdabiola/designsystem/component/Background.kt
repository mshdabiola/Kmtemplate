/*
 * Copyright (C) 2024-2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package com.mshdabiola.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.theme.GradientColors
import com.mshdabiola.designsystem.theme.HyaTheme
import com.mshdabiola.designsystem.theme.LocalBackgroundTheme
import com.mshdabiola.designsystem.theme.LocalGradientColors
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.tan

/**
 * The main background for the app.
 * Uses [LocalBackgroundTheme] to set the color and tonal elevation of a [Surface].
 *
 * @param modifier Modifier to be applied to the background.
 * @param content The background content.
 */
@Composable
fun HyaBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val color = LocalBackgroundTheme.current.color
    val tonalElevation = LocalBackgroundTheme.current.tonalElevation
    Surface(
        color = if (color == Color.Unspecified) Color.Transparent else color,
        tonalElevation = if (tonalElevation == Dp.Unspecified) 0.dp else tonalElevation,
        modifier = modifier.fillMaxSize(),
    ) {
        CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
            content()
        }
    }
}

/**
 * A gradient background for select screens. Uses [LocalBackgroundTheme] to set the gradient colors
 * of a [Box] within a [Surface].
 *
 * @param modifier Modifier to be applied to the background.
 * @param gradientColors The gradient colors to be rendered.
 * @param content The background content.
 */
@Composable
fun HyaGradientBackground(
    modifier: Modifier = Modifier,
    gradientColors: GradientColors = LocalGradientColors.current,
    content: @Composable () -> Unit,
) {
    val currentTopColor by rememberUpdatedState(gradientColors.top)
    val currentBottomColor by rememberUpdatedState(gradientColors.bottom)
    Surface(
        color =
        if (gradientColors.container == Color.Unspecified) {
            Color.Transparent
        } else {
            gradientColors.container
        },
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .drawWithCache {
                    // Compute the start and end coordinates such that the gradients are angled 11.06
                    // degrees off the vertical axis
                    val offset =
                        size.height *
                            tan(
                                radiansToDegrees(11.06)
                                    .toFloat(),
                            )

                    val start = Offset(size.width / 2 + offset / 2, 0f)
                    val end = Offset(size.width / 2 - offset / 2, size.height)

                    // Create the top gradient that fades out after the halfway point vertically
                    val topGradient =
                        Brush.linearGradient(
                            0f to
                                if (currentTopColor == Color.Unspecified) {
                                    Color.Transparent
                                } else {
                                    currentTopColor
                                },
                            0.724f to Color.Transparent,
                            start = start,
                            end = end,
                        )
                    // Create the bottom gradient that fades in before the halfway point vertically
                    val bottomGradient =
                        Brush.linearGradient(
                            0.2552f to Color.Transparent,
                            1f to
                                if (currentBottomColor == Color.Unspecified) {
                                    Color.Transparent
                                } else {
                                    currentBottomColor
                                },
                            start = start,
                            end = end,
                        )

                    onDrawBehind {
                        // There is overlap here, so order is important
                        drawRect(topGradient)
                        drawRect(bottomGradient)
                    }
                },
        ) {
            content()
        }
    }
}

expect fun radiansToDegrees(radians: Double): Double

@Preview
@Composable
fun BackgroundDefault() {
    HyaTheme(disableDynamicTheming = true) {
        HyaBackground(Modifier.size(100.dp), content = {})
    }
}

@Preview
@Composable
fun BackgroundDynamic() {
    HyaTheme(disableDynamicTheming = false) {
        HyaBackground(Modifier.size(100.dp), content = {})
    }
}

@Preview
@Composable
fun BackgroundAndroid() {
    HyaTheme(androidTheme = true) {
        HyaBackground(Modifier.size(100.dp), content = {})
    }
}

@Preview
@Composable
fun GradientBackgroundDefault() {
    HyaTheme(disableDynamicTheming = true) {
        HyaGradientBackground(Modifier.size(100.dp), content = {})
    }
}

@Preview
@Composable
fun GradientBackgroundDynamic() {
    HyaTheme(disableDynamicTheming = false) {
        HyaGradientBackground(Modifier.size(100.dp), content = {})
    }
}

@Preview
@Composable
fun GradientBackgroundAndroid() {
    HyaTheme(androidTheme = true) {
        HyaGradientBackground(Modifier.size(100.dp), content = {})
    }
}
