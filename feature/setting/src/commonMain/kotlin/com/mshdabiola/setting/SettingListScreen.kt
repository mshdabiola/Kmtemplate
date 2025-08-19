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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.KmtIconButton
import com.mshdabiola.designsystem.component.KmtTopAppBar
import com.mshdabiola.designsystem.drawable.KmtIcons
import com.mshdabiola.model.testtag.SettingScreenListTestTags
import kmtemplate.feature.setting.generated.resources.Res
import kmtemplate.feature.setting.generated.resources.general
import kmtemplate.feature.setting.generated.resources.screen_name
import kmtemplate.feature.setting.generated.resources.segment
import kmtemplate.feature.setting.generated.resources.support
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource

enum class SettingNav(val segment: Int, val index: Int) {
    Appearance(0, 0),
    Language(0, 1),
    Update(0, 2),
    ReportBug(1, 0),
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
    val generalIcon = listOf(KmtIcons.Appearance, KmtIcons.Language, KmtIcons.Update)
    val generalArrayString = stringArrayResource(Res.array.general)
    val supportIcon = listOf(KmtIcons.BugReport, KmtIcons.Faq, KmtIcons.About)
    val supportArrayString = stringArrayResource(Res.array.support)
    val stringArray = listOf(generalArrayString, supportArrayString)
    val iconArray = listOf(generalIcon, supportIcon)

    Scaffold(
        modifier = modifier.testTag(SettingScreenListTestTags.SCREEN_ROOT),
        topBar = {
            KmtTopAppBar(
                modifier = Modifier.testTag(SettingScreenListTestTags.TOP_APP_BAR),
                title = { Text(stringResource(Res.string.screen_name)) },
                navigationIcon = {
                    if (onDrawer != null) {
                        KmtIconButton(
                            onClick = onDrawer,
                            modifier = Modifier.testTag(SettingScreenListTestTags.MENU_ICON_BUTTON),
                        ) {
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
                .padding(16.dp)
                .testTag(SettingScreenListTestTags.SETTINGS_LAZY_COLUMN),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            settingsMap.forEach { (mapIndex, settingList) ->
                item {
                    Text(
                        text = segmentArrayString.getOrElse(mapIndex, { "" }),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.testTag("${SettingScreenListTestTags.SECTION_HEADER_TEXT_PREFIX}$mapIndex"),
                    )
                }
                items(settingList, key = { it.name }) { setting ->
                    SettingListItem(
                        modifier = Modifier,
                        icon = iconArray[setting.segment][setting.index],
                        title = stringArray
                            .getOrNull(setting.segment)
                            ?.getOrNull(setting.index)
                            ?: "",
                        onClick = { onSettingClick(setting) },
                        settingNav = setting,
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
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
    settingNav: SettingNav,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("${SettingScreenListTestTags.LIST_ITEM_CARD_PREFIX}${settingNav.name}"),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.testTag("${SettingScreenListTestTags.LIST_ITEM_ICON_PREFIX}${settingNav.name}"),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .weight(1f)
                    .testTag("${SettingScreenListTestTags.LIST_ITEM_TITLE_TEXT_PREFIX}${settingNav.name}"),
            )
        }
    }
}
