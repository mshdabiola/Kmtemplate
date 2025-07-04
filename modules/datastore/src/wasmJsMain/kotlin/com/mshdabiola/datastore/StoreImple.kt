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
package com.mshdabiola.datastore

import com.mshdabiola.model.UserData
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class StoreImple : Store {
    private val store: KStore<UserData> = storeOf(key = "my_cats", default = UserData())
    override val userData: Flow<UserData>
        get() = store.updates.map { it ?: UserData() }

    override suspend fun updateUserData(transform: suspend (UserData) -> UserData): UserData {
        val data = transform(userData.first())
        store.update { data }
        return data
    }
}
