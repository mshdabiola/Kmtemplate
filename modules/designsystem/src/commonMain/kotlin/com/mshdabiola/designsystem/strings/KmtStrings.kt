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
package com.mshdabiola.designsystem.strings

import androidx.compose.runtime.Composable
import kmtemplate.modules.designsystem.generated.resources.Res
import kmtemplate.modules.designsystem.generated.resources.brand
import kmtemplate.modules.designsystem.generated.resources.last_update
import kmtemplate.modules.designsystem.generated.resources.supported_languages_codes
import kmtemplate.modules.designsystem.generated.resources.supported_languages_display_names
import kmtemplate.modules.designsystem.generated.resources.version
import kmtemplate.modules.designsystem.generated.resources.version_name
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource

object KmtStrings {
    val brand
        @Composable get() = stringResource(Res.string.brand)

    val versionCode
        @Composable get() = stringResource(Res.string.version)

    val version
        @Composable get() = stringResource(Res.string.version_name)

    val lastUpdate
        @Composable get() = stringResource(Res.string.last_update)

    val supportedLanguage
        @Composable get(): List<Pair<String, String>> {
            val name = stringArrayResource(Res.array.supported_languages_display_names)
            val code = stringArrayResource(Res.array.supported_languages_codes)
            return name.zip(code)
        }
}
