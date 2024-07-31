package com.mshdabiola.setting

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import org.junit.Rule
import kotlin.test.Test

class SettingScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun main() {
        composeRule.setContent {
            SettingScreen(
                settingState = SettingState.Success(
                    themeBrand = ThemeBrand.DEFAULT,
                    darkThemeConfig = DarkThemeConfig.DARK,
                ),
            )
        }
        composeRule.onNodeWithTag("setting:screen").assertExists()
        composeRule.onNodeWithTag("setting:theme").assertExists()
        composeRule.onNodeWithTag("setting:mode").assertExists()
    }
}
