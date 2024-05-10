package com.mshdabiola.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.icon.mainIcons
import com.mshdabiola.designsystem.icon.mainRoute
import com.mshdabiola.designsystem.icon.settingIcons
import com.mshdabiola.designsystem.icon.settingRoute
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import skeletonmultiplatform.modules.ui.generated.resources.Res
import skeletonmultiplatform.modules.ui.generated.resources.app_name
import skeletonmultiplatform.modules.ui.generated.resources.main
import skeletonmultiplatform.modules.ui.generated.resources.main_navigator
import skeletonmultiplatform.modules.ui.generated.resources.setting_navigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonNavigation(
    modifier: Modifier = Modifier,
    currentNavigation: String = mainRoute[0],
    onCreate: () -> Unit = {},
    showLong: Boolean = true,
    onNavigate: (String) -> Unit = {},

) {
    val color = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent)

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface, // LocalBackgroundTheme.current.color,
        shape = RoundedCornerShape(0.dp),

    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .padding(8.dp)
                .verticalScroll(state = rememberScrollState()),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocalLibrary, "Logo")
                Text(
                    stringResource(Res.string.app_name),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
            Spacer(Modifier.height(32.dp))
//        FloatingActionButton(
//            containerColor = MaterialTheme.colorScheme.secondaryContainer,
//            onClick = onCreate){
//            Row (
//                modifier = Modifier.padding(ButtonDefaults.TextButtonWithIconContentPadding),
//                verticalAlignment = Alignment.CenterVertically
//                ){
//                Icon(Icons.Default.AddAPhoto,"add image")
//                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
//                Text(stringResource(Res.string.add_image))
//            }
//        }

            if (showLong) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(stringResource(Res.string.main))
                    stringArrayResource(Res.array.main_navigator)
                        .forEachIndexed { index, navigator ->
                            NavigationDrawerItem(
                                selected = currentNavigation.contains(mainRoute[index]),
                                label = { Text(navigator) },
                                onClick = { onNavigate(mainRoute[index]) },
                                colors = color,
                                icon = { Icon(mainIcons[index], navigator) },
                            )
                        }
                }
            }

            Spacer(Modifier.height(64.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                stringArrayResource(Res.array.setting_navigator)
                    .forEachIndexed { index, navigator ->
                        NavigationDrawerItem(
                            selected = currentNavigation.contains(settingRoute[index]),
                            label = { Text(navigator) },
                            onClick = { onNavigate(settingRoute[index]) },
                            colors = color,
                            icon = { Icon(settingIcons[index], navigator) },
                        )
                    }
            }
            Spacer(Modifier.height(8.dp))

            HorizontalDivider()
            Spacer(Modifier.height(8.dp))

            ProfileCard()
        }
    }
}

@Composable
fun CommonRail(
    modifier: Modifier = Modifier,
    currentNavigation: String = mainRoute[0],
    onCreate: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
) {
    NavigationRail(modifier) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(state = rememberScrollState()),
        ) {
            Icon(Icons.Default.LocalLibrary, "Logo")

            Spacer(Modifier.height(32.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                Text(stringResource(Res.string.main))
                stringArrayResource(Res.array.main_navigator)
                    .forEachIndexed { index, navigator ->
                        NavigationRailItem(
                            selected = currentNavigation.contains(mainRoute[index]),
//                            label = { Text(navigator) },
                            onClick = { onNavigate(mainRoute[index]) },
                            alwaysShowLabel = false,
                            icon = { Icon(mainIcons[index], navigator) },
                        )
                    }
            }

            Spacer(Modifier.height(64.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                stringArrayResource(Res.array.setting_navigator)
                    .forEachIndexed { index, navigator ->
                        NavigationRailItem(
                            selected = currentNavigation.contains(settingRoute[index]),
                            // label = { Text(navigator) },
                            onClick = { onNavigate(settingRoute[index]) },
                            alwaysShowLabel = false,
                            icon = { Icon(settingIcons[index], navigator) },
                        )
                    }
            }

            HorizontalDivider()

            // ProfileCard()
        }
    }
}

@Composable
fun CommonBar(
    modifier: Modifier = Modifier,
    currentNavigation: String = mainRoute[0],
    onCreate: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
) {
    NavigationBar(modifier) {
        stringArrayResource(Res.array.main_navigator)
            .forEachIndexed { index, navigator ->
                NavigationBarItem(
                    selected = currentNavigation.contains(mainRoute[index]),
                    // label = { Text(navigator) },
                    onClick = { onNavigate(mainRoute[index]) },
                    alwaysShowLabel = false,
                    icon = { Icon(mainIcons[index], navigator) },
                )
            }
    }
}

@Composable
expect fun NavigationPreview()
