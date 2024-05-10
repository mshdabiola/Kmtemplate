package com.mshdabiola.network.di

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cache.storage.FileStorage
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import java.io.File

actual fun <T : HttpClientEngineConfig> HttpClientConfig<T>.initPlatform() {
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }
    install(HttpCache) {
        val file = File.createTempFile("abiola", "tem")
        publicStorage(FileStorage(file))
    }
}
