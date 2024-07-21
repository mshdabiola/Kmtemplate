/*
 *abiola 2022
 */

package com.mshdabiola.setting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import hydraulic.features.setting.generated.resources.Res
import hydraulic.features.setting.generated.resources.daynight
import hydraulic.features.setting.generated.resources.theme
import org.jetbrains.compose.resources.stringArrayResource

// import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SettingRoute(
    modifier: Modifier = Modifier,
    onShowSnack: suspend (String, String?) -> Boolean,
    viewModel: SettingViewModel,
) {
    val settingState = viewModel.uiState.collectAsStateWithLifecycleCommon()

    var dark by remember { mutableStateOf(false) }
    var theme by remember { mutableStateOf(false) }

    SettingScreen(
        modifier = modifier,
        settingState = settingState.value,
        showDarkDialog = { dark = true },
        showThemeDialog = { theme = true },
    )

    AnimatedVisibility(theme) {
        OptionsDialog(
            modifier = Modifier,
            options = stringArrayResource(Res.array.theme),
            current = settingState.value.userData.themeBrand.ordinal,
            onDismiss = { theme = false },
            onSelect = { viewModel.setThemeBrand(ThemeBrand.entries[it]) },
        )
    }
    AnimatedVisibility(dark) {
        OptionsDialog(
            modifier = Modifier,
            options = stringArrayResource(Res.array.daynight),
            current = settingState.value.userData.darkThemeConfig.ordinal,
            onDismiss = { dark = false },
            onSelect = { viewModel.setDarkThemeConfig(DarkThemeConfig.entries[it]) },
        )
    }
}

@Composable
internal fun SettingScreen(
    settingState: SettingState,
    modifier: Modifier = Modifier,
    showDarkDialog: () -> Unit = {},
    showThemeDialog: () -> Unit = {},
) {
    Card(modifier = modifier) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(text = "Settings", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))

            ListItem(
                modifier = Modifier.clickable { showThemeDialog() },
                headlineContent = { Text("Theme") },
                supportingContent = {
                    Text(stringArrayResource(Res.array.theme)[settingState.userData.themeBrand.ordinal])
                },
            )

            ListItem(
                modifier = Modifier.clickable { showDarkDialog() },
                headlineContent = { Text("DayNight mode") },
                supportingContent = {
                    Text(stringArrayResource(Res.array.daynight)[settingState.userData.darkThemeConfig.ordinal])
                },
            )
        }
    }
}
