/*
 *abiola 2022
 */

package com.mshdabiola.setting

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.ui.collectAsStateWithLifecycleCommon
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

// import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SettingRoute(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onShowSnack: suspend (String, String?) -> Boolean,
    viewModel: SettingViewModel,
) {
    val settingState = viewModel.uiState.collectAsStateWithLifecycleCommon()

    SettingScreen(
        modifier = modifier,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        settingState = settingState.value,
        setThemeBrand = viewModel::setThemeBrand,
        setContrast = viewModel::setThemeContrast,
        setDarkThemeConfig = viewModel::setDarkThemeConfig,
    )
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalSharedTransitionApi::class,
)
@Composable
internal fun SettingScreen(
    settingState: SettingState,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    setThemeBrand: (ThemeBrand) -> Unit = {},
    setDarkThemeConfig: (DarkThemeConfig) -> Unit = {},
    setContrast: (Contrast) -> Unit = {},

    ) {
    with(sharedTransitionScope) {
        Column(
            modifier.sharedBounds(
                sharedContentState = rememberSharedContentState("container"),
                animatedVisibilityScope =animatedContentScope,
            ),
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
