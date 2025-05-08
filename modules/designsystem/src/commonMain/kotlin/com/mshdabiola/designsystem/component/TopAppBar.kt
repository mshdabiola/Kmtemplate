/*
 *abiola 2024
 */

@file:OptIn(ExperimentalMaterial3Api::class)

package com.mshdabiola.designsystem.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import com.mshdabiola.designsystem.icon.HyaIcons
import com.mshdabiola.designsystem.theme.HyaTheme
import hydraulic.modules.designsystem.generated.resources.Res
import hydraulic.modules.designsystem.generated.resources.app_name
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HyaTopAppBar(
    titleRes: StringResource? = null,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String,
    actionIcon: ImageVector,
    actionIconContentDescription: String,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(titleRes ?: Res.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        colors = colors,
        modifier = modifier.testTag("HyaTopAppBar"),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun HyaTopAppBarPreview() {
    HyaTheme {
        HyaTopAppBar(
            titleRes = Res.string.app_name,
            navigationIcon = HyaIcons.Search,
            navigationIconContentDescription = "Navigation icon",
            actionIcon = HyaIcons.MoreVert,
            actionIconContentDescription = "Action icon",
        )
    }
}
