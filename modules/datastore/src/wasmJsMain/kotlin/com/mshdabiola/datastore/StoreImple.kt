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
