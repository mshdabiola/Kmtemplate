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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun KmtButton(
    onClick: () -> Unit,
    label: String,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues? = null,
) {
    val size = ButtonDefaults.MinHeight
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = ButtonDefaults.shape,
        colors = ButtonDefaults.buttonColors(),
        modifier = modifier.heightIn(size),
        contentPadding = contentPadding ?: ButtonDefaults.ContentPadding,
    ) {
        if (icon != null) {
            Icon(
                icon,
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }

        Text(label)
    }
}

@Composable
fun KmtSecondaryButton(
    onClick: () -> Unit,
    label: String,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues? = null,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = ButtonDefaults.shape,
        colors = ButtonDefaults.textButtonColors(),
        contentPadding = contentPadding ?: ButtonDefaults.ContentPadding,
    ) {
        if (icon != null) {
            Icon(
                icon,
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }

        Text(label)
    }
}

@Composable
fun KmtIconButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        shape = IconButtonDefaults.standardShape,
        colors = IconButtonDefaults.iconButtonColors(),
        enabled = enabled,
    ) {
        Icon(imageVector, contentDescription)
    }
}
