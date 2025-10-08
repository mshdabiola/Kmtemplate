/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.model.testtag

// Test Tags for KmtScaffold and its inner components
object KmtScaffoldTestTags {
    const val MODAL_NAVIGATION_DRAWER = "KmtScaffold:ModalNavigationDrawer"
    const val PERMANENT_NAVIGATION_DRAWER = "KmtScaffold:PermanentNavigationDrawer"
    const val MODAL_DRAWER_SHEET = "KmtScaffold:ModalDrawerSheet"
    const val WIDE_NAVIGATION_RAIL = "KmtScaffold:WideNavigationRail"
    const val PERMANENT_DRAWER_SHEET = "KmtScaffold:PermanentDrawerSheet"
    const val RAIL_TOGGLE_BUTTON = "KmtScaffold:RailToggleButton"
    const val SCAFFOLD_CONTENT_AREA = "KmtScaffold:ScaffoldContentArea"

    object FabTestTags {
        const val FAB_ANIMATED_CONTENT = "KmtScaffold:FabAnimatedContent"
        const val SMALL_FAB = "KmtScaffold:SmallFab"
        const val EXTENDED_FAB = "KmtScaffold:ExtendedFab"
        const val FAB_ADD_ICON = "KmtScaffold:FabAddIcon"
        const val FAB_ADD_TEXT = "KmtScaffold:FabAddText"
    }

    object DrawerContentTestTags {
        const val DRAWER_CONTENT_COLUMN = "KmtScaffold:DrawerContentColumn"
        const val BRAND_ROW = "KmtScaffold:BrandRow"
        const val BRAND_ICON = "KmtScaffold:BrandIcon"
        const val BRAND_TEXT = "KmtScaffold:BrandText"
        fun navigationItemTag(route: Any) = "KmtScaffold:NavigationItem:$route"
        fun wideNavigationRailItemTag(route: Any) = "KmtScaffold:WideNavigationRailItem:$route"
    }
}
