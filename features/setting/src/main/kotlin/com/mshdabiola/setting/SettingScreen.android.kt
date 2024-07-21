package com.mshdabiola.setting

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun ScreenPreview() {
    SettingScreen(
        modifier = Modifier,
        settingState = SettingState(
            UserData(
                useDynamicColor = true,
                themeBrand = ThemeBrand.DEFAULT,
                darkThemeConfig = DarkThemeConfig.LIGHT,
                shouldHideOnboarding = false,
            ),
        ),

    )
}
