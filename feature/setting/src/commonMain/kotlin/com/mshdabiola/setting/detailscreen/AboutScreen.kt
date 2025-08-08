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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.KmtTextButton
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
fun AboutScreen(
    modifier: Modifier = Modifier,
    openUrl: (String) -> Unit = {},
    openEmail: (String, String, String) -> Unit = { _, _, _ -> },
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .testTag(AboutScreenTestTags.SCREEN_ROOT), // Tag for the root
        verticalArrangement = Arrangement.Top, // Changed from SpacedBy to Top for more control with Spacers
        horizontalAlignment = Alignment.Start,
    ) {
        Icon(
            imageVector = KmtIcons.AppIcon,
            contentDescription = "App Logo",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
                .testTag(AboutScreenTestTags.APP_ICON),
            tint = MaterialTheme.colorScheme.primary,
        )

        Text(
            text = KmtStrings.brand,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .testTag(AboutScreenTestTags.APP_NAME),

        )

        Text(
            text = stringResource(Res.string.about),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp, bottom = 8.dp)
                .testTag(AboutScreenTestTags.APP_DESCRIPTION),
        )

        Spacer(Modifier.height(8.dp))
        HorizontalDivider(
            modifier = Modifier
                .width(64.dp)
                .align(Alignment.CenterHorizontally),
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(Res.string.version),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.testTag(AboutScreenTestTags.VERSION_NAME_LABEL),
        )
        Text(
            text = KmtStrings.version,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.testTag(AboutScreenTestTags.VERSION_NAME_VALUE),
        )

        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.version_code),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.testTag(AboutScreenTestTags.VERSION_CODE_LABEL),
        )
        Text(
            text = KmtStrings.versionCode,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.testTag(AboutScreenTestTags.VERSION_CODE_VALUE),
        )

        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.last_update),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.testTag(AboutScreenTestTags.LAST_UPDATE_LABEL),
        )
        Text(
            text = KmtStrings.lastUpdate,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.testTag(AboutScreenTestTags.LAST_UPDATE_VALUE),
        )

        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.developed_by),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.testTag(AboutScreenTestTags.DEVELOPED_BY_LABEL),
        )
        Text(
            text = stringResource(Res.string.developer),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.testTag(AboutScreenTestTags.DEVELOPER_NAME),
        )

        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.contact_us),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.testTag(AboutScreenTestTags.CONTACT_US_LABEL),
        )

        Text(
            text = "mshdabiola@gmail.com",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
                textDecoration = TextDecoration.Underline,
            ),
            modifier = Modifier
                .clickable {
                    openEmail(
                        "mshdabiola@gmail.com",
                        "Feedback for Kmtemplate",
                        "",
                    )
                }
                .padding(top = 4.dp, bottom = 16.dp) // Added bottom padding
                .testTag(AboutScreenTestTags.EMAIL_LINK),
        )

        KmtTextButton(
            onClick = {},
            contentPadding = PaddingValues(vertical = 4.dp),
            modifier = Modifier.testTag(AboutScreenTestTags.PRIVACY_POLICY_BUTTON),
        ) {
            Text(stringResource(Res.string.privacy_policy))
        }

        KmtTextButton(
            onClick = {},
            contentPadding = PaddingValues(vertical = 4.dp),
            modifier = Modifier.testTag(AboutScreenTestTags.TERMS_AND_CONDITIONS_BUTTON),
        ) {
            Text(stringResource(Res.string.terms_and_condition))
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    KmtTheme {
        AboutScreen()
    }
}
