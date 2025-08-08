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
package com.mshdabiola.model.testtag

object FaqScreenTestTags {
    const val SCREEN_ROOT = "faq:screen_root"
    const val EMPTY_STATE_TEXT = "faq:empty_state_text"
    const val FAQ_LIST = "faq:faq_list"

    object FaqListItemTestTags {
        const val LIST_ITEM_ROOT_PREFIX = "faq_item:root_"
        const val QUESTION_TEXT_PREFIX = "faq_item:question_"
        const val EXPAND_ICON_PREFIX = "faq_item:expand_icon_"
        const val ANSWER_TEXT_PREFIX = "faq_item:answer_"
    }
}
