/*
 * Copyright (C) 2023-2025 MshdAbiola
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
