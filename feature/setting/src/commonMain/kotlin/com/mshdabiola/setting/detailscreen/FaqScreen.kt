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
package com.mshdabiola.setting.detailscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.drawable.KmtIcons
import com.mshdabiola.designsystem.theme.KmtTheme
import kmtemplate.feature.setting.generated.resources.Res
import kmtemplate.feature.setting.generated.resources.faq_benefits_answer
import kmtemplate.feature.setting.generated.resources.faq_benefits_question
import kmtemplate.feature.setting.generated.resources.faq_empty_state
import kmtemplate.feature.setting.generated.resources.faq_find_shared_code_answer
import kmtemplate.feature.setting.generated.resources.faq_find_shared_code_question
import kmtemplate.feature.setting.generated.resources.faq_icon_cd_collapse
import kmtemplate.feature.setting.generated.resources.faq_icon_cd_expand
import kmtemplate.feature.setting.generated.resources.faq_kmp_answer
import kmtemplate.feature.setting.generated.resources.faq_kmp_question
import kmtemplate.feature.setting.generated.resources.faq_preview_shared_code_answer_collapsed
import kmtemplate.feature.setting.generated.resources.faq_preview_shared_code_answer_expanded
import kmtemplate.feature.setting.generated.resources.faq_preview_shared_code_question
import kmtemplate.feature.setting.generated.resources.faq_share_ui_answer
import kmtemplate.feature.setting.generated.resources.faq_share_ui_question
import kmtemplate.feature.setting.generated.resources.faq_template_help_answer
import kmtemplate.feature.setting.generated.resources.faq_template_help_question
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

data class FaqItem(
    val id: Int,
    val question: String,
    val answer: String,
)

@Composable
fun FaqScreen(
    modifier: Modifier = Modifier,
) {
    val questions = listOf(
        FaqItem(
            id = 1,
            question = stringResource(Res.string.faq_kmp_question),
            answer = stringResource(Res.string.faq_kmp_answer),
        ),
        FaqItem(
            id = 2,
            question = stringResource(Res.string.faq_template_help_question),
            answer = stringResource(Res.string.faq_template_help_answer),
        ),
        FaqItem(
            id = 3,
            question = stringResource(Res.string.faq_share_ui_question),
            answer = stringResource(Res.string.faq_share_ui_answer),
        ),
        FaqItem(
            id = 4,
            question = stringResource(Res.string.faq_benefits_question),
            answer = stringResource(Res.string.faq_benefits_answer),
        ),
        FaqItem(
            id = 5,
            question = stringResource(Res.string.faq_find_shared_code_question),
            answer = stringResource(Res.string.faq_find_shared_code_answer),
        ),
    )

    if (questions.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag(FaqScreenTestTags.SCREEN_ROOT),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(Res.string.faq_empty_state),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag(FaqScreenTestTags.EMPTY_STATE_TEXT),
            )
        }
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .testTag(FaqScreenTestTags.FAQ_LIST),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(questions, key = { it.id }) { faqItem ->
            FaqListItem(
                faqItem = faqItem,
                modifier = Modifier.testTag("${FaqListItemTestTags.LIST_ITEM_ROOT_PREFIX}${faqItem.id}"),
            )
        }
    }
}

@Composable
fun FaqListItem(
    faqItem: FaqItem,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = faqItem.question,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("${FaqListItemTestTags.QUESTION_TEXT_PREFIX}${faqItem.id}"),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if (expanded) KmtIcons.ExpandLess else KmtIcons.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(Res.string.faq_icon_cd_collapse)
                    } else {
                        stringResource(Res.string.faq_icon_cd_expand)
                    },
                    modifier = Modifier.testTag("${FaqListItemTestTags.EXPAND_ICON_PREFIX}${faqItem.id}"),
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = faqItem.answer,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.testTag("${FaqListItemTestTags.ANSWER_TEXT_PREFIX}${faqItem.id}"),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "FAQ Screen Preview")
@Composable
fun FaqScreenPreview() {
    KmtTheme {
        FaqScreen()
    }
}

@Preview(showBackground = true, name = "FAQ List Item Preview (Collapsed)")
@Composable
fun FaqListItemCollapsedPreview() {
    KmtTheme {
        FaqListItem(
            faqItem = FaqItem(
                id = 5,
                question = stringResource(Res.string.faq_preview_shared_code_question),
                answer = stringResource(Res.string.faq_preview_shared_code_answer_collapsed),
            ),
        )
    }
}

@Preview(showBackground = true, name = "FAQ List Item Preview (Expanded)")
@Composable
fun FaqListItemExpandedPreview() {
    KmtTheme {
        val item = FaqItem(
            id = 5,
            question = stringResource(Res.string.faq_preview_shared_code_question),
            answer = stringResource(Res.string.faq_preview_shared_code_answer_expanded),
        )
        FaqListItem(faqItem = item)
    }
}

object FaqScreenTestTags {
    const val SCREEN_ROOT = "faq:screen_root"
    const val EMPTY_STATE_TEXT = "faq:empty_state_text"
    const val FAQ_LIST = "faq:faq_list"
}

object FaqListItemTestTags {
    const val LIST_ITEM_ROOT_PREFIX = "faq_item:root_"
    const val QUESTION_TEXT_PREFIX = "faq_item:question_"
    const val EXPAND_ICON_PREFIX = "faq_item:expand_icon_"
    const val ANSWER_TEXT_PREFIX = "faq_item:answer_"
}
