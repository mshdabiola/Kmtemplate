package com.mshdabiola.data.repository

import com.mshdabiola.network.INetworkDataSource

internal class RealINetworkRepository(
    private val networkSource: INetworkDataSource,
) : INetworkRepository {
    override suspend fun get() {
        networkSource.goToGoogle()
    }

    override suspend fun gotoGoogle(): String {
        return ""
    }
}
