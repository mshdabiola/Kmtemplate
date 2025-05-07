/*
 *abiola 2022
 */

package com.mshdabiola.designsystem.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.component.HyaTopicTag
import com.mshdabiola.designsystem.theme.HyaTheme

@ThemePreviews
@Composable
fun TagPreview() {
    HyaTheme {
        HyaTopicTag(followed = true, onClick = {}) {
            Text("Topic".uppercase())
        }
    }
}
