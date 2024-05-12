package com.mshdabiola.datastore

import com.mshdabiola.datastore.model.UserDataSer
import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData
import kotlinx.coroutines.flow.Flow

interface Store {

    val userData: Flow<UserDataSer>

    suspend fun updateUserData(transform: suspend (UserDataSer) -> UserDataSer): UserDataSer

}
