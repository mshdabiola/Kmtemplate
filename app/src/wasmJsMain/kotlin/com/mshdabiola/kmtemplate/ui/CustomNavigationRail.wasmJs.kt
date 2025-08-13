package com.mshdabiola.kmtemplate.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.testtag.KmtScaffoldTestTags
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
actual fun CustomWideNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier ,
    railExpanded: Boolean, // Control whether to show the wider version
) {
    if (railExpanded) {
        // Custom layout for expanded state (e.g., icon and label side-by-side)
        // This is a simplified version. You'd use NavigationRailItem's styling or build your own.
        NavigationDrawerItem(
            modifier = modifier.widthIn(max = 150.dp),
            icon =icon,
            label = label,
            selected = selected,
            onClick = onClick,
        )
    } else {
        // Standard NavigationRailItem behavior when not expanded
        NavigationRailItem(
            selected = selected,
            onClick = onClick,
            icon = icon,
            label = label, // Label might be hidden by default in compact NavigationRail
            modifier = modifier,
            alwaysShowLabel = false, // Or true, depending on desired compact look
        )
    }
}
