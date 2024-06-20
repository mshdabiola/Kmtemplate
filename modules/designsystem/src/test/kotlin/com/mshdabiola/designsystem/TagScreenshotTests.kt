/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.DeviceConfigurationOverride
import androidx.compose.ui.test.FontScale
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import com.mshdabiola.designsystem.component.SkTopicTag
import com.mshdabiola.designsystem.theme.SkTheme
import com.mshdabiola.testing.util.DefaultRoborazziOptions
import com.mshdabiola.testing.util.captureMultiTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = "480dpi")
@LooperMode(LooperMode.Mode.PAUSED)
class TagScreenshotTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun Tag_multipleThemes() {
        composeTestRule.captureMultiTheme("Tag") {
            SkTopicTag(followed = true, onClick = {}) {
                Text("TOPIC")
            }
        }
    }

    @Test
    fun tag_hugeFont() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                DeviceConfigurationOverride(
                    DeviceConfigurationOverride.Companion.FontScale(2f),
                ) {
                    SkTheme {
                        SkTopicTag(followed = true, onClick = {}) {
                            Text("LOOOOONG TOPIC")
                        }
                    }
                }
            }
        }
        composeTestRule.onRoot()
            .captureRoboImage(
                "src/test/screenshots/Tag/Tag_fontScale2.png",
                roborazziOptions = DefaultRoborazziOptions,
            )
    }
}
