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
package com.mshdabiola.kmtemplate.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag // Ensure this is imported
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.drawable.KmtDrawable
import com.mshdabiola.designsystem.strings.KmtStrings
import com.mshdabiola.designsystem.theme.onPrimaryLight
import com.mshdabiola.designsystem.theme.primaryLight
import org.jetbrains.compose.ui.tooling.preview.Preview

// Test Tags Object for SplashScreen
object SplashScreenTestTags {
    const val SCREEN_ROOT = "splash:screen_root" // Tag for the root Surface
    const val BRAND_IMAGE = "splash:brand_image" // Tag for the brand Image
    const val BRAND_TEXT = "splash:brand_text" // Tag for the brand Text
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxSize() // Apply fillMaxSize to the Surface as well for the root
            .testTag(SplashScreenTestTags.SCREEN_ROOT), // Apply root tag here
        color = primaryLight,
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize(), // This Column already fills the Surface
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .sizeIn(maxWidth = 200.dp, maxHeight = 200.dp)
                    .testTag(SplashScreenTestTags.BRAND_IMAGE), // Tag for the image
                imageVector = KmtDrawable.brand,
                contentDescription = "app icon", // Keep contentDescription for accessibility
            )

            Spacer(Modifier.height(32.dp))
            Text(
                modifier = Modifier.testTag(SplashScreenTestTags.BRAND_TEXT), // Tag for the text
                text = KmtStrings.brand,
                style = MaterialTheme.typography.headlineSmall,
                color = onPrimaryLight,
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}
