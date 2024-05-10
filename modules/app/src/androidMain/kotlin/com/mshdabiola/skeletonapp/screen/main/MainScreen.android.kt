package com.mshdabiola.skeletonapp.screen.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.toImmutableList

@Composable
@Preview
actual fun MainScreenPreview() {
    MainScreen(
        mainState = MainState(),
        items = listOf(ModelUiState(2, "")).toImmutableList(),
    )
}
