package com.mshdabiola.skeletonapp.screen.main

import androidx.compose.runtime.Composable

@Composable
fun MainScreenNav(navigateToDetail: () -> Unit, navigateToSetting: () -> Unit) {
    MainScreen(
        navigateToDetail,
        navigateToSetting,
    )
}
