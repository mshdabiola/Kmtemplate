/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.setting.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.mshdabiola.model.AssetNotFoundException
import com.mshdabiola.model.BuildConfig
import com.mshdabiola.model.DeviceNotSupportedException
import com.mshdabiola.model.InvalidVersionFormatException
import com.mshdabiola.model.NoUpdateAvailableException
import com.mshdabiola.model.Notification
import com.mshdabiola.model.PreReleaseNotAllowedException
import com.mshdabiola.model.ReleaseInfo
import com.mshdabiola.model.SnackbarDuration
import com.mshdabiola.model.Type
import com.mshdabiola.setting.SettingScreen
import com.mshdabiola.setting.SettingViewModel
import com.mshdabiola.setting.WindowRepository
import com.mshdabiola.ui.LocalNavAnimatedContentScope
import com.mshdabiola.ui.ReleaseUpdateDialog
import kmtemplate.feature.setting.generated.resources.Res
import kmtemplate.feature.setting.generated.resources.data_error_asset_not_found
import kmtemplate.feature.setting.generated.resources.data_error_current_version_greater
import kmtemplate.feature.setting.generated.resources.data_error_device_not_supported
import kmtemplate.feature.setting.generated.resources.data_error_invalid_version_format
import kmtemplate.feature.setting.generated.resources.data_error_prerelease_not_allowed
import kmtemplate.feature.setting.generated.resources.notification_message_error_checking_update
import kmtemplate.feature.setting.generated.resources.notification_message_up_to_date
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

fun NavController.navigateToSetting(
    navOptions: NavOptions = navOptions { launchSingleTop = true },
) =
    navigate(
        Setting,
        navOptions,
    )

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.settingScreen(
    modifier: Modifier,
    onDrawer: (() -> Unit)?,
    setNotification: (Notification) -> Unit,

) {
    composable<Setting> {
        val viewModel: SettingViewModel = koinViewModel()
        val settingState = viewModel.settingState.collectAsStateWithLifecycle()
        val windowRepository: WindowRepository = getWindowRepository()

        CompositionLocalProvider(
            LocalNavAnimatedContentScope provides this,
        ) {
            SettingScreen(
                modifier = modifier,
                onDrawer = onDrawer,
                settingState = settingState.value,
                onContrastChange = { viewModel.setContrast(it) },
                onDarkModeChange = { viewModel.setDarkThemeConfig(it) },
                onGradientBackgroundChange = { viewModel.setGradientBackground(it) },
                onLanguageChange = { viewModel.setLanguage(it) },
                openUrl = { windowRepository.openUrl(it) },
                openEmail = { email, subject, body -> windowRepository.openEmail(email, subject, body) },
                onSetUpdateDialog = { viewModel.setShowDialog(it) },
                onSetUpdateFromPreRelease = { viewModel.setUpdateFromPreRelease(it) },
                onCheckForUpdate = { viewModel.checkForUpdate(BuildConfig.VERSION_NAME) },
            )
            val releaseInfo = settingState.value.releaseInfo
            if (releaseInfo != null) {
                when (releaseInfo) {
                    is ReleaseInfo.NewUpdate -> {
                        ReleaseUpdateDialog(
                            releaseInfo = releaseInfo,
                            onDismissRequest = { viewModel.hideUpdateDialog() },
                            onDownloadClick = { windowRepository.openUrl(releaseInfo.asset) },
                        )
                    }
                    is ReleaseInfo.UpToDate -> {
                        LaunchedEffect(releaseInfo) {
                            viewModel.hideUpdateDialog()
                            setNotification(
                                Notification.Message(
                                    duration = SnackbarDuration.Short,
                                    type = Type.Success,
                                    message = getString(
                                        Res.string.notification_message_up_to_date,
                                    ),
                                ),
                            )
                        }
                    }
                    is ReleaseInfo.Error -> {
                        when (releaseInfo.exception) {
                            is AssetNotFoundException -> {
                                LaunchedEffect(releaseInfo) {
                                    viewModel.hideUpdateDialog()
                                    setNotification(
                                        Notification.Message(
                                            duration = SnackbarDuration.Short,
                                            type = Type.Default,
                                            message = getString(
                                                Res.string.data_error_asset_not_found,
                                            ),
                                        ),
                                    )
                                }
                            }
                            is PreReleaseNotAllowedException -> {
                                LaunchedEffect(releaseInfo) {
                                    viewModel.hideUpdateDialog()
                                    setNotification(
                                        Notification.Message(
                                            duration = SnackbarDuration.Short,
                                            type = Type.Default,
                                            message = getString(
                                                Res.string.data_error_prerelease_not_allowed,
                                            ),
                                        ),
                                    )
                                }
                            }
                            is DeviceNotSupportedException -> {
                                LaunchedEffect(releaseInfo) {
                                    viewModel.hideUpdateDialog()
                                    setNotification(
                                        Notification.Message(
                                            duration = SnackbarDuration.Short,
                                            type = Type.Default,
                                            message = getString(
                                                Res.string.data_error_device_not_supported,
                                            ),
                                        ),
                                    )
                                }
                            }
                            is NoUpdateAvailableException -> {
                                LaunchedEffect(releaseInfo) {
                                    viewModel.hideUpdateDialog()
                                    setNotification(
                                        Notification.Message(
                                            duration = SnackbarDuration.Short,
                                            type = Type.Success,
                                            message = getString(
                                                Res.string.data_error_current_version_greater,
                                            ),
                                        ),
                                    )
                                }
                            }
                            is InvalidVersionFormatException -> {
                                LaunchedEffect(releaseInfo) {
                                    viewModel.hideUpdateDialog()
                                    setNotification(
                                        Notification.Message(
                                            duration = SnackbarDuration.Short,
                                            type = Type.Error,
                                            message = getString(
                                                Res.string.data_error_invalid_version_format,
                                            ),
                                        ),
                                    )
                                }
                            }
                            else -> {
                                LaunchedEffect(releaseInfo) {
                                    viewModel.hideUpdateDialog()
                                    setNotification(
                                        Notification.Message(
                                            duration = SnackbarDuration.Long,
                                            type = Type.Error,
                                            message = getString(
                                                Res.string.notification_message_error_checking_update,
                                            ),
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
expect fun getWindowRepository(): WindowRepository
