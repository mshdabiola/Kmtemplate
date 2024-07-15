/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.SkBackground
import com.mshdabiola.designsystem.component.SkGradientBackground

class BackgroundScreenshotTests {

    @ThemePreviews
    @Composable
    fun Background() {
        SkBackground(Modifier.size(100.dp)) {
            Text("background")
        }

    }

    @ThemePreviews
    @Composable
    fun GradientBackground() {

        SkGradientBackground(Modifier.size(100.dp)) {
            Text("Gradient background")
        }

    }
}
