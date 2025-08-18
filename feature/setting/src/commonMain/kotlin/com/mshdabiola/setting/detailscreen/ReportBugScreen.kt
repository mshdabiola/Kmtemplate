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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.KmtButton
import com.mshdabiola.designsystem.component.KmtTextButton
import com.mshdabiola.designsystem.component.KmtTextField
import com.mshdabiola.designsystem.drawable.KmtIcons
import com.mshdabiola.designsystem.strings.KmtStrings
import com.mshdabiola.designsystem.theme.KmtTheme
import com.mshdabiola.model.testtag.AboutScreenTestTags
import kmtemplate.feature.setting.generated.resources.Res
import kmtemplate.feature.setting.generated.resources.about
import kmtemplate.feature.setting.generated.resources.contact_us
import kmtemplate.feature.setting.generated.resources.developed_by
import kmtemplate.feature.setting.generated.resources.developer
import kmtemplate.feature.setting.generated.resources.last_update
import kmtemplate.feature.setting.generated.resources.privacy_policy
import kmtemplate.feature.setting.generated.resources.terms_and_condition
import kmtemplate.feature.setting.generated.resources.version
import kmtemplate.feature.setting.generated.resources.version_code
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

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
            .padding(horizontal = 24.dp, vertical = 32.dp), // Tag for the root
        verticalArrangement = Arrangement.Top, // Changed from SpacedBy to Top for more control with Spacers
        horizontalAlignment = Alignment.Start,
    ) {
        val heading = rememberTextFieldState()
        val content = rememberTextFieldState()
        val appName = KmtStrings.brand


        KmtTextField(
            modifier = Modifier.fillMaxWidth(),
            state = heading,
            label = "Title",
            placeholder = "Briefly describe the bug you found",
            imeAction = ImeAction.Next,
            maxNum = TextFieldLineLimits.SingleLine
        )
        Spacer(modifier = Modifier.height(16.dp))
        KmtTextField(
            modifier = Modifier.height(200.dp).fillMaxWidth(),
            state = content,
            label = "Description",
            placeholder = "Describe the problem in detail",
            imeAction = ImeAction.Done,

            )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            KmtButton(
                enabled = heading.text.isNotEmpty() && content.text.isNotEmpty(),
                onClick = {
                    val title = "Bug Report of $appName : ${heading.text}"
                    openEmail("mshdabiola@gmail.com", title, content.text.toString())
                },
            ) {
                Text(text = "Submit via Email")
            }
            KmtButton(
                enabled = !(heading.text.isNotEmpty() && content.text.isNotEmpty()),

                onClick = { openUrl("https://github.com/mshdabiola/Kmtemplate/issues") },
            ) {

                Text(text = "Report Bug on GitHub")
            }
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
