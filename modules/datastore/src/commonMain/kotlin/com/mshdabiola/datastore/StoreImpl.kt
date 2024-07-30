package com.mshdabiola.datastore

import androidx.datastore.core.DataStore
import com.mshdabiola.model.UserData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class StoreImpl(
    private val userdata: DataStore<UserData>,
    private val coroutineDispatcher: CoroutineDispatcher,
) : Store {

    override val userData: Flow<UserData>
        get() = userdata
            .data

    override suspend fun updateUserData(transform: suspend (UserData) -> UserData): UserData {
        return withContext(coroutineDispatcher) {
            userdata.updateData(transform)
        }
    }
}
