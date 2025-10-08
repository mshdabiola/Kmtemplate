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
    const val MODAL_NAVIGATION_DRAWER = "SynScaffold:ModalNavigationDrawer"
    const val PERMANENT_NAVIGATION_DRAWER = "SynScaffold:PermanentNavigationDrawer"
    const val MODAL_DRAWER_SHEET = "SynScaffold:ModalDrawerSheet"
    const val WIDE_NAVIGATION_RAIL = "SynScaffold:WideNavigationRail"
    const val PERMANENT_DRAWER_SHEET = "SynScaffold:PermanentDrawerSheet"
    const val RAIL_TOGGLE_BUTTON = "SynScaffold:RailToggleButton"
    const val SCAFFOLD_CONTENT_AREA = "SynScaffold:ScaffoldContentArea"

    object FabTestTags {
        const val FAB_ANIMATED_CONTENT = "SynScaffold:FabAnimatedContent"
        const val SMALL_FAB = "SynScaffold:SmallFab"
        const val EXTENDED_FAB = "SynScaffold:ExtendedFab"
        const val FAB_ADD_ICON = "SynScaffold:FabAddIcon"
        const val FAB_ADD_TEXT = "SynScaffold:FabAddText"
    }

    object DrawerContentTestTags {
        const val DRAWER_CONTENT_COLUMN = "SynScaffold:DrawerContentColumn"
        const val BRAND_ROW = "SynScaffold:BrandRow"
        const val BRAND_ICON = "SynScaffold:BrandIcon"
        const val BRAND_TEXT = "SynScaffold:BrandText"
        fun navigationItemTag(route: Any) = "SynScaffold:NavigationItem:$route"
        fun wideNavigationRailItemTag(route: Any) = "SynScaffold:WideNavigationRailItem:$route"
    }
}
