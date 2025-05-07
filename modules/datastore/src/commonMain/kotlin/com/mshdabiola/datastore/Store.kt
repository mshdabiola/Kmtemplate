package com.mshdabiola.datastore

import com.mshdabiola.model.UserData
import kotlinx.coroutines.flow.Flow

interface Store {
    val userData: Flow<UserData>

    suspend fun updateUserData(transform: suspend (UserData) -> UserData): UserData
}
