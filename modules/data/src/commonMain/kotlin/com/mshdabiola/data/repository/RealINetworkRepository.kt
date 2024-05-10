package com.mshdabiola.data.repository

import com.mshdabiola.network.INetworkDataSource

internal class RealINetworkRepository constructor(
    private val INetworkDataSource: INetworkDataSource,
) : INetworkRepository {
    override suspend fun get() {
        //  networkSource.get()
    }

    override suspend fun gotoGoogle(): String {
        return ""
    }
}
