/*
 *abiola 2023
 */

package com.mshdabiola.designsystem

import androidx.activity.ComponentActivity
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.mshdabiola.designsystem.component.SkIconToggleButton
import com.mshdabiola.designsystem.icon.SkIcons
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
class IconButtonScreenshotTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun iconButton_multipleThemes() {
        composeTestRule.captureMultiTheme("IconButton") {
            NiaIconToggleExample(false)
        }
    }

    @Test
    fun iconButton_unchecked_multipleThemes() {
        composeTestRule.captureMultiTheme("IconButton", "IconButtonUnchecked") {
            Surface {
                NiaIconToggleExample(true)
            }
        }
    }

    @Composable
    private fun NiaIconToggleExample(checked: Boolean) {
        SkIconToggleButton(
            checked = checked,
            onCheckedChange = { },
            icon = {
                Icon(
                    imageVector = SkIcons.BookmarkBorder,
                    contentDescription = null,
                )
            },
            checkedIcon = {
                Icon(
                    imageVector = SkIcons.Bookmark,
                    contentDescription = null,
                )
            },
        )
    }
}
