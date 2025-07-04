/*
 * Copyright (C) 2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package com.mshdabiola.kotlinmultiplatformtemplate.ui

import androidx.compose.ui.graphics.vector.ImageVector
import com.mshdabiola.designsystem.icon.KmtIcons
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.setting.navigation.Setting

data class TopLevelRoute<T : Any>(val route: T, val icon: ImageVector)

val TOP_LEVEL_ROUTES =
    listOf(
        TopLevelRoute(route = Main, icon = KmtIcons.Domain),
        TopLevelRoute(route = Unit, icon = KmtIcons.Person),
        TopLevelRoute(route = Unit, icon = KmtIcons.Stairs),
    )

val SETTING_LEVEL_ROUTES =
    listOf(
        TopLevelRoute(route = Setting, icon = KmtIcons.Settings),
        TopLevelRoute(route = Setting, icon = KmtIcons.Info),
    )
