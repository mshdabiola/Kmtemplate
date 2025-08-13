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
package com.mshdabiola.kmtemplate.ui

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
actual fun CustomWideNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier,
    railExpanded: Boolean, // Control whether to show the wider version
) {
    if (railExpanded) {
        // Custom layout for expanded state (e.g., icon and label side-by-side)
        // This is a simplified version. You'd use NavigationRailItem's styling or build your own.
        NavigationDrawerItem(
            modifier = modifier.widthIn(max = 150.dp),
            icon = icon,
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
