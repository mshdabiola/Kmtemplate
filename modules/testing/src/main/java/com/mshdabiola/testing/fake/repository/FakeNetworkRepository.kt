package com.mshdabiola.testing.fake.repository

import com.mshdabiola.data.repository.INetworkRepository

class FakeNetworkRepository : INetworkRepository {
    override suspend fun get() {
    }

    override suspend fun gotoGoogle(): String {
        return "got to google"
    }
}
