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
