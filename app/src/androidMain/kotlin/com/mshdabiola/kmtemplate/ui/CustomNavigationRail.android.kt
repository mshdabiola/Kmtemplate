package com.mshdabiola.kmtemplate.ui

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.WideNavigationRailItem
import androidx.compose.material3.WideNavigationRailValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.mshdabiola.model.testtag.KmtScaffoldTestTags

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
        railExpanded = railExpanded
    )
}
