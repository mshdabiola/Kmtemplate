/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.kotlinmultiplatformtemplate.ui

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.mshdabiola.designsystem.icon.KmtIcons
import com.mshdabiola.main.navigation.Main
import com.mshdabiola.setting.navigation.Setting

data class TopLevelRoute<T : Any>(
    val route: T,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val label: String,
)

val TOP_LEVEL_ROUTES =
    setOf(
        TopLevelRoute(
            route = Main,
            selectedIcon = KmtIcons.Home,
            unSelectedIcon = KmtIcons.HomeOutlined,
            label = "Home",
        ),
        TopLevelRoute(
            route = Setting,
            selectedIcon = KmtIcons.Settings,
            unSelectedIcon = KmtIcons.SettingsOutlined,
            label = "Settings",
        ),
        TopLevelRoute(
            route = Unit,
            selectedIcon = KmtIcons.About,
            unSelectedIcon = KmtIcons.AboutOutlined,
            label = "About",
        ),
    )
