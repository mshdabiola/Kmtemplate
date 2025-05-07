/*
 *abiola 2024
 */

@file:OptIn(ExperimentalMaterial3Api::class)

package com.mshdabiola.designsystem.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.designsystem.component.DetailTopAppBar
import com.mshdabiola.designsystem.component.SkTopAppBar
import com.mshdabiola.designsystem.icon.HyaIcons

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun SkTopAppBarPreview() {
    SkTopAppBar(
        titleRes = "",
        navigationIcon = HyaIcons.Search,
        navigationIconContentDescription = "Navigation icon",
        actionIcon = HyaIcons.MoreVert,
        actionIconContentDescription = "Action icon",
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun DetailTopAppBarPreview() {
    DetailTopAppBar()
}
