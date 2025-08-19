/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.UserSettings
import com.mshdabiola.model.testtag.UpdateScreenTestTags
import com.mshdabiola.setting.detailscreen.UpdateScreen
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class UpdateScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun updateScreen_elementsDisplayed() {
        composeRule.setContent {
            KmtTheme {
                UpdateScreen(userSettings = UserSettings())
            }
        }

        composeRule.onNodeWithTag(UpdateScreenTestTags.ROOT_COLUMN).assertIsDisplayed()
        composeRule.onNodeWithTag(UpdateScreenTestTags.SHOW_UPDATE_DIALOG_SWITCH).assertIsDisplayed()
        composeRule.onNodeWithTag(UpdateScreenTestTags.JOIN_BETA_RELEASE_SWITCH).assertIsDisplayed()
        composeRule.onNodeWithTag(UpdateScreenTestTags.CHECK_FOR_UPDATE_BUTTON).assertIsDisplayed()
    }

    @Test
    fun updateScreen_initialSwitchStates() {
        val initialSettings = UserSettings(showUpdateDialog = true, updateFromPreRelease = false)
        composeRule.setContent {
            KmtTheme {
                UpdateScreen(userSettings = initialSettings)
            }
        }

        composeRule.onNodeWithTag(UpdateScreenTestTags.SHOW_UPDATE_DIALOG_SWITCH).assertIsOn()
        composeRule.onNodeWithTag(UpdateScreenTestTags.JOIN_BETA_RELEASE_SWITCH).assertIsOff()
    }

    @Test
    fun updateScreen_showUpdateDialogSwitch_togglesStateAndCallsCallback() {
        var showUpdateDialogCalled = false
        var calledWithValue: Boolean? = null

        composeRule.setContent {
            var initialSettings by remember { mutableStateOf(UserSettings(showUpdateDialog = false))}
            KmtTheme {
                UpdateScreen(
                    userSettings = initialSettings,
                    onSetUpdateDialog = {
                        showUpdateDialogCalled = true
                        calledWithValue = it
                        initialSettings = initialSettings.copy(showUpdateDialog = it)
                    },
                )
            }
        }

        composeRule.onNodeWithTag(UpdateScreenTestTags.SHOW_UPDATE_DIALOG_SWITCH)
            .assertIsOff()
            .performClick()
            .assertIsOn()

        assertTrue(showUpdateDialogCalled)
        assertEquals(true, calledWithValue)
    }

    @Test
    fun updateScreen_joinBetaReleaseSwitch_togglesStateAndCallsCallback() {
        var updateFromPreReleaseCalled = false
        var calledWithValue: Boolean? = null

        composeRule.setContent {
            var initialSettings by remember { mutableStateOf(UserSettings(showUpdateDialog = false))}

            KmtTheme {
                UpdateScreen(
                    userSettings = initialSettings,
                    onSetUpdateFromPreRelease = {
                        updateFromPreReleaseCalled = true
                        calledWithValue = it
                        initialSettings = initialSettings.copy(updateFromPreRelease = it)
                    },
                )
            }
        }

        composeRule.onNodeWithTag(UpdateScreenTestTags.JOIN_BETA_RELEASE_SWITCH)
            .assertIsOn()
            .performClick()
            .assertIsOff()

        assertTrue(updateFromPreReleaseCalled)
        assertEquals(false, calledWithValue)
    }

    @Test
    fun updateScreen_checkForUpdateButton_callsCallback() {
        var checkForUpdateCalled = false
        composeRule.setContent {
            KmtTheme {
                UpdateScreen(
                    userSettings = UserSettings(),
                    onCheckForUpdate = { checkForUpdateCalled = true },
                )
            }
        }

        composeRule.onNodeWithTag(UpdateScreenTestTags.CHECK_FOR_UPDATE_BUTTON).performClick()

        assertTrue(checkForUpdateCalled)
    }
}
