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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
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
import com.mshdabiola.model.testtag.AppearanceScreenTestTags
import com.mshdabiola.setting.SettingState
import kmtemplate.feature.setting.generated.resources.Res
import kmtemplate.feature.setting.generated.resources.appearance_background_title
import kmtemplate.feature.setting.generated.resources.appearance_contrast_option_high_cd
import kmtemplate.feature.setting.generated.resources.appearance_contrast_option_high_label
import kmtemplate.feature.setting.generated.resources.appearance_contrast_option_low_cd
import kmtemplate.feature.setting.generated.resources.appearance_contrast_option_low_label
import kmtemplate.feature.setting.generated.resources.appearance_contrast_option_standard_cd
import kmtemplate.feature.setting.generated.resources.appearance_contrast_option_standard_label
import kmtemplate.feature.setting.generated.resources.appearance_contrast_title
import kmtemplate.feature.setting.generated.resources.appearance_dark_mode_title
import kmtemplate.feature.setting.generated.resources.appearance_gradient_background_text
import kmtemplate.feature.setting.generated.resources.appearance_select_contrast_high
import kmtemplate.feature.setting.generated.resources.appearance_select_contrast_low
import kmtemplate.feature.setting.generated.resources.appearance_select_contrast_standard
import kmtemplate.feature.setting.generated.resources.daynight
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppearanceScreen(
    modifier: Modifier = Modifier,
    settingsState: SettingState,
    onContrastChange: (Int) -> Unit,
    onDarkModeChange: (DarkThemeConfig) -> Unit,
    onGradientBackgroundChange: (Boolean) -> Unit,
) {
    val contrastOptions = listOf(
        ContrastOption(
            id = 0,
            icon = KmtIcons.LightMode,
            contentDescription = stringResource(Res.string.appearance_contrast_option_low_cd),
            label = stringResource(Res.string.appearance_contrast_option_low_label),
            clickLabel = stringResource(Res.string.appearance_select_contrast_low),
        ),
        ContrastOption(
            id = 1,
            icon = KmtIcons.Contrast,
            contentDescription = stringResource(Res.string.appearance_contrast_option_standard_cd),
            label = stringResource(Res.string.appearance_contrast_option_standard_label),
            clickLabel = stringResource(Res.string.appearance_select_contrast_standard),
        ),
        ContrastOption(
            id = 2,
            icon = KmtIcons.DarkMode,
            contentDescription = stringResource(Res.string.appearance_contrast_option_high_cd),
            label = stringResource(Res.string.appearance_contrast_option_high_label),
            clickLabel = stringResource(Res.string.appearance_select_contrast_high),
        ),
    )
    val dayNightOptions = stringArrayResource(Res.array.daynight)
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scrollState)
            .testTag(AppearanceScreenTestTags.SCREEN_ROOT),
    ) {
        Text(
            text = stringResource(Res.string.appearance_contrast_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .testTag(AppearanceScreenTestTags.CONTRAST_TITLE),
        )
        ContrastTimeline(
            // modifier is passed, root tag is inside ContrastTimeline
            options = contrastOptions,
            selectedOptionId = settingsState.contrast,
            onOptionSelected = { onContrastChange(it) },
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(Res.string.appearance_background_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .testTag(AppearanceScreenTestTags.BACKGROUND_TITLE),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onGradientBackgroundChange(!settingsState.gradientBackground) }
                .padding(vertical = 12.dp)
                .testTag(AppearanceScreenTestTags.GRADIENT_BACKGROUND_ROW),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.appearance_gradient_background_text),
                modifier = Modifier.weight(1f)
                    .testTag(AppearanceScreenTestTags.GRADIENT_BACKGROUND_TEXT),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Switch(
                checked = settingsState.gradientBackground,
                onCheckedChange = { onGradientBackgroundChange(it) },
                modifier = Modifier.testTag(AppearanceScreenTestTags.GRADIENT_BACKGROUND_SWITCH),
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(Res.string.appearance_dark_mode_title),
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
                    .testTag(AppearanceScreenTestTags.darkModeOptionRow(darkThemeConfigEntry.name)),
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
                        AppearanceScreenTestTags.darkModeRadioButton(darkThemeConfigEntry.name),
                    ),
                )
                Text(
                    text = dayNightOptions[darkThemeConfigEntry.ordinal],
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .testTag(
                            AppearanceScreenTestTags.darkModeOptionText(darkThemeConfigEntry.name),
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
    val clickLabel: String,
)

@Composable
fun ContrastTimeline(
    modifier: Modifier = Modifier, // Allow modifier to be passed
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
        modifier = modifier // Apply the passed modifier here
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .testTag(AppearanceScreenTestTags.ContrastTimelineTestTags.TIMELINE_ROOT),
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
                        onClickLabel = option.clickLabel,
                    )
                    .padding(horizontal = 4.dp)
                    .testTag(AppearanceScreenTestTags.ContrastTimelineTestTags.optionItem(option.id)),
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
                            .testTag(AppearanceScreenTestTags
                                .ContrastTimelineTestTags.optionBackground(option.id)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = option.icon,
                            contentDescription = option.contentDescription,
                            modifier = Modifier
                                .size(iconSize)
                                .testTag(AppearanceScreenTestTags
                                    .ContrastTimelineTestTags.optionIcon(option.id)),
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

@Preview(showBackground = true)
@Composable
fun AppearanceScreenPreview() {
    KmtTheme {
        AppearanceScreen(
            settingsState = SettingState(
                contrast = 0,
                darkThemeConfig = DarkThemeConfig.DARK,
                gradientBackground = true,
            ),
            onContrastChange = {},
            onDarkModeChange = {},
            onGradientBackgroundChange = {},
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
            clickLabel = "Select Low",
        ),
        ContrastOption(
            id = 1,
            icon = KmtIcons.Contrast,
            contentDescription = "Standard Contrast",
            label = "Standard",
            clickLabel = "Select Standard",
        ),
        ContrastOption(
            id = 2,
            icon = KmtIcons.DarkMode,
            contentDescription = "High Contrast",
            label = "High",
            clickLabel = "Select High",
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
