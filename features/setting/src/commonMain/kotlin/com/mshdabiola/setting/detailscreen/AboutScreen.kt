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
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.KmtTextButton
import com.mshdabiola.designsystem.drawable.KmtDrawable
import com.mshdabiola.designsystem.strings.KmtStrings
import com.mshdabiola.designsystem.theme.KmtTheme
import kotlinmultiplatformtemplate.features.setting.generated.resources.Res
import kotlinmultiplatformtemplate.features.setting.generated.resources.about_me
import kotlinmultiplatformtemplate.features.setting.generated.resources.contact_us
import kotlinmultiplatformtemplate.features.setting.generated.resources.developed_by
import kotlinmultiplatformtemplate.features.setting.generated.resources.developer
import kotlinmultiplatformtemplate.features.setting.generated.resources.last_update
import kotlinmultiplatformtemplate.features.setting.generated.resources.privacy_policy
import kotlinmultiplatformtemplate.features.setting.generated.resources.terms_and_condition
import kotlinmultiplatformtemplate.features.setting.generated.resources.version
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
) {
    val contactUs = openEmail(
        "mshdabiola@gmail.com",
        "Feedback for KMTemplate",
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Icon(
            imageVector = KmtDrawable.brand,
            contentDescription = "brand",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .testTag("about:app_icon"),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = KmtStrings.brand,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.testTag("about:app_name"),
        )
        HorizontalDivider(
            modifier = Modifier.width(64.dp),
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = stringResource(Res.string.about_me),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.testTag("about:about_me")
                .padding(bottom = 8.dp),

        )

        Text(
            text = stringResource(Res.string.version),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelLarge,
        )
        Text(
            text = KmtStrings.version,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.testTag("about:version_value"),
        )

        Text(
            text = stringResource(Res.string.last_update),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelLarge,
        )
        Text(
            text = KmtStrings.lastUpdate,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.testTag("about:last_update_value"),
        )

        Text(
            text = stringResource(Res.string.developed_by),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelLarge,
        )

        Text(
            text = stringResource(Res.string.developer),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.testTag("about:last_update_value"),
        )

        Text(
            text = stringResource(Res.string.contact_us),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelLarge,
        )
        KmtTextButton(
            onClick = { contactUs() },
            contentPadding = PaddingValues(0.dp),
        ) {
            Text("mshdabiola@gmail.com")
        }
        KmtTextButton(
            onClick = {},
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(stringResource(Res.string.privacy_policy))
        }
        KmtTextButton(
            onClick = { contactUs() },
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(stringResource(Res.string.terms_and_condition))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    KmtTheme {
        AboutScreen()
    }
}
