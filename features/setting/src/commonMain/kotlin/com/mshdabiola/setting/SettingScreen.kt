/*
 *abiola 2022
 */

package com.mshdabiola.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

// import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SettingRoute(
    onBack: () -> Unit,
    onShowSnack: suspend (String, String?) -> Boolean,
    viewModel: SettingViewModel,
) {
    val settingState = viewModel.uiState.collectAsStateWithLifecycleCommon()

    SettingScreen(
        settingState = settingState.value,
        onBack = onBack,
        setThemeBrand = viewModel::setThemeBrand,
        setContrast = viewModel::setThemeContrast,
        setDarkThemeConfig = viewModel::setDarkThemeConfig,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
internal fun SettingScreen(
    settingState: SettingState,
    onBack: () -> Unit = {},
    setThemeBrand: (ThemeBrand) -> Unit = {},
    setDarkThemeConfig: (DarkThemeConfig) -> Unit = {},
    setContrast: (Contrast) -> Unit = {},

) {
    Scaffold(
        modifier = Modifier, // .semantics { this.testTagsAsResourceId = true },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back",
                        )
                    }
                },
                title = {
                    Text(text = "Setting")
                },
            )
        },
        containerColor = Color.Transparent,
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Theme")
                DropdownMenu(
                    currentIndex = ThemeBrand.entries.indexOf(settingState.userData.themeBrand),
                    data = ThemeBrand.entries.map { themeBrand ->
                        themeBrand
                            .name
                            .lowercase()
                            .replaceFirstChar {
                                it.uppercaseChar()
                            }
                    }
                        .toImmutableList(),
                    onDataChange = {
                        setThemeBrand(ThemeBrand.entries[it])
                    },
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Dark")
                DropdownMenu(
                    currentIndex = DarkThemeConfig.entries.indexOf(settingState.userData.darkThemeConfig),
                    data = DarkThemeConfig.entries.map { themeBrand ->
                        themeBrand
                            .name
                            .lowercase()
                            .replaceFirstChar {
                                it.uppercaseChar()
                            }
                    }
                        .toImmutableList(),
                    onDataChange = {
                        setDarkThemeConfig(DarkThemeConfig.entries[it])
                    },
                )
            }
        }
    }
}
//
// @Preview
// @Composable
// fun MainScreenPreview() {
//    MainScreen(
//        mainState = MainState(),
//       items =listOf(ModelUiState(2, "")).toImmutableList()
//    )
// }

@Composable
expect fun SettingScreenPreview()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    currentIndex: Int = 0,
    data: ImmutableList<String> = emptyList<String>().toImmutableList(),
    onDataChange: (Int) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = data[currentIndex],
            onValueChange = {},
            //  label = { Text("Label") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            data.forEachIndexed { index, s ->
                DropdownMenuItem(
                    text = { Text(s) },
                    onClick = {
                        onDataChange(index)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}
