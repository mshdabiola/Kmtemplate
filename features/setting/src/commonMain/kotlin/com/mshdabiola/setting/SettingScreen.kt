/*
 *abiola 2022
 */

package com.mshdabiola.setting

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mshdabiola.designsystem.icon.HyaIcons
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.ui.Waiting
import hydraulicapp.features.setting.generated.resources.Res
import hydraulicapp.features.setting.generated.resources.daynight
import hydraulicapp.features.setting.generated.resources.theme
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringArrayResource

// import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SettingRoute(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
    viewModel: SettingViewModel,
) {
    val settingState = viewModel.settingState.collectAsStateWithLifecycle()

    SettingScreen(
        modifier = modifier.heightIn(min = 300.dp),
        settingState = settingState.value,
        setTheme = viewModel::setThemeBrand,
        setDarkMode = viewModel::setDarkThemeConfig,
        onBack = onBack,
        onShowSnack = onShowSnack,
    )
}

@Composable
internal fun SettingScreen(
    settingState: SettingState,
    modifier: Modifier = Modifier,
    setTheme: (ThemeBrand) -> Unit = {},
    setDarkMode: (DarkThemeConfig) -> Unit = {},
    onBack: () -> Unit = {},
    onShowSnack: suspend (String, String?) -> Boolean = { _, _ -> false },
) {
    Card(modifier = modifier.testTag("setting:screen")) {
        AnimatedContent(settingState) {
            when (it) {
                is SettingState.Loading -> Waiting(modifier)
                is SettingState.Success ->
                    MainContent(
                        modifier = modifier,
                        settingState = it,
                        setTheme = setTheme,
                        setDarkMode = setDarkMode,
                        onBack = onBack,
                        onShowSnack = onShowSnack,
                    )

                else -> {}
            }
        }
    }
}

@Composable
internal fun MainContent(
    modifier: Modifier = Modifier,
    settingState: SettingState.Success,
    setTheme: (ThemeBrand) -> Unit = {},
    setDarkMode: (DarkThemeConfig) -> Unit = {},
    onBack: () -> Unit = {},
    onShowSnack: suspend (String, String?) -> Boolean = { _, _ -> false },
) {
    var dark by remember { mutableStateOf(false) }
    var theme by remember { mutableStateOf(false) }
    val themeArray = stringArrayResource(Res.array.theme)
    val dayLightArray = stringArrayResource(Res.array.daynight)
    val couroutine = rememberCoroutineScope()

    Column(
        modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Settings", style = MaterialTheme.typography.titleLarge)
            IconButton(
                onClick = onBack,
            ) {
                Icon(imageVector = HyaIcons.Cancel, "cancel")
            }
        }

        Spacer(Modifier.height(8.dp))

        ListItem(
            modifier =
            Modifier.testTag("setting:theme")
                .clickable {
                    theme = true
                    couroutine.launch {
                        onShowSnack("Theme", "Clicked")
                    }
                },
            headlineContent = { Text("Theme") },
            supportingContent = {
                Text(themeArray.getOrNull(settingState.themeBrand.ordinal) ?: "")
            },
        )

        ListItem(
            modifier = Modifier.testTag("setting:mode").clickable { dark = true },
            headlineContent = { Text("DayNight mode") },
            supportingContent = {
                Text(dayLightArray.getOrNull(settingState.darkThemeConfig.ordinal) ?: "")
            },
        )
    }

    AnimatedVisibility(theme) {
        OptionsDialog(
            modifier = Modifier,
            options = themeArray,
            current = settingState.themeBrand.ordinal,
            onDismiss = { theme = false },
            onSelect = { setTheme(ThemeBrand.entries[it]) },
        )
    }
    AnimatedVisibility(dark) {
        OptionsDialog(
            modifier = Modifier,
            options = dayLightArray,
            current = settingState.darkThemeConfig.ordinal,
            onDismiss = { dark = false },
            onSelect = { setDarkMode(DarkThemeConfig.entries[it]) },
        )
    }
}
