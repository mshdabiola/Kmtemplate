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
package com.mshdabiola.setting.detailscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.drawable.KmtIcons
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.setting.SettingState
import kotlinmultiplatformtemplate.features.setting.generated.resources.Res
import kotlinmultiplatformtemplate.features.setting.generated.resources.daynight
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppearanceScreen(
    modifier: Modifier = Modifier,
    settingsState: SettingState,
    onContrastChange: (Int) -> Unit,
    onDarkModeChange: (DarkThemeConfig) -> Unit,
) {
    val contrastOptions = listOf(
        ContrastOption(
            id = 0,
            icon = KmtIcons.LightMode, // Representing "Low Contrast"
            contentDescription = "Low Contrast",
            label = "Low",
        ),
        ContrastOption(
            id = 1,
            icon = KmtIcons.Contrast, // Representing "Standard Contrast"
            contentDescription = "Standard Contrast",
            label = "Standard",
        ),
        ContrastOption(
            id = 2,
            icon = KmtIcons.DarkMode, // Representing "High Contrast"
            contentDescription = "High Contrast",
            label = "High",
        ),
    )
    val dayNightOptions = stringArrayResource(Res.array.daynight)

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Contrast",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        ContrastTimeline(
            options = contrastOptions,
            selectedOptionId = settingsState.contrast,
            onOptionSelected = { onContrastChange(it) },
        )

        Spacer(modifier = Modifier.height(16.dp)) // Add space between sections

        Text(
            text = "Dark Mode",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        DarkThemeConfig.entries.forEach { darkThemeConfigEntry ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDarkModeChange(darkThemeConfigEntry) }
                    .padding(vertical = 12.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = darkThemeConfigEntry.ordinal == settingsState.darkThemeConfig.ordinal,
                    onClick = { onDarkModeChange(darkThemeConfigEntry) },
                )
                Text(
                    text = dayNightOptions[darkThemeConfigEntry.ordinal],
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
        }
    }
}

@Preview
@Composable
fun AppearanceScreenPreview() {
    AppearanceScreen(
        settingsState = SettingState(contrast = 0, darkThemeConfig = DarkThemeConfig.DARK),
        onContrastChange = {},
        onDarkModeChange = {},
    )
}

data class ContrastOption(
    val id: Int,
    val icon: ImageVector,
    val contentDescription: String,
    val label: String,
)

@Composable
fun ContrastTimeline(
    modifier: Modifier = Modifier,
    options: List<ContrastOption>,
    selectedOptionId: Int,
    onOptionSelected: (Int) -> Unit,
    iconSize: Dp = 24.dp,
    lineThickness: Dp = 2.dp,
    selectedIndicatorSize: Dp = 32.dp,
    unselectedIndicatorSize: Dp = 28.dp,
    lineColor: Color = MaterialTheme.colorScheme.outlineVariant,
    selectedIconColor: Color = MaterialTheme.colorScheme.primary,
    unselectedIconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    selectedBackgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    unselectedBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = option.id == selectedOptionId
            val currentIndicatorSize = if (isSelected) selectedIndicatorSize else unselectedIndicatorSize

            // Clickable area for the icon and its surrounding
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        onClick = { onOptionSelected(option.id) },
                        role = Role.RadioButton,
                        onClickLabel = "Select ${option.label}",
                    )
                    .padding(horizontal = 4.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Line before the first item (conditionally)
                    if (index > 0) {
                        HorizontalDivider(
                            modifier = Modifier
                                .weight(1f)
                                .height(lineThickness),
                            color = lineColor,
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Box(
                        modifier = Modifier
                            .size(currentIndicatorSize)
                            .clip(CircleShape)
                            .background(if (isSelected) selectedBackgroundColor else unselectedBackgroundColor)
                            .border(
                                width = if (isSelected) 1.5.dp else 0.dp,
                                color = if (isSelected) selectedIconColor else Color.Transparent,
                                shape = CircleShape,
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = option.icon,
                            contentDescription = option.contentDescription,
                            modifier = Modifier.size(iconSize),
                            tint = if (isSelected) selectedIconColor else unselectedIconColor,
                        )
                    }

                    // Line after the last item (conditionally)
                    if (index < options.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier
                                .weight(1f)
                                .height(lineThickness),
                            color = lineColor,
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ContrastTimelinePreview() {
    val contrastOptions = listOf(
        ContrastOption(
            id = 0,
            icon = KmtIcons.LightMode,
            contentDescription = "Low Contrast",
            label = "Low",
        ),
        ContrastOption(
            id = 1,
            icon = KmtIcons.Contrast,
            contentDescription = "Standard Contrast",
            label = "Standard",
        ),
        ContrastOption(
            id = 2,
            icon = KmtIcons.DarkMode,
            contentDescription = "High Contrast",
            label = "High",
        ),
    )

    KmtTheme {
        // Use your app's theme
        ContrastTimeline(
            options = contrastOptions,
            selectedOptionId = 0,
            onOptionSelected = { },
        )
    }
}
