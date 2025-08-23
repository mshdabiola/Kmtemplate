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
