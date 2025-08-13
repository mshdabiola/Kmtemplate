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
    railExpanded: Boolean,
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
