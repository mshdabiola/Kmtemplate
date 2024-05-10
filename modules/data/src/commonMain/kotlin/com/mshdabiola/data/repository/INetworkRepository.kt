package com.mshdabiola.data.repository

interface INetworkRepository {
    suspend fun get()

    suspend fun gotoGoogle(): String
}
