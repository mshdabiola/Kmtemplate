/*
 *abiola 2022
 */

package com.mshdabiola.setting

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for [SettingScreen] composable.
 */
class MainScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun enterText_showsShowText() {
        composeTestRule.setContent {
            SettingScreen(
                settingState = SettingState(
                    UserData(
                        themeBrand = ThemeBrand.DEFAULT,
                        shouldHideOnboarding = false,
                        darkThemeConfig = DarkThemeConfig.DARK,
                        useDynamicColor = false,

                    ),
                ),
            )
        }

//        composeTestRule
//            .onNodeWithTag("main:list")
//            .assertExists()
    }
}
