package com.mshdabiola.designsystem

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.SkTextField

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun SkTextFieldPreview() {
    SkTextField(state = TextFieldState("Sk Testing"))
}
