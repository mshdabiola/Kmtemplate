package com.mshdabiola.designsystem.component

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.WideNavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
actual fun CustomWideNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable (() -> Unit),
    label: @Composable (() -> Unit),
    modifier: Modifier,
    railExpanded: Boolean
) {
    WideNavigationRailItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        icon = icon,
        label = label,
        railExpanded = railExpanded,
    )
}
