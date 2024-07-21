/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.component.SkTopicTag
import com.mshdabiola.designsystem.theme.SkTheme

class TagScreenshotTests {

    @ThemePreviews
    @Composable
    fun Tag() {
        SkTopicTag(followed = true, onClick = {}) {
            Text("TOPIC")
        }
    }

    @ThemePreviews
    @Composable
    fun TagHumFontScale2() {
        SkTheme {
            SkTopicTag(followed = true, onClick = {}) {
                Text("LOOOOONG TOPIC")
            }
        }
    }
}
