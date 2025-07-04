/*
 * Copyright (C) 2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
 */
package com.mshdabiola.designsystem.string

import androidx.compose.runtime.Composable
import kotlin_multiplatform_template.modules.designsystem.generated.resources.Res
import kotlin_multiplatform_template.modules.designsystem.generated.resources.app_name
import kotlin_multiplatform_template.modules.designsystem.generated.resources.modules_ui_cbt_exam_part
import kotlin_multiplatform_template.modules.designsystem.generated.resources.modules_ui_cbt_sections
import kotlin_multiplatform_template.modules.designsystem.generated.resources.modules_ui_cbt_subject
import kotlin_multiplatform_template.modules.designsystem.generated.resources.modules_ui_cbt_type
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource

val appName2
    @Composable
    get() = stringResource(Res.string.app_name)

val subject
    @Composable
    get() = stringResource(Res.string.modules_ui_cbt_subject)

val type2
    @Composable
    get() = stringResource(Res.string.modules_ui_cbt_type)

val examPart
    @Composable
    get() = stringArrayResource(Res.array.modules_ui_cbt_exam_part).toTypedArray()

val sections
    @Composable
    get() = stringArrayResource(Res.array.modules_ui_cbt_sections).toTypedArray()

@OptIn(ExperimentalResourceApi::class)
fun getFileUri(fileName: String) = Res.getUri(fileName)

@OptIn(ExperimentalResourceApi::class)
suspend fun getByte(fileName: String) = Res.readBytes(fileName)
//
// val mainNavigator
//    @Composable
//    get() = stringArrayResource(Res.array.main_navigator)
// val settingNavigator
//    @Composable
//    get() = stringArrayResource(Res.array.setting_navigator)
//
// val cbtNavigator
//    @Composable
//    get() = stringArrayResource(Res.array.str_arr)
