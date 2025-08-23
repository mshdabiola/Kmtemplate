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

object SettingDetailScreenTestTags {
    val TOP_APP_BAR_TITLE = "setting_detail:top_app_bar_title"
    const val SCREEN_ROOT = "setting_detail:screen_root"
    const val TOP_APP_BAR = "setting_detail:top_app_bar"
    const val BACK_ICON_BUTTON = "setting_detail:back_icon_button"

    // If you need to target specific detail content containers,
    // you might add tags like:
    // const val APPEARANCE_CONTENT_CONTAINER = "setting_detail:appearance_content"
    // const val FAQ_CONTENT_CONTAINER = "setting_detail:faq_content"
    // const val ABOUT_CONTENT_CONTAINER = "setting_detail:about_content"
    // However, often the tags within the specific content screens (AppearanceScreen, FaqScreen, etc.)
    // are sufficient. You can decide based on your testing needs.
}
