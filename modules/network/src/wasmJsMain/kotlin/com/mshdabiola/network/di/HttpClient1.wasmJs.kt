package com.mshdabiola.network.di

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.js.Js

actual val httpClient: HttpClient
    get() =
        HttpClient(Js) {
            init()
        }

actual fun <T : HttpClientEngineConfig> HttpClientConfig<T>.initPlatform() {
}
