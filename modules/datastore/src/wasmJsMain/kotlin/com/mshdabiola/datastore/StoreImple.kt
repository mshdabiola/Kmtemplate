package com.mshdabiola.datastore

import com.mshdabiola.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.updateAndGet

class StoreImple : Store {
    private val mutableUserData = MutableStateFlow(UserData())
    override val userData: Flow<UserData>
        get() = mutableUserData.asStateFlow()

    override suspend fun updateUserData(transform: suspend (UserData) -> UserData): UserData {
        val data = transform(userData.first())
        return mutableUserData.updateAndGet { data }
    }
}
