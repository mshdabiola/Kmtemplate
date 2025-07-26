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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.strings.KmtStrings
import com.mshdabiola.designsystem.strings.KmtStrings.version
import kotlinmultiplatformtemplate.features.setting.generated.resources.Res
import kotlinmultiplatformtemplate.features.setting.generated.resources.about_me
import kotlinmultiplatformtemplate.features.setting.generated.resources.last_update
import kotlinmultiplatformtemplate.features.setting.generated.resources.terms_and_condition
import kotlinmultiplatformtemplate.features.setting.generated.resources.version
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(start = 24.dp, end = 24.dp, top = 200.dp)) {
        Text(
            text = KmtStrings.brand,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.testTag("about:app_name"),
        )
        Spacer(Modifier.height(8.dp))
        HorizontalDivider(
            modifier = Modifier.width(64.dp),
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.version),
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = version,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.testTag("about:version_value"),
        )

        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.last_update),
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = KmtStrings.lastUpdate,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.testTag("about:last_update_value"),
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.about_me),
            modifier = Modifier.testTag("about:about_me"),
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.terms_and_condition),
            modifier = Modifier.testTag("about:terms"),
        )
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    AboutScreen()
}
