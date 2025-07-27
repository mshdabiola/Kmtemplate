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
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag // Make sure this is imported
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.drawable.KmtIcons
import com.mshdabiola.designsystem.theme.KmtTheme // For Preview
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
    val dayNightOptions = stringArrayResource(Res.array.daynight)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .testTag(AppearanceScreenTestTags.SCREEN_ROOT), // Tag for the root
    ) {
        Text(
            text = "Contrast",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .testTag(AppearanceScreenTestTags.CONTRAST_TITLE),
        )
        ContrastTimeline(
            modifier = Modifier.testTag(AppearanceScreenTestTags.CONTRAST_TIMELINE),
            options = contrastOptions,
            selectedOptionId = settingsState.contrast,
            onOptionSelected = { onContrastChange(it) },
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Dark Mode",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .testTag(AppearanceScreenTestTags.DARK_MODE_TITLE),
        )
        DarkThemeConfig.entries.forEach { darkThemeConfigEntry ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDarkModeChange(darkThemeConfigEntry) }
                    .padding(vertical = 12.dp)
                    // Dynamic test tag for the row, including the entry name for uniqueness
                    .testTag("${AppearanceScreenTestTags.DARK_MODE_OPTION_ROW_PREFIX}${darkThemeConfigEntry.name}"),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = darkThemeConfigEntry.ordinal == settingsState.darkThemeConfig.ordinal,
                    onClick = { onDarkModeChange(darkThemeConfigEntry) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledSelectedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                        disabledUnselectedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                    ),
                    modifier = Modifier.testTag(
                        "${AppearanceScreenTestTags.DARK_MODE_RADIO_BUTTON_PREFIX}${darkThemeConfigEntry.name}",
                    ),
                )
                Text(
                    text = dayNightOptions[darkThemeConfigEntry.ordinal],
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .testTag(
                            "${AppearanceScreenTestTags.DARK_MODE_OPTION_TEXT_PREFIX}${darkThemeConfigEntry.name}",
                        ),
                )
            }
        }
    }
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
        modifier = modifier // The root tag for ContrastTimeline is applied where it's used
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .testTag(ContrastTimelineTestTags.TIMELINE_ROOT), // Tag for the timeline root
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = option.id == selectedOptionId
            val currentIndicatorSize = if (isSelected) selectedIndicatorSize else unselectedIndicatorSize

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        onClick = { onOptionSelected(option.id) },
                        role = Role.RadioButton,
                        onClickLabel = "Select ${option.label}",
                    )
                    .padding(horizontal = 4.dp)
                    // Dynamic tag for each clickable option item in the timeline
                    .testTag("${ContrastTimelineTestTags.OPTION_ITEM_PREFIX}${option.id}"),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
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
                            )
                            // Dynamic tag for the background/indicator of each option
                            .testTag("${ContrastTimelineTestTags.OPTION_BACKGROUND_PREFIX}${option.id}"),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = option.icon,
                            contentDescription = option.contentDescription, // Could be used for semantics-based finding
                            modifier = Modifier
                                .size(iconSize)
                                // Dynamic tag for each icon
                                .testTag("${ContrastTimelineTestTags.OPTION_ICON_PREFIX}${option.id}"),
                            tint = if (isSelected) selectedIconColor else unselectedIconColor,
                        )
                    }

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
fun AppearanceScreenPreview() {
    KmtTheme {
        AppearanceScreen(
            settingsState = SettingState(contrast = 0, darkThemeConfig = DarkThemeConfig.DARK),
            onContrastChange = {},
            onDarkModeChange = {},
        )
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
        ContrastTimeline(
            options = contrastOptions,
            selectedOptionId = 0,
            onOptionSelected = { },
        )
    }
}

object AppearanceScreenTestTags {
    const val SCREEN_ROOT = "appearance:screen_root"

    const val CONTRAST_TITLE = "appearance:contrast_title"
    const val CONTRAST_TIMELINE = "appearance:contrast_timeline"
    // For individual contrast options, dynamic tags are often better,
    // but you can define a prefix if needed for finding options by a common pattern.
    // Example: const val CONTRAST_OPTION_PREFIX = "appearance:contrast_option_"

    const val DARK_MODE_TITLE = "appearance:dark_mode_title"

    // For individual dark mode options (RadioButton + Text row)
    // It's common to tag the Row or the RadioButton itself.
    // We'll use a dynamic approach in the example below by appending the config name.
    const val DARK_MODE_OPTION_ROW_PREFIX = "appearance:dark_mode_option_row_"
    const val DARK_MODE_RADIO_BUTTON_PREFIX = "appearance:dark_mode_radio_button_"
    const val DARK_MODE_OPTION_TEXT_PREFIX = "appearance:dark_mode_option_text_"
}

// Test tags for the ContrastTimeline component if it's complex enough
// or if you test it in isolation.
object ContrastTimelineTestTags {
    const val TIMELINE_ROOT = "contrast_timeline:root"
    const val OPTION_ITEM_PREFIX = "contrast_timeline:option_item_" // e.g., option_item_0, option_item_1
    const val OPTION_ICON_PREFIX = "contrast_timeline:option_icon_"
    const val OPTION_BACKGROUND_PREFIX = "contrast_timeline:option_background_"
}
