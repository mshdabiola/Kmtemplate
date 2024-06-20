/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.activity.ComponentActivity
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.DeviceConfigurationOverride
import androidx.compose.ui.test.FontScale
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import com.mshdabiola.designsystem.component.SkTab
import com.mshdabiola.designsystem.component.SkTabRow
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
class TabsScreenshotTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun tabs_multipleThemes() {
        composeTestRule.captureMultiTheme("Tabs") {
            NiaTabsExample()
        }
    }

    @Test
    fun tabs_hugeFont() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                DeviceConfigurationOverride(
                    DeviceConfigurationOverride.FontScale(2f),
                ) {
                    SkTheme {
                        NiaTabsExample("Looooong item")
                    }
                }
            }
        }
        composeTestRule.onRoot()
            .captureRoboImage(
                "src/test/screenshots/Tabs/Tabs_fontScale2.png",
                roborazziOptions = DefaultRoborazziOptions,
            )
    }

    @Composable
    private fun NiaTabsExample(label: String = "Topics") {
        Surface {
            val titles = listOf(label, "People")
            SkTabRow(selectedTabIndex = 0) {
                titles.forEachIndexed { index, title ->
                    SkTab(
                        selected = index == 0,
                        onClick = { },
                        text = { Text(text = title) },
                    )
                }
            }
        }
    }
}
