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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.KmtButton
import com.mshdabiola.designsystem.component.KmtTextField
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.BuildConfig
import com.mshdabiola.model.testtag.ReportBugScreenTestTags
import kmtemplate.feature.setting.generated.resources.Res
import kmtemplate.feature.setting.generated.resources.report_bug_description_label
import kmtemplate.feature.setting.generated.resources.report_bug_description_placeholder
import kmtemplate.feature.setting.generated.resources.report_bug_submit_email_button
import kmtemplate.feature.setting.generated.resources.report_bug_submit_github_button
import kmtemplate.feature.setting.generated.resources.report_bug_title_label
import kmtemplate.feature.setting.generated.resources.report_bug_title_placeholder
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ReportBugScreen(
    modifier: Modifier = Modifier,
    openEmail: (String, String, String) -> Unit = { _, _, _ -> },
    openUrl: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 8.dp)
            .testTag(ReportBugScreenTestTags.ROOT_COLUMN),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        val heading = rememberTextFieldState()
        val content = rememberTextFieldState()

        KmtButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .testTag(ReportBugScreenTestTags.SUBMIT_GITHUB_BUTTON),
            enabled = true,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
            shape = ButtonDefaults.shapes(MaterialTheme.shapes.medium),
            onClick = { openUrl(BuildConfig.ISSUE_GITHUB_URL) },
        ) {
            Text(text = stringResource(Res.string.report_bug_submit_github_button))
        }

        Spacer(Modifier.height(24.dp))

        KmtTextField(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(ReportBugScreenTestTags.TITLE_TEXT_FIELD),
            state = heading,
            label = stringResource(Res.string.report_bug_title_label),
            placeholder = stringResource(Res.string.report_bug_title_placeholder),
            imeAction = ImeAction.Next,
            maxNum = TextFieldLineLimits.SingleLine,
        )
        Spacer(modifier = Modifier.height(16.dp))
        KmtTextField(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .testTag(ReportBugScreenTestTags.DESCRIPTION_TEXT_FIELD),
            state = content,
            label = stringResource(Res.string.report_bug_description_label),
            placeholder = stringResource(Res.string.report_bug_description_placeholder),
            imeAction = ImeAction.Done,

        )
        Spacer(modifier = Modifier.height(16.dp))
        KmtButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .testTag(ReportBugScreenTestTags.SUBMIT_EMAIL_BUTTON),
            enabled = heading.text.isNotEmpty() && content.text.isNotEmpty(),
            onClick = {
                openEmail(BuildConfig.DEVELOPER_EMAIL, heading.text.toString(), content.text.toString())
            },
        ) {
            Text(text = stringResource(Res.string.report_bug_submit_email_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportBugScreenPreview() {
    KmtTheme {
        ReportBugScreen()
    }
}
