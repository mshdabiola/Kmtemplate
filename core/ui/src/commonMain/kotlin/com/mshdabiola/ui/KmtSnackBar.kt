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

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.mshdabiola.designsystem.theme.KmtExtendedTheme
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.Type
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun KmtSnackerBar(
    type: Type,
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
) {
    data class SnackbarColors(
        val containerColor: Color,
        val contentColor: Color,
        val actionColor: Color,
        val actionContentColor: Color,
    )
    val extendedColorScheme = KmtExtendedTheme.colors

    val colors = when (type) {
        Type.Default -> SnackbarColors(
            containerColor = SnackbarDefaults.color,
            contentColor = SnackbarDefaults.contentColor,
            actionColor = SnackbarDefaults.actionColor,
            actionContentColor = SnackbarDefaults.actionContentColor,
        )
        Type.Error -> SnackbarColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer,
            actionColor = MaterialTheme.colorScheme.error,
            actionContentColor = MaterialTheme.colorScheme.onError,
        )
        Type.Success -> SnackbarColors(
            containerColor = extendedColorScheme.success.colorContainer,
            contentColor = extendedColorScheme.success.onColorContainer,
            actionColor = extendedColorScheme.success.color,
            actionContentColor = extendedColorScheme.success.onColor,
        )
        Type.Warning -> SnackbarColors(
            containerColor = extendedColorScheme.warning.colorContainer,
            contentColor = extendedColorScheme.warning.onColorContainer,
            actionColor = extendedColorScheme.warning.color,
            actionContentColor = extendedColorScheme.warning.onColor,
        )
    }

    Snackbar(
        modifier = modifier.testTag("KmtSnackBar"),
        snackbarData = snackbarData,
        containerColor = colors.containerColor,
        contentColor = colors.contentColor,
        actionColor = colors.actionColor,
        actionContentColor = colors.actionContentColor,
        dismissActionContentColor = colors.actionContentColor,
    )
}

@Preview
@Composable
fun KmtSnackerBarPreview() {
    val visuals = object : SnackbarVisuals {
        override val message: String
            get() = "Snackbar message"
        override val actionLabel: String?
            get() = "Testing"
        override val withDismissAction: Boolean
            get() = false
        override val duration: SnackbarDuration
            get() = SnackbarDuration.Short
    }
    KmtTheme {
        KmtSnackerBar(
            type = Type.Default,
            snackbarData = object : SnackbarData {
                override val visuals = visuals
                override fun performAction() {}
                override fun dismiss() {}
            },
        )
    }
}
