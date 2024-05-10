package com.mshdabiola.skeletonapp.screen.setting

import androidx.compose.runtime.Composable

@Composable
fun SettingScreenNav(onBack: () -> Unit) {
    SettingScreen {
        onBack()
    }
}
