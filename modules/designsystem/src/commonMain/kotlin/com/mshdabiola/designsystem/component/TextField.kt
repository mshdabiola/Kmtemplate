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
package com.mshdabiola.designsystem.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction

@Composable
fun KmtTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    placeholder: String? = null,
    imeAction: ImeAction = ImeAction.Done,
    keyboardAction: KeyboardActionHandler? = null,
    maxNum: TextFieldLineLimits = TextFieldLineLimits.Default,
) {
    TextField(
        modifier = modifier,
        state = state,
        placeholder = { placeholder?.let { Text(text = it) } },
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        onKeyboardAction = keyboardAction,
        lineLimits = maxNum,
    )
}
