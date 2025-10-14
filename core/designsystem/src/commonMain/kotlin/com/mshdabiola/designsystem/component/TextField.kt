/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.designsystem.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction

@Composable
fun KmtTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    placeholder: String? = null,
    supportingText: String? = null,
    label: String? = null,
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
    keyboardAction: KeyboardActionHandler? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    interactionSource: MutableInteractionSource? = null,
    maxNum: TextFieldLineLimits = TextFieldLineLimits.Default,
    color: Color = Color.Unspecified,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        modifier = modifier,
        state = state,
        placeholder = if (placeholder != null) {
            { Text(text = placeholder) }
        } else {
            null
        },
        supportingText = if (supportingText != null) {
            { Text(text = supportingText) }
        } else {
            null
        },
        label = if (label != null) {
            { Text(text = label) }
        } else {
            null
        },
        isError = isError,
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        onKeyboardAction = keyboardAction,
        lineLimits = maxNum,
        colors = TextFieldDefaults.colors(
            focusedTextColor = color,
            unfocusedTextColor = color,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
        ),
        textStyle = textStyle,
        interactionSource = interactionSource,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
    )
}
