package com.mshdabiola.skeletonapp.screen.detail

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview
@Composable
actual fun DetailScreenPreview() {
    val size = WindowSizeClass.calculateFromSize(Size(451f, 456f), LocalDensity.current)

    DetailScreen(size)
}
