package com.mshdabiola.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

actual val httpClient: HttpClient
    get() =
        HttpClient(Android) {
            init()
        }
