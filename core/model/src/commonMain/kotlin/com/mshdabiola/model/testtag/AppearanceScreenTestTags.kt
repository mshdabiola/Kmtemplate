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

object AppearanceScreenTestTags {
    const val SCREEN_ROOT = "appearance:screen_root"

    const val CONTRAST_TITLE = "appearance:contrast_title"
    // ContrastTimeline itself will have its own root tag under ContrastTimelineTestTags

    const val BACKGROUND_TITLE = "appearance:background_title"
    const val GRADIENT_BACKGROUND_ROW = "appearance:gradient_background_row"
    const val GRADIENT_BACKGROUND_TEXT = "appearance:gradient_background_text"
    const val GRADIENT_BACKGROUND_SWITCH = "appearance:gradient_background_switch"

    const val DARK_MODE_TITLE = "appearance:dark_mode_title"
    fun darkModeOptionRow(name: String) = "appearance:dark_mode_option_row_$name"
    fun darkModeRadioButton(name: String) = "appearance:dark_mode_radio_button_$name"
    fun darkModeOptionText(name: String) = "appearance:dark_mode_option_text_$name"

    object ContrastTimelineTestTags {
        const val TIMELINE_ROOT = "contrast_timeline:root"
        fun optionItem(id: Int) = "contrast_timeline:option_item_$id"
        fun optionIcon(id: Int) = "contrast_timeline:option_icon_$id"
        fun optionBackground(id: Int) = "contrast_timeline:option_background_$id"
    }
}
