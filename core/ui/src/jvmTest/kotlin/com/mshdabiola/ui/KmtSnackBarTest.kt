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
package com.mshdabiola.ui

import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.Type
import org.junit.Rule
import org.junit.Test

class KmtSnackBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummySnackbarVisuals = object : SnackbarVisuals {
        override val message: String = "Test message"
        override val actionLabel: String? = "Action"
        override val withDismissAction: Boolean = false
        override val duration: SnackbarDuration = SnackbarDuration.Short
    }

    private val dummySnackbarData = object : SnackbarData {
        override val visuals: SnackbarVisuals = dummySnackbarVisuals
        override fun performAction() {}
        override fun dismiss() {}
    }

    @Test
    fun kmtSnackBar_defaultType_isDisplayed() {
        composeTestRule.setContent {
            KmtTheme {
                KmtSnackerBar(
                    type = Type.Default,
                    snackbarData = dummySnackbarData,
                )
            }
        }

        composeTestRule.onNodeWithTag("KmtSnackBar").assertIsDisplayed()
    }

    @Test
    fun kmtSnackBar_errorType_isDisplayed() {
        composeTestRule.setContent {
            KmtTheme {
                KmtSnackerBar(
                    type = Type.Error,
                    snackbarData = dummySnackbarData,
                )
            }
        }

        composeTestRule.onNodeWithTag("KmtSnackBar").assertIsDisplayed()
    }

    @Test
    fun kmtSnackBar_successType_isDisplayed() {
        composeTestRule.setContent {
            KmtTheme {
                KmtSnackerBar(
                    type = Type.Success,
                    snackbarData = dummySnackbarData,
                )
            }
        }

        composeTestRule.onNodeWithTag("KmtSnackBar").assertIsDisplayed()
    }

    @Test
    fun kmtSnackBar_warningType_isDisplayed() {
        composeTestRule.setContent {
            KmtTheme {
                KmtSnackerBar(
                    type = Type.Warning,
                    snackbarData = dummySnackbarData,
                )
            }
        }

        composeTestRule.onNodeWithTag("KmtSnackBar").assertIsDisplayed()
    }
}
