package com.mshdabiola.datastore

import com.mshdabiola.datastore.model.UserDataSer
import kotlinx.coroutines.flow.Flow

interface Store {

    val userData: Flow<UserDataSer>

    suspend fun updateUserData(transform: suspend (UserDataSer) -> UserDataSer): UserDataSer
}
