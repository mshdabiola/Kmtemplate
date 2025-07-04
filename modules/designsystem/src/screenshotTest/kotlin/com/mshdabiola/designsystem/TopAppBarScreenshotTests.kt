/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.designsystem

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.HyaTopAppBar
import com.mshdabiola.designsystem.icon.HyaIcons
import com.mshdabiola.testing.util.CaptureMultiTheme

class TopAppBarScreenshotTests() {

    @Preview
    @Composable
    fun TopAppBar() {
        CaptureMultiTheme {
            HyaTopAppBarExample()
        }
    }

    @Preview(fontScale = 2.0f)
    @Composable
    fun TopAppBarHumFontScale2() {
        CaptureMultiTheme {
            HyaTopAppBarExample()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun HyaTopAppBarExample() {
        HyaTopAppBar(
            titleRes = "untitled",
            navigationIcon = HyaIcons.Search,
            navigationIconContentDescription = "Navigation icon",
            actionIcon = HyaIcons.MoreVert,
            actionIconContentDescription = "Action icon",
        )
    }
}
