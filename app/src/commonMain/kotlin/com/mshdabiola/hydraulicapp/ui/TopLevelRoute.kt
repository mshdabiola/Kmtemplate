package com.mshdabiola.hydraulicapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Stairs
import androidx.compose.ui.graphics.vector.ImageVector
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.setting.navigation.Setting

data class TopLevelRoute<T : Any>(val route: T, val icon: ImageVector)

val TOP_LEVEL_ROUTES =
    listOf(
        TopLevelRoute(route = Main, icon = Icons.Default.Domain),
        TopLevelRoute(route = Unit, icon = Icons.Default.Person),
        TopLevelRoute(route = Unit, icon = Icons.Default.Stairs),
    )

val SETTING_LEVEL_ROUTES =
    listOf(
        TopLevelRoute(route = Setting, icon = Icons.Default.Settings),
        TopLevelRoute(route = Setting, icon = Icons.Default.Info),
//    TopLevelRoute(route = SettingArg, icon = Icons.Default.Feedback),
//    TopLevelRoute(route = SettingArg, icon = Icons.Default.Insights),
//    TopLevelRoute(route = SettingArg, icon = Icons.AutoMirrored.Filled.Logout)
    )
