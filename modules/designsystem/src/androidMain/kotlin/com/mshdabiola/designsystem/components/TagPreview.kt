/*
 *abiola 2022
 */

package com.mshdabiola.designsystem.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.component.SkTopicTag
import com.mshdabiola.designsystem.theme.SkTheme

@ThemePreviews
@Composable
fun TagPreview() {
    SkTheme {
        SkTopicTag(followed = true, onClick = {}) {
            Text("Topic".uppercase())
        }
    }
}
