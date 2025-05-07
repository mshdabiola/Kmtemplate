package com.mshdabiola.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

actual val httpClient: HttpClient
    get() =
        HttpClient(CIO) {
            init()
        }
