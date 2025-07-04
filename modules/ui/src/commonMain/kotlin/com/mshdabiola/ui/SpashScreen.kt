/*
 * Copyright (C) 2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package com.mshdabiola.ui

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
import com.mshdabiola.designsystem.drawable.defaultAppIcon
import com.mshdabiola.designsystem.theme.MyColors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    appName: String = "KotlinMultiplatformTemplate",
) {
    Surface(
        modifier = modifier,
        color = MyColors.Default.primaryLight,
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
                painter = defaultAppIcon,
                contentDescription = "app icon",
            )

            Spacer(Modifier.height(32.dp))
            Text(
                text = appName,
                style = MaterialTheme.typography.headlineSmall,
                color = MyColors.Default.onPrimaryLight,
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}
