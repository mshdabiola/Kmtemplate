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
package com.mshdabiola.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.KmtIconButton
import com.mshdabiola.designsystem.component.KmtTopAppBar
import com.mshdabiola.designsystem.drawable.KmtIcons
import kotlinmultiplatformtemplate.features.setting.generated.resources.Res
import kotlinmultiplatformtemplate.features.setting.generated.resources.general
import kotlinmultiplatformtemplate.features.setting.generated.resources.screen_name
import kotlinmultiplatformtemplate.features.setting.generated.resources.segment
import kotlinmultiplatformtemplate.features.setting.generated.resources.support
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource

enum class SettingNav(val segment: Int, val index: Int) {
    // general
    Appearance(0, 0),

    // support
    Issue(1, 0),
    Faq(1, 1),

    About(1, 2),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingListScreen(
    modifier: Modifier = Modifier,
    settingsMap: Map<Int, List<SettingNav>>,
    onDrawer: (() -> Unit)?,
    onSettingClick: (SettingNav) -> Unit = {},
) {
    val segmentArrayString = stringArrayResource(Res.array.segment)

    val generalIcon = listOf(
        KmtIcons.Appearance,
    )
    val generalArrayString = stringArrayResource(Res.array.general)

    val supportIcon = listOf(
        KmtIcons.BugReport,
        KmtIcons.Faq,
        KmtIcons.About,
    )
    val supportArrayString = stringArrayResource(Res.array.support)

    val stringArray = listOf(
        generalArrayString,
        supportArrayString,
    )
    val iconArray = listOf(
        generalIcon,
        supportIcon,
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            KmtTopAppBar(
                title = { Text(stringResource(Res.string.screen_name)) },
                navigationIcon = {
                    if (onDrawer != null) {
                        KmtIconButton(onClick = onDrawer) {
                            Icon(
                                imageVector = KmtIcons.Menu,
                                contentDescription = "menu",
                            )
                        }
                    }
                },
            )
        },
        containerColor = Color.Transparent,
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            settingsMap.forEach { (index, settingList) ->
                item {
                    Text(segmentArrayString[index], style = MaterialTheme.typography.titleSmall)
                }
                items(settingList) { setting ->
                    SettingListItem(
                        modifier = Modifier,
                        icon = iconArray[setting.segment][setting.index],
                        title = stringArray[setting.segment][setting.index],
                        onClick = { onSettingClick(setting) },
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun SettingListItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f),
            )
        }
    }
}
