package com.mshdabiola.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable

@Preview
@Composable
fun NavigationRailPreview() {
    // CommonNavigation()
    CommonRail { }
}

@Preview
@Composable
fun NavigationBarPreview() {
    // CommonNavigation()
    CommonBar { }
}

@Preview
@Composable
fun NavigationPreview() {
    // CommonNavigation()
    Drawer { }
}
