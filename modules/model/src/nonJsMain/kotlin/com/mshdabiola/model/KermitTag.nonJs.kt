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
package com.mshdabiola.model

import co.touchlab.kermit.Logger
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

actual inline fun <reified L : Logger> Scope.getLoggerWithTag(tag: String): L {
    return get(parameters = { parametersOf(tag) })
}
