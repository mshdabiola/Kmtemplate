package com.mshdabiola.datastore

import androidx.datastore.core.DataStore
import com.mshdabiola.datastore.model.UserDataSer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class StoreImpl(
    private val userdata: DataStore<UserDataSer>,
    private val coroutineDispatcher: CoroutineDispatcher,
) : Store {

    override val userData: Flow<UserDataSer>
        get() = userdata
            .data

    override suspend fun updateUserData(transform: suspend (UserDataSer) -> UserDataSer): UserDataSer {
        return withContext(coroutineDispatcher) {
            userdata.updateData(transform)
        }
    }
}
