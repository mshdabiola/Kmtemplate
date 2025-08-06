package com.mshdabiola.model.testtag

object SettingScreenListTestTags {
    const val SCREEN_ROOT = "setting_list:screen_root" // Typically the Scaffold or main LazyColumn
    const val TOP_APP_BAR = "setting_list:top_app_bar"
    const val MENU_ICON_BUTTON = "setting_list:menu_icon_button"
    const val SETTINGS_LAZY_COLUMN = "setting_list:lazy_column"

    // For dynamic section headers (using index)
    const val SECTION_HEADER_TEXT_PREFIX = "setting_list:section_header_"

    // For dynamic list items (using segment and index of SettingNav)
    const val LIST_ITEM_CARD_PREFIX = "setting_list_item:card_" // e.g., setting_list_item:card_Appearance
    const val LIST_ITEM_ICON_PREFIX = "setting_list_item:icon_"
    const val LIST_ITEM_TITLE_TEXT_PREFIX = "setting_list_item:title_"
}
