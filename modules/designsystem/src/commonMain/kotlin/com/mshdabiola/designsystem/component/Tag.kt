/*
 *abiola 2022
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
import com.mshdabiola.designsystem.theme.HyaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HyaTopicTag(
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
                    alpha = HyaTagDefaults.UNFOLLOWED_TOPIC_TAG_CONTAINER_ALPHA,
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
                            alpha = HyaTagDefaults.DISABLED_TOPIC_TAG_CONTAINER_ALPHA,
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
    HyaTheme {
        HyaTopicTag(followed = true, onClick = {}) {
            Text("Topic".uppercase())
        }
    }
}

/**
 * Now in Android tag default values.
 */
object HyaTagDefaults {
    const val UNFOLLOWED_TOPIC_TAG_CONTAINER_ALPHA = 0.5f

    // TODO: File bug
    // Button disabled container alpha value not exposed by ButtonDefaults
    const val DISABLED_TOPIC_TAG_CONTAINER_ALPHA = 0.12f
}
