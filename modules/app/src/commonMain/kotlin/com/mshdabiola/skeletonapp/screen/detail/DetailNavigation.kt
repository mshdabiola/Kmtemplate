package com.mshdabiola.skeletonapp.screen.detail

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable

@Composable
fun MainScreenNav(windowSizeClass: WindowSizeClass, onBack: () -> Unit) {
    DetailScreenn(
        windowSizeClass,
        onBack,
    )
}
