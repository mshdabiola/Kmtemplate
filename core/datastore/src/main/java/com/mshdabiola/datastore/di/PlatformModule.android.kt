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
package com.mshdabiola.datastore.di

import android.content.Context
import com.mshdabiola.datastore.createDataStoreUserData
import org.koin.core.module.Module
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

actual val datastoreModule: Module
    get() =
        module {
            includes(commonModule)

            single(qualifier = qualifier("userdata")) {
                val context: Context = get()

                createDataStoreUserData { context.filesDir.resolve("userdata").absolutePath }
            }
        }
