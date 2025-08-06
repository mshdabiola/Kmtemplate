package com.mshdabiola.model.testtag

// Test Tags for KmtScaffold and its inner components
object KmtScaffoldTestTags {
    const val MODAL_NAVIGATION_DRAWER = "scaffold:modal_nav_drawer"
    const val PERMANENT_NAVIGATION_DRAWER = "scaffold:permanent_nav_drawer"
    const val MODAL_DRAWER_SHEET = "scaffold:modal_drawer_sheet"
    const val WIDE_NAVIGATION_RAIL = "scaffold:wide_nav_rail"
    const val PERMANENT_DRAWER_SHEET = "scaffold:permanent_drawer_sheet"
    const val RAIL_TOGGLE_BUTTON = "scaffold:rail_toggle_button" // For expanding/collapsing rail
    const val SCAFFOLD_CONTENT_AREA = "scaffold:content_area" // For the main Scaffold used inside drawers

    object FabTestTags {
        const val FAB_ANIMATED_CONTENT = "fab:animated_content"
        const val SMALL_FAB = "fab:small_fab"
        const val EXTENDED_FAB = "fab:extended_fab"
        const val FAB_ADD_ICON = "fab:add_icon" // Common for both FAB types
        const val FAB_ADD_TEXT = "fab:add_text" // Specific to Extended FAB
    }


    object DrawerContentTestTags {
        const val DRAWER_CONTENT_COLUMN = "drawer:content_column"
        const val BRAND_ROW = "drawer:brand_row"
        const val BRAND_ICON = "drawer:brand_icon"
        const val BRAND_TEXT = "drawer:brand_text"
        fun navigationItemTag(route: Any) = "drawer:nav_item_$route"
        fun wideNavigationRailItemTag(route: Any) = "drawer:wide_nav_rail_item_$route"
    }



}
