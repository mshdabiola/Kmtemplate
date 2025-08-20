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
    fun kmtSnackBar_isDisplayedForAllTypes() {
        listOf(
            Type.Default,
            Type.Error,
            Type.Success,
            Type.Warning,
        ).forEach { type ->
            composeTestRule.setContent {
                KmtTheme {
                    KmtSnackerBar(
                        type = type,
                        snackbarData = dummySnackbarData,
                    )
                }
            }

            composeTestRule.onNodeWithTag("KmtSnackBar")
                .assertIsDisplayed()
        }
    }
}
