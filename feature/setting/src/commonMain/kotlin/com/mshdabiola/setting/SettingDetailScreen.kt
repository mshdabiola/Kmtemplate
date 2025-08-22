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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.KmtIconButton
import com.mshdabiola.designsystem.component.KmtTopAppBar
import com.mshdabiola.designsystem.drawable.KmtIcons
import com.mshdabiola.designsystem.strings.KmtStrings
import com.mshdabiola.model.BuildConfig
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.testtag.SettingDetailScreenTestTags
import com.mshdabiola.setting.detailscreen.AboutScreen
import com.mshdabiola.setting.detailscreen.AppearanceScreen
import com.mshdabiola.setting.detailscreen.FaqScreen
import com.mshdabiola.setting.detailscreen.LanguageScreen
import com.mshdabiola.setting.detailscreen.ReportBugScreen
import com.mshdabiola.setting.detailscreen.UpdateScreen
import kmtemplate.feature.setting.generated.resources.Res
import kmtemplate.feature.setting.generated.resources.general
import kmtemplate.feature.setting.generated.resources.report_bug_email_subject_format
import kmtemplate.feature.setting.generated.resources.support
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringArrayResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingDetailScreen(
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)?,
    settingNav: SettingNav,
    settingState: SettingState,
    onContrastChange: (Int) -> Unit = {},
    onDarkModeChange: (DarkThemeConfig) -> Unit = {},
    onGradientBackgroundChange: (Boolean) -> Unit = {},
    onLanguageChange: (String) -> Unit = {},
    openUrl: (String) -> Unit = {},
    openEmail: (String, String, String) -> Unit = { _, _, _ -> },
    onSetUpdateDialog: (Boolean) -> Unit = {},
    onSetUpdateFromPreRelease: (Boolean) -> Unit = {},
    onCheckForUpdate: () -> Unit = {},
) {
    val generalArrayString = stringArrayResource(Res.array.general)
    val supportArrayString = stringArrayResource(Res.array.support)
    val stringArray = listOf(generalArrayString, supportArrayString)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.testTag(SettingDetailScreenTestTags.SCREEN_ROOT),
        topBar = {
            KmtTopAppBar(
                modifier = Modifier.testTag(SettingDetailScreenTestTags.TOP_APP_BAR),
                title = {
                    Text(
                        stringArray
                            .getOrNull(settingNav.segment)
                            ?.getOrNull(settingNav.index)
                            ?: "",
                    )
                },
                navigationIcon = {
                    if (onBack != null) {
                        KmtIconButton(
                            onClick = onBack,
                            modifier = Modifier.testTag(SettingDetailScreenTestTags.BACK_ICON_BUTTON),
                        ) {
                            Icon(
                                imageVector = KmtIcons.ArrowBack,
                                contentDescription = "back",
                            )
                        }
                    }
                },
            )
        },
        containerColor = Color.Transparent,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            when (settingNav) {
                SettingNav.Faq -> {
                    FaqScreen(
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                SettingNav.About -> {
                    AboutScreen(
                        modifier = Modifier.fillMaxSize(),
                        openUrl = openUrl,
                        openEmail = openEmail,
                        platform = settingState.platform,
                    )
                }

                SettingNav.Appearance -> {
                    AppearanceScreen(
                        modifier = Modifier.fillMaxSize(),
                        userSettings = settingState.userSettings,
                        onContrastChange = onContrastChange,
                        onDarkModeChange = onDarkModeChange,
                        onGradientBackgroundChange = onGradientBackgroundChange,
                    )
                }

                SettingNav.Language -> {
                    LanguageScreen(
                        modifier = Modifier.fillMaxSize(),
                        currentLanguageCode = settingState.userSettings.language,
                        onLanguageSelected = onLanguageChange,
                    )
                }

                SettingNav.ReportBug -> {
                    ReportBugScreen(
                        modifier = Modifier.fillMaxSize(),
                        openEmail = { email, subject, body ->
                            coroutineScope.launch {
                                val emailSubject = getString(
                                    Res.string.report_bug_email_subject_format,
                                    BuildConfig.BRAND_NAME,
                                    subject,
                                )
                                openEmail("mshdabiola@gmail.com", emailSubject, body)
                            }
                        },
                        openUrl = openUrl,
                    )
                }
                SettingNav.Update -> {
                    UpdateScreen(
                        modifier = Modifier.fillMaxSize(),
                        userSettings = settingState.userSettings,
                        onSetUpdateDialog = onSetUpdateDialog,
                        onSetUpdateFromPreRelease = onSetUpdateFromPreRelease,
                        onCheckForUpdate = onCheckForUpdate,

                    )
                }
            }
        }
    }
}
