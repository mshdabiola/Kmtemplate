/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.HyaBackground
import com.mshdabiola.designsystem.component.HyaGradientBackground
import com.mshdabiola.testing.util.Capture

class BackgroundScreenshotTests {

    @Preview
    @Composable
    fun Background() {
        Capture {
            HyaGradientBackground(Modifier.size(100.dp)) {
                Text("background")
            }
        }
    }

    @Preview
    @Composable
    fun GradientBackground() {
        Capture {
            HyaBackground(Modifier.size(100.dp)) {
                Text("Gradient background")
            }
        }
    }
}
