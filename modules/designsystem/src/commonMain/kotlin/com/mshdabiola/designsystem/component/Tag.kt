/*
 * Copyright (C) 2022-2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package com.mshdabiola.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.theme.KmtTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun KmtTopicTag(
    modifier: Modifier = Modifier,
    followed: Boolean,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
) {
    Box(modifier = modifier) {
        val containerColor =
            if (followed) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(
                    alpha = KmtTagDefaults.UNFOLLOWED_TOPIC_TAG_CONTAINER_ALPHA,
                )
            }
        TextButton(
            onClick = onClick,
            enabled = enabled,
            colors =
            ButtonDefaults.textButtonColors(
                containerColor = containerColor,
                contentColor = contentColorFor(backgroundColor = containerColor),
                disabledContainerColor =
                MaterialTheme.colorScheme.onSurface.copy(
                    alpha = KmtTagDefaults.DISABLED_TOPIC_TAG_CONTAINER_ALPHA,
                ),
            ),
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                text()
            }
        }
    }
}

@Preview
@Composable
fun TagPreview() {
    KmtTheme {
        KmtTopicTag(followed = true, onClick = {}) {
            Text("Topic".uppercase())
        }
    }
}

/**
 * Now in Android tag default values.
 */
object KmtTagDefaults {
    const val UNFOLLOWED_TOPIC_TAG_CONTAINER_ALPHA = 0.5f

    // TOD: File bug
    // Button disabled container alpha value not exposed by ButtonDefaults
    const val DISABLED_TOPIC_TAG_CONTAINER_ALPHA = 0.12f
}
