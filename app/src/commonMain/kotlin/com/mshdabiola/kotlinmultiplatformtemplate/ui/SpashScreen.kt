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
package com.mshdabiola.kotlinmultiplatformtemplate.ui

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
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.drawable.KmtDrawable
import com.mshdabiola.designsystem.strings.KmtStrings
import com.mshdabiola.designsystem.theme.onPrimaryLight
import com.mshdabiola.designsystem.theme.primaryLight
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = primaryLight,
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.sizeIn(maxWidth = 200.dp, maxHeight = 200.dp),
                imageVector = KmtDrawable.brand,
                contentDescription = "app icon",
            )

            Spacer(Modifier.height(32.dp))
            Text(
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
