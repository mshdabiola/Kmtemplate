package com.mshdabiola.hydraulicapp.ui

import androidx.compose.ui.graphics.vector.ImageVector
import com.mshdabiola.designsystem.icon.HyaIcons
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.setting.navigation.Setting

data class TopLevelRoute<T : Any>(val route: T, val icon: ImageVector)

val TOP_LEVEL_ROUTES =
    listOf(
        TopLevelRoute(route = Main, icon = HyaIcons.Domain),
        TopLevelRoute(route = Unit, icon = HyaIcons.Person),
        TopLevelRoute(route = Unit, icon = HyaIcons.Stairs),
    )

val SETTING_LEVEL_ROUTES =
    listOf(
        TopLevelRoute(route = Setting, icon = HyaIcons.Settings),
        TopLevelRoute(route = Setting, icon = HyaIcons.Info),
    )
