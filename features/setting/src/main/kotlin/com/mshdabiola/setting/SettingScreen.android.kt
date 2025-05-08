package com.mshdabiola.setting

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun ScreenPreview() {
    SettingScreen(
        settingState =
            SettingState.Success(
                themeBrand = ThemeBrand.DEFAULT,
                darkThemeConfig = DarkThemeConfig.DARK,
            ),
    )
}
