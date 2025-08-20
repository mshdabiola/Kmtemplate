package com.mshdabiola.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mshdabiola.designsystem.theme.KmtExtendedTheme
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.Type
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun KmtSnackerBar(type: Type, snackbarData: SnackbarData) {
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
