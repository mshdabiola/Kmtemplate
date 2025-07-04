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
package com.mshdabiola.designsystem.string

import androidx.compose.runtime.Composable
import hydraulicapp.modules.designsystem.generated.resources.Res
import hydraulicapp.modules.designsystem.generated.resources.app_name
import hydraulicapp.modules.designsystem.generated.resources.modules_ui_cbt_exam_part
import hydraulicapp.modules.designsystem.generated.resources.modules_ui_cbt_sections
import hydraulicapp.modules.designsystem.generated.resources.modules_ui_cbt_subject
import hydraulicapp.modules.designsystem.generated.resources.modules_ui_cbt_type
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
