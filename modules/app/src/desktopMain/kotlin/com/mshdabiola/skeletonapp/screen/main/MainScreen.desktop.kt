package com.mshdabiola.skeletonapp.screen.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.toImmutableList

@Composable
@Preview
actual fun MainScreenPreview() {
    MainScreen(
        mainState = MainState(),
        items = listOf(ModelUiState(2, "")).toImmutableList(),
    )
}
