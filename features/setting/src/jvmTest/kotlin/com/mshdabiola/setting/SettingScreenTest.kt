package com.mshdabiola.setting

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class SettingScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun main() {
        composeRule.setContent {
            //  SettingScreen(settingState = SettingState(UserData()))
        }
    }
}
