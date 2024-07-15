/*
 *abiola 2024
 */

package com.mshdabiola.designsystem

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.component.SkTopAppBar
import com.mshdabiola.designsystem.icon.SkIcons
import com.mshdabiola.designsystem.theme.SkTheme


class TopAppBarScreenshotTests() {


    @ThemePreviews
    @Composable
    fun TopAppBar() {
        NiaTopAppBarExample()

    }


    @ThemePreviews
    @Composable
    fun TopAppBarHumFontScale2() {

        SkTheme {
            NiaTopAppBarExample()
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun NiaTopAppBarExample() {
        SkTopAppBar(
            titleRes = "untitled",
            navigationIcon = SkIcons.Search,
            navigationIconContentDescription = "Navigation icon",
            actionIcon = SkIcons.MoreVert,
            actionIconContentDescription = "Action icon",
        )
    }
}
